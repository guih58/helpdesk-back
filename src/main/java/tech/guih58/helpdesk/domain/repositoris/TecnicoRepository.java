package tech.guih58.helpdesk.domain.repositoris;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.guih58.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico,Integer> {

}
