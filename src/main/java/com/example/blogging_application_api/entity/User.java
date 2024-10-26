package com.example.blogging_application_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {
    //     private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotEmpty(message = "Name can not be empty")
    @Size(min = 8, message = "Username must be min of 8 character")
    @Column(unique = true)
    String name;
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email can not be empty")
    String email;
    @NotEmpty(message = "Password can not be empty")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d).+$")
    /*
    Breakdown of the Pattern:

    ^ - Asserts the start of the string.
    (?=.*[a-z]) - Ensures there is at least one lowercase letter.
    (?=.*[A-Z]) - Ensures there is at least one uppercase letter.
    (?=.*\d) - Ensures there is at least one digit.
    .+ - Matches one or more characters (to ensure that the password has a length greater than zero).
    $ - Asserts the end of the string.

        System.out.println(isValidPassword("Password1")); // true
        System.out.println(isValidPassword("password"));   // false
        System.out.println(isValidPassword("PASSWORD"));   // false
        System.out.println(isValidPassword("123456"));     // false
        System.out.println(isValidPassword("Pass123"));    // true
     */
    String password;
    @NotEmpty(message = "About section can not be empty")
    String about;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns =  @JoinColumn(name = "role", referencedColumnName = "roleId")
    )
    private Set<Roles> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities = this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getRoleName())).toList();
        return this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getRoleName())).toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
