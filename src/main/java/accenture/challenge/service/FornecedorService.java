package accenture.challenge.service;

import accenture.challenge.dto.FornecedorDTO;
import accenture.challenge.entities.Fornecedor;
import accenture.challenge.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public FornecedorDTO create(FornecedorDTO fornecedorDTO){
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setNome(fornecedorDTO.getNome());
        fornecedor.setEmail(fornecedorDTO.getEmail());
        fornecedor.setDoc(fornecedorDTO.getDoc());
        fornecedor.setRg(fornecedorDTO.getRg());
        fornecedor.setDat_nasc(fornecedorDTO.getDat_nasc());
        fornecedor.setCep(fornecedorDTO.getCep());

        fornecedorRepository.save(fornecedor);

        fornecedorDTO.setId(fornecedor.getId());

        return fornecedorDTO;
    }

    public FornecedorDTO update(FornecedorDTO fornecedorDTO, int fornecedorId){
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId).get();

        fornecedor.setNome(fornecedorDTO.getNome());
        fornecedor.setEmail(fornecedorDTO.getEmail());
        fornecedor.setDoc(fornecedorDTO.getDoc());
        fornecedor.setRg(fornecedorDTO.getRg());
        fornecedor.setCep(fornecedorDTO.getCep());
        fornecedor.setDat_nasc(fornecedorDTO.getDat_nasc());

        fornecedorRepository.save(fornecedor);

        return fornecedorDTO;
    }

    private FornecedorDTO convert (Fornecedor empresa){
        FornecedorDTO fornecedorDTO = new FornecedorDTO();

        fornecedorDTO.setId(empresa.getId());
        fornecedorDTO.setEmail(empresa.getEmail());
        fornecedorDTO.setNome(empresa.getNome());
        fornecedorDTO.setDoc(empresa.getDoc());
        fornecedorDTO.setDat_nasc(empresa.getDat_nasc());
        fornecedorDTO.setCep(empresa.getCep());


        return fornecedorDTO;
    }

    public List<FornecedorDTO> getAll(){
        return fornecedorRepository.findAll().stream().map(this::convert).collect(Collectors.toList());
    }

    public String delete(int fornecedorId){
        fornecedorRepository.deleteById(fornecedorId);
        return "Deleted";
    }

}
