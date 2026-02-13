package back.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;

import java.io.Serializable;
import java.util.List;

@Entity
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio!")
    @NotNull(message = "Nome não pode ser nulo!")
    private String nome;

    @NotEmpty(message = "Sobre nome não pode ser vazio!")
    @NotNull(message = "Sobre nome não pode ser nulo!")
    private String sobrenome;

    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String uf;
    private String ibge;

    private String sexopessoa;

    @Min(value = 18, message = "Idade inválida!")
    private int idade;

    @OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Telefone> telefones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public List<Telefone> getTelefones(){
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones){
        this.telefones = telefones;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getSexopessoa(){
        return sexopessoa;
    }

    public void setSexopessoa(String sexopessoa){
        this.sexopessoa = sexopessoa;
    }
}
