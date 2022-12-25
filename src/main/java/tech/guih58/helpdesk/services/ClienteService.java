package tech.guih58.helpdesk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.guih58.helpdesk.domain.Cliente;
import tech.guih58.helpdesk.domain.Pessoal;
import tech.guih58.helpdesk.domain.dtos.ClienteDTO;
import tech.guih58.helpdesk.domain.repositoris.ClienteRepository;
import tech.guih58.helpdesk.domain.repositoris.PessoaRepository;
import tech.guih58.helpdesk.services.execeptions.DataIntegrityViolationExeception;
import tech.guih58.helpdesk.services.execeptions.ObjectnotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    //Para fazer um hash na senha
    @Autowired
    private BCryptPasswordEncoder encoder;


    public Cliente findById(Integer id){
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(()-> new ObjectnotFoundException("Objeto não encontrado!"));
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return clienteRepository.save(newObj);
    }

    private void validaPorCpfEEmail(ClienteDTO objDTO) {
        Optional<Pessoal> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationExeception("CPF já cadastrado no sistema!");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationExeception("Email já cadastrado no sistema!");
        }
    }

    public Cliente update(Integer id, ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        if(!objDTO.getSenha().equals(oldObj.getSenha())){
            objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        }
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);

        return clienteRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if(obj.getChamados().size() > 0){
            throw new DataIntegrityViolationExeception("Cliente possui chamados, não pode ser excluido");
        }else{
            clienteRepository.deleteById(id);
        }

    }
}
