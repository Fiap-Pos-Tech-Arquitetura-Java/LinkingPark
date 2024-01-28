package br.com.fiap.postech.linkingpark.documents;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("forma_pagamento")
public class FormaPagamento {

    @Id
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
