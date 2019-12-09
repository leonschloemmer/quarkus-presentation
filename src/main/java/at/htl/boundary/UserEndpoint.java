package at.htl.boundary;

import at.htl.control.PasswordSecurity;
import at.htl.entity.User;
import at.htl.restInterface.UserInterface;

import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Path("users")
@Transactional
public class UserEndpoint {

    @POST
    public Response createUser(UserInterface ui) {
        String hashedPassword;
        try {
            hashedPassword = PasswordSecurity.generatePasswordHash(ui.getPassword());
        } catch (NoSuchAlgorithmException e) {
            return Response.serverError().build();
        } catch (InvalidKeySpecException e) {
            return Response.serverError().build();
        }

        User user = new User(ui.getUsername(), hashedPassword);
    }

}
