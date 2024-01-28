package br.com.fiap.postech.linkingpark.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("compra_tempo")
public class CompraTempo {

    @Id
    private String id;
    @DBRef
    private Motorista motorista;

    @DBRef
    private Veiculo veiculo;

    private String status;
    private String tipo;
    private Integer tempoEmMinutos;

    private Double tarifa;

    @DBRef
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Double getTarifa() {
        return tarifa;
    }

    public void setTarifa(Double tarifa) {
        this.tarifa = tarifa;
    }

    public Double getValorTotalPago() {
        if (getTarifa() != null) {
            return getTarifa() * getTempoEmMinutos();
        }
        return null;
    }
}
