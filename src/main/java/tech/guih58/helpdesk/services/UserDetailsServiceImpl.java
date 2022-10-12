package tech.guih58.helpdesk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.guih58.helpdesk.domain.Pessoal;
import tech.guih58.helpdesk.domain.repositoris.PessoaRepository;
import tech.guih58.helpdesk.security.UserSS;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private PessoaRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoal> user = repository.findByEmail(email);
        if(user.isPresent()) {
            return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getSenha(), user.get().getPerfis());
        }
        throw new UsernameNotFoundException(email);
    }

}
