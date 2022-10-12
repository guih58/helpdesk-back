package tech.guih58.helpdesk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.guih58.helpdesk.domain.Chamado;
import tech.guih58.helpdesk.domain.Cliente;
import tech.guih58.helpdesk.domain.Tecnico;
import tech.guih58.helpdesk.domain.enums.Perfil;
import tech.guih58.helpdesk.domain.enums.Prioridade;
import tech.guih58.helpdesk.domain.enums.Status;
import tech.guih58.helpdesk.domain.repositoris.ChamadoRepository;
import tech.guih58.helpdesk.domain.repositoris.ClienteRepository;
import tech.guih58.helpdesk.domain.repositoris.TecnicoRepository;

import java.util.Arrays;

@Service
public class DBService {
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void instanciaDB(){
        Tecnico tec1 = new Tecnico(null, "Matheus Guilherme", "70642012164", "matheus.guih58@gmail.com", encoder.encode("123"));
        tec1.addPerfis(Perfil.ADMIN);


        Cliente cli1 = new Cliente(null,"Anna","33703477172","anna@gmail.com",encoder.encode("123"));

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Priemiro Chamado", tec1, cli1);

        tecnicoRepository.saveAll(Arrays.asList(tec1));
        clienteRepository.saveAll(Arrays.asList(cli1));
        chamadoRepository.saveAll(Arrays.asList(c1));
    }

}
