package br.com.fiap.postech.linkingpark.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("motorista")
public class Motorista {

    @Id
    private String id;
    private String senha;
    private Long cpf;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private String sexo;
    @DBRef
    private List<Veiculo> veiculos;
    @DBRef
    private FormaPagamento formaPagamentoPreferencial;

    public Motorista() {
        super();
    }

    public Motorista(String senha, Long cpf, String nome, String email, String telefone,
                     LocalDate dataNascimento, String sexo, FormaPagamento formaPagamentoPreferencial) {
        this.senha = senha;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.formaPagamentoPreferencial = formaPagamentoPreferencial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Motorista motorista = (Motorista) o;

        return id.equals(motorista.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Motorista{" +
                "id=" + id +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", sexo='" + sexo + '\'' +
                ", formaPagamentoPreferencial=" + formaPagamentoPreferencial +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public FormaPagamento getFormaPagamentoPreferencial() {
        return formaPagamentoPreferencial;
    }

    public void setFormaPagamentoPreferencial(FormaPagamento formaPagamentoPreferencial) {
        this.formaPagamentoPreferencial = formaPagamentoPreferencial;
    }
}
