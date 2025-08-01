package com.api.person.presentation.controller;

import com.api.person.application.dto.PessoaDTO;
import com.api.person.application.service.PessoaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaController {
    @Inject
    PessoaService pessoaService;

    @POST
    public PessoaDTO cadastrar(PessoaDTO dto) {
        return pessoaService.cadastrar(dto);
    }

    @GET
    public List<PessoaDTO> listarTodos() {
        return pessoaService.listarTodos();
    }
}
