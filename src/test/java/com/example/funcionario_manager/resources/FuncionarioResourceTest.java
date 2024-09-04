package com.example.funcionario_manager.resources;


import com.example.funcionario_manager.entities.Endereco;
import com.example.funcionario_manager.entities.Funcionario;
import com.example.funcionario_manager.repositories.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Testes do controlador de Funcionarios")
class FuncionarioResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    @Test
    void deveCriarNovoFuncionarioComSucesso() throws Exception {
        Funcionario funcionario = new Funcionario(null, "12345678901", "João Silva", "Desenvolvedor",
                new Endereco(null, "Rua A", "Cidade A", "Estado A", null));

        mockMvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.nome").value("João Silva"));

    }

    @Test
    void deveRetornarBadRequestQuandoCpfInvalido() throws Exception {
        Funcionario funcionario = new Funcionario(null, "123", "João Silva", "Desenvolvedor",
                new Endereco(null, "Rua A", "Cidade A", "Estado A", null));

        mockMvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarFuncionariosPaginados() throws Exception {
        funcionarioRepository.save(new Funcionario(null, "12345678901", "João Silva", "Desenvolvedor",
                new Endereco(null, "Rua A", "Cidade A", "Estado A", null)));

        mockMvc.perform(get("/funcionarios?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].cpf").value("12345678901"));
    }


    @Test
    void deveBuscarFuncionarioPorCpf() throws Exception {
        funcionarioRepository.save(new Funcionario(null, "12345678901", "João Silva", "Desenvolvedor",
                new Endereco(null, "Rua A", "Cidade A", "Estado A", null)));

        mockMvc.perform(get("/funcionarios/12345678901"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value("12345678901"));
    }


    @Test
    void deveRetornarNotFoundQuandoBuscarFuncionarioInexistente() throws Exception {
        mockMvc.perform(get("/funcionarios/12345678901"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarFuncionarioComSucesso() throws Exception {
        Funcionario funcionario = funcionarioRepository.save(new Funcionario(null, "12345678901", "João Silva", "Desenvolvedor",
                new Endereco(null, "Rua A", "Cidade A", "Estado A", null)));

        funcionario.setNome("João Atualizado");

        mockMvc.perform(put("/funcionarios/{cpf}", funcionario.getCpf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Atualizado"));
    }


    @Test
    void deveDeletarFuncionarioComSucesso() throws Exception {
        funcionarioRepository.save(new Funcionario(null, "12345678901", "João Silva", "Desenvolvedor",
                new Endereco(null, "Rua A", "Cidade A", "Estado A", null)));


        mockMvc.perform(delete("/funcionarios/{cpf}", "12345678901"))
                .andExpect(status().isNoContent());
    }


    @Test
    void deveBuscarFuncionariosPorNome() throws Exception {
        funcionarioRepository.save(new Funcionario(null, "12345678901", "João Silva", "Desenvolvedor",
                new Endereco(null, "Rua A", "Cidade A", "Estado A", null)));

        mockMvc.perform(get("/funcionarios/nome")
                        .param("nome", "João"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("João Silva"));
    }


    @Test
    void deveBuscarFuncionariosPorCargo() throws Exception {
        funcionarioRepository.save(new Funcionario(null, "12345678901", "João Silva", "Desenvolvedor",
                new Endereco(null, "Rua A", "Cidade A", "Estado A", null)));

        mockMvc.perform(get("/funcionarios/cargo")
                        .param("cargo", "Desenvolvedor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].cargo").value("Desenvolvedor"));
    }


    @Test
    void deveBuscarFuncionariosPorCidade() throws Exception {
        funcionarioRepository.save(new Funcionario(null, "12345678901", "João Silva", "Desenvolvedor",
                new Endereco(null, "Rua A", "Cidade A", "Estado A", null)));

        mockMvc.perform(get("/funcionarios/cidade")
                        .param("cidade", "Cidade A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].endereco.cidade").value("Cidade A"));
    }
}

