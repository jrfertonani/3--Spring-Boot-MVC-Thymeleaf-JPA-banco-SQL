package back.model;

public enum Cargo {
    JUNIOR("Junior"),
    PLENO("Pleno"),
    SENIOR("Senior");

    private String nome;
    private String valor;

    private Cargo(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getValor(){
        return valor;
    }

    public void setValor(String valor){
        this.valor = this.name();
    }

}
