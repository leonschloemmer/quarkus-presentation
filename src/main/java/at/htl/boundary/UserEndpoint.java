package at.htl.boundary;

import at.htl.control.PasswordSecurityUtils;
import at.htl.control.dao.UserDAO;
import at.htl.entity.User;
import at.htl.restInterface.UserInterface;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Path("users")
@Transactional
public class UserEndpoint {

    @Inject
    UserDAO userRepo;

    @POST
    public Response createUser(UserInterface ui) {
        String hashedPassword;
        try {
            hashedPassword = PasswordSecurityUtils.generatePasswordHash(ui.getPassword());
        } catch (NoSuchAlgorithmException e) {
            return Response.serverError().build();
        } catch (InvalidKeySpecException e) {
            return Response.serverError().build();
        }

        User user = new User(ui.getUsername(), hashedPassword);
        userRepo.persistUser(user);

        // return jwt to permit access to secure information
    }

}
