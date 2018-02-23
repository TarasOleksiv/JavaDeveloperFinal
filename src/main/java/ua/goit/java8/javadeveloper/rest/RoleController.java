package ua.goit.java8.javadeveloper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.goit.java8.javadeveloper.model.Role;
import ua.goit.java8.javadeveloper.service.RoleService;

import java.util.List;

/**
 * Created by Taras on 20.02.2018.
 */

@RestController
@RequestMapping(value = "api/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = this.roleService.getAll();

        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    /*@GetMapping("api/admin/roles")
    public List<Role> listRoles() {
        return roleService.getAll();
    }*/
}
