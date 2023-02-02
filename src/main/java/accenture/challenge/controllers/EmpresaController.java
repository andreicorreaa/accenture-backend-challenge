package accenture.challenge.controllers;

import accenture.challenge.dto.ApiCepDTO;
import accenture.challenge.dto.EmpresaDTO;
import accenture.challenge.service.ApiCepService;
import accenture.challenge.service.EmpresaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/empresa", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ApiCepService apiCepService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<EmpresaDTO> createEmpresa(@RequestBody EmpresaDTO empresaDTO) throws JsonProcessingException {
        String apiCep = apiCepService.findCEP(empresaDTO.getCep());

        if(apiCep.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        List<ApiCepDTO> ceps = new ObjectMapper().readValue(apiCep, new TypeReference<List<ApiCepDTO>>(){});

        if(ceps.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        empresaService.create(empresaDTO);

        return new ResponseEntity<>(empresaDTO, HttpStatus.OK);
    }

    @PutMapping("/{empresaId}")
    @ResponseBody
    public ResponseEntity<EmpresaDTO> updateEmpresa(@PathVariable("empresaId") int empresaId,
                               @RequestBody EmpresaDTO empresaDTO) throws JsonProcessingException {
        String apiCep = apiCepService.findCEP(empresaDTO.getCep());

        if(apiCep.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        List<ApiCepDTO> ceps = new ObjectMapper().readValue(apiCep, new TypeReference<List<ApiCepDTO>>(){});

        if(ceps.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        empresaService.update(empresaDTO, empresaId);

        return new ResponseEntity<>(empresaDTO, HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<EmpresaDTO>> getAll() {
        return new ResponseEntity<>(empresaService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{empresaId}")
    @ResponseBody
    public ResponseEntity<String> deleteEmpresa(@PathVariable("empresaId") int empresaId) {
        return new ResponseEntity<>(empresaService.delete(empresaId), HttpStatus.OK);
    }

    @PostMapping("/{empresaId}/fornecedor/{fornecedorId}")
    @ResponseBody
    public ResponseEntity<EmpresaDTO> addFornecedor(
            @PathVariable("empresaId") int empresaId,
            @PathVariable("fornecedorId") int fornecedorId){
        return new ResponseEntity<>(empresaService.addFornecedor(empresaId, fornecedorId), HttpStatus.OK);
    }

    @DeleteMapping("/{empresaId}/fornecedor/{fornecedorId}")
    @ResponseBody
    public ResponseEntity<EmpresaDTO> deleteFornecedor(
            @PathVariable("empresaId") int empresaId,
            @PathVariable("fornecedorId") int fornecedorId) {
        return new ResponseEntity<>(empresaService.removeFornecedor(empresaId, fornecedorId), HttpStatus.OK);
    }

}
