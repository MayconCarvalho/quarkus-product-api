package com.api.person.application.service;

import com.api.person.application.dto.PessoaDTO;
import com.api.person.application.mapper.PessoaMapper;
import com.api.person.domain.Pessoa;
import com.api.person.infrastructure.repository.PessoaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PessoaService {
    @Inject
    PessoaRepository pessoaRepository;

    public PessoaDTO cadastrar(PessoaDTO dto) {
        Pessoa pessoa = PessoaMapper.toEntity(dto);
        pessoaRepository.persist(pessoa);
        return PessoaMapper.toDTO(pessoa);
    }

    public List<PessoaDTO> listarTodos() {
        return pessoaRepository.listAll().stream()
                .map(PessoaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
