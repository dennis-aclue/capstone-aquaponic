package de.voelkldennis.backend.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Long id;                //id for database, primary key
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;
    private String[] roles;         //Roles for the user [USER, ADMIN, MANAGER]
    private String[] authorities;   //Authorities for the user [USER_READ, USER_WRITE, ...]
    private boolean isActive;
    private boolean isNotLocked;    //Is the user locked or not

}
