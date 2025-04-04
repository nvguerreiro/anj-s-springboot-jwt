package com.nouhoun.springboot.jwt.integration.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "USER")
@Getter
@Setter
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, unique = true)
	@NotNull
    @Size(min = 4, max = 50)
    private String username;

	@NotEmpty
    @JsonIgnore
    private String password;

    @Column(name = "first_name", length = 50)
    @Size(min = 4, max = 50)
	@NotNull
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(min = 4, max = 50)
	@NotNull
    private String lastName;


    @Column(name = "email", length = 50)
	@NotNull
	@Email
    private String email;

    @Column(name = "enabled")
    @NotNull
    private Boolean enabled;

    @Column(name = "lastpasswordresetdate")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private java.util.Date lastPasswordResetDate;



	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})

	@JsonIgnore
    private List<Authority> authorities = new ArrayList<>();

	public List<GrantedAuthority> getGrantedAuthorities(){
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (Authority authority : authorities){
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		}

		return grantedAuthorities;
	}


}