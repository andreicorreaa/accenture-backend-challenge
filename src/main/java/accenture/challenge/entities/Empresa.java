package accenture.challenge.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;

    @ManyToMany
    @JoinTable(
            name = "empresa_fornecedor",
            joinColumns = @JoinColumn(name = "id_empresa", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "id_fornecedor", referencedColumnName="id"))
    private Set<Fornecedor> fornecedores = new HashSet<>();

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

    public void addFornecedores(Fornecedor fornecedor) {
        this.fornecedores.add(fornecedor);
        fornecedor.getEmpresaFornecedor().add(this);
    }

    public void removeFornecedor(int fornecedorId) {
        Fornecedor fornecedor = this.fornecedores.stream().filter(f -> f.getId() == fornecedorId).findFirst().orElse(null);
        if (fornecedor != null) {
            this.fornecedores.remove(fornecedor);
            fornecedor.getEmpresaFornecedor().remove(this);
        }
    }
}
