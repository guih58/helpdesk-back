package tech.guih58.helpdesk.domain.repositoris;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.guih58.helpdesk.domain.Pessoal;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoal,Integer> {

    Optional<Pessoal> findByCpf(String cpf);
    Optional<Pessoal> findByEmail(String email);

}
