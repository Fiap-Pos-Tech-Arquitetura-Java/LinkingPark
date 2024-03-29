package br.com.fiap.postech.linkingpark.security;

import java.util.Collection;
import java.util.List;

import br.com.fiap.postech.linkingpark.documents.Motorista;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;


	private String username;
	private String password;
	private List<GrantedAuthority> authorities;

	public UserDetailsImpl(Motorista motorista) {

		this.username = motorista.getEmail();
		this.password = motorista.getSenha();
	}

	public UserDetailsImpl() {
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
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