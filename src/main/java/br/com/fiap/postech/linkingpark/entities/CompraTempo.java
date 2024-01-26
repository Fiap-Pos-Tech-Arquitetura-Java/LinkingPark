package br.com.fiap.postech.linkingpark.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_compra_tempo")
public class CompraTempo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    private Motorista motorista;

    @ManyToOne
    private Veiculo veiculo;

    private String status;
    private String tipo;
    private Integer tempoEmMinutos;

    @ManyToOne
    private FormaPagamento formaPagamentoPreferencial;

    public CompraTempo() {
        super();
    }

    public CompraTempo(Motorista motorista, Veiculo veiculo, String status, String tipo,
                       Integer tempoEmMinutos, FormaPagamento formaPagamentoPreferencial, LocalDateTime horaCompra) {
        this();
        this.motorista = motorista;
        this.veiculo = veiculo;
        this.status = status;
        this.tipo = tipo;
        this.tempoEmMinutos = tempoEmMinutos;
        this.formaPagamentoPreferencial = formaPagamentoPreferencial;
        this.horaCompra = horaCompra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompraTempo compraTempo = (CompraTempo) o;

        return id.equals(compraTempo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "\r\nCompraTempo{" +
                "\r\n\tid=" + id +
                "\r\n\t, motorista=" + motorista +
                "\r\n\t, veiculo=" + veiculo +
                "\r\n\t, status='" + status + '\'' +
                "\r\n\t, tipo='" + tipo + '\'' +
                "\r\n\t, tempoEmMinutos=" + tempoEmMinutos +
                "\r\n\t, formaPagamentoPreferencial=" + formaPagamentoPreferencial +
                "\r\n\t, horaCompra=" + horaCompra +
                "\r\n}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime horaCompra;

    public Integer getTempoEmMinutos() {
        return tempoEmMinutos;
    }

    public void setTempoEmMinutos(Integer tempoEmMinutos) {
        this.tempoEmMinutos = tempoEmMinutos;
    }

    public void adicionaUmaHora() {
        setTempoEmMinutos(getTempoEmMinutos() + 60);
    }

    public FormaPagamento getFormaPagamentoPreferencial() {
        return formaPagamentoPreferencial;
    }

    public void setFormaPagamentoPreferencial(FormaPagamento formaPagamentoPreferencial) {
        this.formaPagamentoPreferencial = formaPagamentoPreferencial;
    }

    public LocalDateTime getHoraCompra() {
        return horaCompra;
    }

    public void setHoraCompra(LocalDateTime horaCompra) {
        this.horaCompra = horaCompra;
    }
}
