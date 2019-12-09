package at.htl.control;

import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.util.Map;
import java.util.stream.Collectors;

import static io.smallrye.jwt.KeyUtils.decodePrivateKey;
import static io.smallrye.jwt.KeyUtils.readPrivateKey;

public class TokenUtils {

    private TokenUtils() {

    }

    public static String generateTokenString(String uid)
            throws Exception{
        PrivateKey pk = readPrivateKey("/private.pem");
        return generateTokenString(pk, "/private.pem", uid);
    }

    public static String generateTokenString(PrivateKey privateKey, String kid, String uid) throws Exception {
//        JwtClaims claims = JwtClaims.parse(readTokenContent(jsonResName));
        JwtClaims claims = JwtClaims.parse(getJwtClaims(uid));
        long currentTimeInSecs = currentTimeInSecs();
//        long exp = timeClaims != null && timeClaims.containsKey(Claims.exp.name())
//                ? timeClaims.get(Claims.exp.name()) : currentTimeInSecs + 300; // 300 seconds until expires
        long exp = currentTimeInSecs + 60*15; // 15 minutes until token expires

        // setting claims for jwt
        claims.setIssuedAt(NumericDate.fromSeconds(currentTimeInSecs));
        claims.setClaim(Claims.auth_time.name(), NumericDate.fromSeconds(currentTimeInSecs));
        claims.setExpirationTime(NumericDate.fromSeconds(exp));

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(privateKey);
        jws.setKeyIdHeaderValue(kid);
        jws.setHeader("typ", "JWT");
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_PSS_USING_SHA256);

        return jws.getCompactSerialization();
    }

//    private static String readTokenContent(String jsonResName) throws IOException {
//        InputStream contentIS = TokenUtils.class.getResourceAsStream(jsonResName); // https://stackoverflow.com/questions/3861989/preferred-way-of-loading-resources-in-java#3862115
//        try(BufferedReader buffer = new BufferedReader(new InputStreamReader(contentIS))) {
//            return buffer.lines().collect(Collectors.joining("\n"));
//        }
//    }

    private static String getJwtClaims(String uid) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("iss", "https://this-is-totally-our.domain"); // The issuer of the token, us
        builder.add("sub", uid); // sub = subject = the user that the token was created for

        JsonArrayBuilder groupsBuilder = Json.createArrayBuilder();
        groupsBuilder.add("defaultUsers");

        builder.add("groups", groupsBuilder.build());

        return builder.build().toString();
    }

    public static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }

}
