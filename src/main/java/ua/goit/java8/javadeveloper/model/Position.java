package ua.goit.java8.javadeveloper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Taras on 23.02.2018.
 */

@Entity
@Table(name = "positions")
@Proxy(lazy = false)
public class Position implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "position")
    private Set<User> users;

    public Position() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean isUserExist(User userToCheck){
        Set<User> users = this.getUsers();
        for (User user: users){
            if (userToCheck.getId() == user.getId()){
                return true;
            }
        }
        return false;
    }
}
