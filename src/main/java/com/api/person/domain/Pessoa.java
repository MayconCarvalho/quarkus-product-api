package com.api.person.domain;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.types.ObjectId;

public class Pessoa extends PanacheMongoEntity {
    public String nome;
    public String email;
    public int idade;

    public Pessoa() {}

    public Pessoa(String nome, String email, int idade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }
}
