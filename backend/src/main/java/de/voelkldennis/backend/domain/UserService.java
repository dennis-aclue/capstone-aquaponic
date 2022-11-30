package de.voelkldennis.backend.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetails {
    private final UserRepo userRepo;
    private User user;

    public User getUser(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

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
}
