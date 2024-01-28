package br.com.fiap.postech.linkingpark.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("veiculo")
public class Veiculo {
    @Id
    private String id;

    private String placa;

    private String modelo;

    private String cor;

    public Veiculo() {
        super();
    }

    public Veiculo(String placa, String modelo, String cor) {
        this();
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ( modelo != null ? ", modelo='" + modelo + '\'' : "" ) +
                ( cor != null ? ", placa='" + cor + '\'' : "" ) +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
