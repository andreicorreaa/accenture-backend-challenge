package accenture.challenge.service;

import accenture.challenge.dto.ApiCepDTO;
import accenture.challenge.dto.EmpresaDTO;
import accenture.challenge.entities.Empresa;
import accenture.challenge.entities.Fornecedor;
import accenture.challenge.repositories.EmpresaRepository;
import accenture.challenge.repositories.FornecedorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ApiCepService apiCepService;

    public EmpresaDTO create(EmpresaDTO empresaDTO){
        Empresa empresa = new Empresa();

        empresa.setNome(empresaDTO.getNome());
        empresa.setCep(empresaDTO.getCep());
        empresa.setDoc(empresaDTO.getDoc());

        empresaRepository.save(empresa);

        empresaDTO.setId(empresa.getId());

        return empresaDTO;
    }

    public EmpresaDTO update(EmpresaDTO empresaDTO, int empresaId){
        Empresa empresa = empresaRepository.findById(empresaId).get();
        empresa.setDoc(empresaDTO.getDoc());
        empresa.setCep(empresaDTO.getCep());
        empresa.setNome(empresaDTO.getNome());
        empresaRepository.save(empresa);

        return empresaDTO;
    }

    private EmpresaDTO convert (Empresa empresa){
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setId(empresa.getId());
        empresaDTO.setCep(empresa.getCep());
        empresaDTO.setNome(empresa.getNome());
        empresaDTO.setDoc(empresa.getDoc());

        return empresaDTO;
    }

    public List<EmpresaDTO> getAll(){
        return empresaRepository.findAll().stream().map(this::convert).collect(Collectors.toList());
    }

    public String delete(int empresaId){
        empresaRepository.deleteById(empresaId);
        return "Deleted";
    }

    public EmpresaDTO addFornecedor(int empresaId, int fornecedorId){
        EmpresaDTO empresaDTO = new EmpresaDTO();
        try{
            Empresa empresa = empresaRepository.findById(empresaId).get();
            Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId).get();

            String apiCep = apiCepService.findCEP(empresaDTO.getCep());

            if(apiCep.isEmpty()) return empresaDTO;

            List<ApiCepDTO> ceps = new ObjectMapper().readValue(apiCep, new TypeReference<List<ApiCepDTO>>(){});

            if(ceps.isEmpty()) return empresaDTO;

            if((!ceps.get(0).getUf().isEmpty() && ceps.get(0).getUf().equals("PR"))){
                Calendar dateOfBirth = new GregorianCalendar();
                dateOfBirth.setTime(fornecedor.getDat_nasc());

                Calendar today = Calendar.getInstance();
                int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

                dateOfBirth.add(Calendar.YEAR, age);
                if (today.before(dateOfBirth)) {
                    age--;
                }

                if(age < 18) return empresaDTO;
            }

            empresa.addFornecedores(fornecedor);

            empresaRepository.save(empresa);

            empresaDTO = convert(empresa);
        }catch (Exception e){
            System.out.println(e);
        }

        return empresaDTO;
    }

    public EmpresaDTO removeFornecedor(int empresaId, int fornecedorId){
        EmpresaDTO empresaDTO = new EmpresaDTO();
        try{
            Empresa empresa = empresaRepository.findById(empresaId).get();

            empresa.removeFornecedor(fornecedorId);

            empresaRepository.save(empresa);

            empresaDTO = convert(empresa);
        }catch (Exception e){
            System.out.println(e);
        }

        return empresaDTO;
    }
}
