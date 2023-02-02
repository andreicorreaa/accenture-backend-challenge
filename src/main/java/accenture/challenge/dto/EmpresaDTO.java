package accenture.challenge.dto;

import accenture.challenge.entities.Fornecedor;

import java.util.HashSet;
import java.util.Set;

public class EmpresaDTO {
    private int id;
    private String cep;
    private String nome;
    private String doc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }
}
