package com.api.person.infrastructure.repository;

import com.api.person.domain.Pessoa;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaRepository implements PanacheMongoRepository<Pessoa> {
    // Métodos customizados podem ser adicionados aqui
}
