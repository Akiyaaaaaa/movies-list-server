package dev.akiyaaa.moviesreview.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.akiyaaa.moviesreview.models.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Getter
    private String id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    public Collection<? extends GrantedAuthority> authorities;
    
    
    public UserDetailImpl(String id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }
    public static UserDetailImpl build(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserDetailImpl(user.getId(),user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    public String getEmail(){
        return email;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
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
