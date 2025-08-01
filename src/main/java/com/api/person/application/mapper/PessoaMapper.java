package com.api.person.application.mapper;

import com.api.person.domain.Pessoa;
import com.api.person.application.dto.PessoaDTO;

public class PessoaMapper {
    public static PessoaDTO toDTO(Pessoa pessoa) {
        if (pessoa == null) return null;
        return new PessoaDTO(
            pessoa.id != null ? pessoa.id.toString() : null,
            pessoa.nome,
            pessoa.email,
            pessoa.idade
        );
    }

    public static Pessoa toEntity(PessoaDTO dto) {
        if (dto == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.nome = dto.nome;
        pessoa.email = dto.email;
        pessoa.idade = dto.idade;
        return pessoa;
    }
}
