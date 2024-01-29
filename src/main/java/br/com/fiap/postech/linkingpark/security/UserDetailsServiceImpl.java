package br.com.fiap.postech.linkingpark.security;

import java.util.Optional;

import br.com.fiap.postech.linkingpark.documents.Motorista;
import br.com.fiap.postech.linkingpark.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private MotoristaRepository motoristaRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<Motorista> motorista = motoristaRepository.findByEmail(userName);

		if (motorista.isPresent())
			return new UserDetailsImpl(motorista.get());
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			
	}
}