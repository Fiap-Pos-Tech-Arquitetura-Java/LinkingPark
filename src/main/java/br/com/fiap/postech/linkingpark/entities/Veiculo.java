package br.com.fiap.postech.linkingpark.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_veiculos")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
