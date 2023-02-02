package accenture.challenge.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;

    @ManyToMany(mappedBy = "fornecedores")
    private Set<Empresa> empresaFornecedor = new HashSet<>();

    private String nome;
    private String email;
    private String doc;
    private String rg;
    private String cep;
    private Date dat_nasc;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getDat_nasc() {
        return dat_nasc;
    }

    public void setDat_nasc(Date dat_nasc) {
        this.dat_nasc = dat_nasc;
    }

    public Set<Empresa> getEmpresaFornecedor() {
        return empresaFornecedor;
    }

    public void setEmpresaFornecedor(Set<Empresa> empresaFornecedor) {
        this.empresaFornecedor = empresaFornecedor;
    }
}
