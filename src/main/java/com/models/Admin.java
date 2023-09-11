package com.models;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.validation.constraints.NotBlank;
/*
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
*/
@Document(collection = "admins")
public class Admin //implements UserDetails
{

	@Id
    private String id;
	@NotBlank
    private String username;
	@NotBlank
    private String password;
	private GrantedAuthority authority;
	
	
		
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admin other = (Admin) obj;
		return Objects.equals(id, other.id);
	}
	

	// Set the role as authority
    public void setRole(String role) {
        this.authority = new SimpleGrantedAuthority(role);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authority);
    }
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Admin(String id, @NotBlank String username, @NotBlank String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public Admin(@NotBlank String username, @NotBlank String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public Admin() {
		super();
	}
	
	@Override
	public String toString() {
		return "Admin [id=" + id + ", username=" + username + ", password=" + password + "]";
	}

/*
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
	
	*/
	
	
}
