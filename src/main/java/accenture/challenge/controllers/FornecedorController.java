package accenture.challenge.controllers;

import accenture.challenge.dto.ApiCepDTO;
import accenture.challenge.dto.FornecedorDTO;
import accenture.challenge.helpers.DocHelpers;
import accenture.challenge.service.ApiCepService;
import accenture.challenge.service.FornecedorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
@RequestMapping(value = "/fornecedor", produces = MediaType.APPLICATION_JSON_VALUE)
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private ApiCepService apiCepService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<FornecedorDTO> create(@RequestBody FornecedorDTO fornecedorDTO) throws JsonProcessingException {
        String apiCep = apiCepService.findCEP(fornecedorDTO.getCep());

        if(apiCep.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        List<ApiCepDTO> ceps = new ObjectMapper().readValue(apiCep, new TypeReference<List<ApiCepDTO>>(){});

        if(ceps.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        DocHelpers docHelpers = new DocHelpers();

        //devido ao tempo, deixei essa regra no controller
        if(docHelpers.isCPF(fornecedorDTO.getDoc())){
            if(fornecedorDTO.getDat_nasc() == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if(fornecedorDTO.getRg() == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        fornecedorService.create(fornecedorDTO);

        return new ResponseEntity<>(fornecedorDTO, HttpStatus.OK);
    }

    @PutMapping("/{fornecedorId}")
    @ResponseBody
    public FornecedorDTO update(@PathVariable("fornecedorId") int fornecedorId,
                                    @RequestBody FornecedorDTO fornecedorDTO) {
        return fornecedorService.update(fornecedorDTO, fornecedorId);
    }

    @GetMapping
    @ResponseBody
    public List<FornecedorDTO> getAll() {
        return fornecedorService.getAll();
    }

    @DeleteMapping("/{fornecedorId}")
    @ResponseBody
    public String delete(@PathVariable("fornecedorId") int fornecedorId) {
        return fornecedorService.delete(fornecedorId);
    }
}
