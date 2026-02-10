package back.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", foreignKey = @ForeignKey(name = "fk_pessoa_telefone"))
    private Pessoa pessoa;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNumero(){
        return numero;
    }

    public void setNumero(String numero){
        this.numero = numero;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }


    public Pessoa getPessoa(){
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa){
        this.pessoa = pessoa;
    }


}
