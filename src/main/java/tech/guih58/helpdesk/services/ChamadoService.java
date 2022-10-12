package tech.guih58.helpdesk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.guih58.helpdesk.domain.Chamado;
import tech.guih58.helpdesk.domain.Cliente;
import tech.guih58.helpdesk.domain.Tecnico;
import tech.guih58.helpdesk.domain.dtos.ChamadoDTO;
import tech.guih58.helpdesk.domain.enums.Prioridade;
import tech.guih58.helpdesk.domain.enums.Status;
import tech.guih58.helpdesk.domain.repositoris.ChamadoRepository;
import tech.guih58.helpdesk.services.execeptions.ObjectnotFoundException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;
    public Chamado findById(Integer id){
        Optional<Chamado> obj = chamadoRepository.findById(id);
        return  obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado Id" + id ));
    }

    public List<Chamado> findAll() {
        return chamadoRepository.findAll();
    }

    public Chamado create(ChamadoDTO objDTO) {
        return chamadoRepository.save(newChamado(objDTO));
    }

    private Chamado newChamado(ChamadoDTO obj){
        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());

        Chamado chamado = new Chamado();

        if(obj.getId() != null){
            chamado.setId(obj.getId());
        }
        if(obj.getStatus().equals(2)){
            chamado.setDataFechamaento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toEnum(obj.getStatus()));
        chamado.setTitulo(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        return chamado;
    }



    public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
        objDTO.setId(id);
        Chamado oldObj = findById(id);
        oldObj = newChamado(objDTO);
        return chamadoRepository.save(oldObj);
    }
}
