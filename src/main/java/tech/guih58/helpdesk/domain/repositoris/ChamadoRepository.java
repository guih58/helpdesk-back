package tech.guih58.helpdesk.domain.repositoris;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.guih58.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
}
