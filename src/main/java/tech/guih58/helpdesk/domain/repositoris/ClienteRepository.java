package tech.guih58.helpdesk.domain.repositoris;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.guih58.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {

}
