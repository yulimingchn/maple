package com.dawn.maple.bean;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author dawnyu
 */
@Data
public class User implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private boolean enabled;

    private List<Role> roles;

    private String email;

    private String userFace;

    private Timestamp regTime;

    private Date createTime;

    private Date updateTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
