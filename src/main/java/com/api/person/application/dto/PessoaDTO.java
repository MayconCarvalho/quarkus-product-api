package com.api.person.application.dto;

public class PessoaDTO {
    public String id;
    public String nome;
    public String email;
    public int idade;

    public PessoaDTO() {}

    public PessoaDTO(String id, String nome, String email, int idade) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }
}
