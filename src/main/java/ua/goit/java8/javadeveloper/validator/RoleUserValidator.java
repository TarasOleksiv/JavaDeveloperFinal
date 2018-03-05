package ua.goit.java8.javadeveloper.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.service.UserService;

/**
 * Created by t.oleksiv on 03/03/2018.
 */

@Component
public class RoleUserValidator {

    @Autowired
    private UserService userService;

    public boolean isAllowedToRead(long id, Authentication authentication){
        String usernamePrincipal = authentication.getName();
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasSuperRole =
                ((authentication.getAuthorities().stream()
                        .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")))
                        ||
                        (authentication.getAuthorities().stream()
                                .anyMatch(r -> r.getAuthority().equals("ROLE_MODERATOR"))));

        if (hasSuperRole) {
            return true;
        }

        User userPrincipal = userService.findByUsername(usernamePrincipal);

        if (userPrincipal.getId() != id){
            return false;
        }

        return true;
    }
}
