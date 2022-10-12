package tech.guih58.helpdesk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.guih58.helpdesk.domain.Pessoal;
import tech.guih58.helpdesk.domain.Tecnico;
import tech.guih58.helpdesk.domain.dtos.TecnicoDTO;
import tech.guih58.helpdesk.domain.repositoris.PessoaRepository;
import tech.guih58.helpdesk.domain.repositoris.TecnicoRepository;
import tech.guih58.helpdesk.services.execeptions.DataIntegrityViolationExeception;
import tech.guih58.helpdesk.services.execeptions.ObjectnotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    //Para fazer um hash na senha
    @Autowired
    private BCryptPasswordEncoder encoder;


    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = tecnicoRepository.findById(id);
        return obj.orElseThrow(()-> new ObjectnotFoundException("Objeto não encontrado!"));
    }

    public List<Tecnico> findAll(){
        return tecnicoRepository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return tecnicoRepository.save(newObj);
    }

    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        Optional<Pessoal> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationExeception("CPF já cadastrado no sistema!");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationExeception("Email já cadastrado no sistema!");
        }
    }

    public Tecnico update(Integer id, TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);

        return tecnicoRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if(obj.getChamados().size() > 0){
            throw new DataIntegrityViolationExeception("Tecnico possui chamados, não pode ser excluido");
        }else{
            tecnicoRepository.deleteById(id);
        }

    }
}
