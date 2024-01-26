package br.com.fiap.postech.linkingpark.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_forma_pagamento")
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public FormaPagamento() {
        super();
    }

    public FormaPagamento(String nome) {
        this();
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormaPagamento formaPagamento = (FormaPagamento) o;

        return id.equals(formaPagamento.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "FormaPagamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

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
}
