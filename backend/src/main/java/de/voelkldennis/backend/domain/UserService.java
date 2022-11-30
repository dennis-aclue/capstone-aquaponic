package de.voelkldennis.backend.domain;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
@Service("userDetailsService")
@Transactional
public class UserService implements UserDetails, UserDetailsService {
    private UserRepo userRepo;
    private User user;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public UserService(User user) {
        this.user = user;
    }

//    public User getUser(String username) {
//        return userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
//    }

    public void deleteUser(String username) {
        userRepo.deleteByUsername(username);
    }

    public void createUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(User user) {
        userRepo.save(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stream(this.user.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // I do not use this method in my application now
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // I do not use this method too (I do not use password expiration)
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            LOGGER.error("User not found by username: " + username);
            throw new UsernameNotFoundException("User not found by username: " + username);
        } else {
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepo.save(user);
            UserService userService = new UserService(user);
            LOGGER.info("Returning found user by username: " + username);
            return userService;
        }
    }

}
