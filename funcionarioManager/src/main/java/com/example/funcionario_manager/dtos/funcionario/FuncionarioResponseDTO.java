package com.example.funcionario_manager.dtos.funcionario;

import com.example.funcionario_manager.dtos.endereco.EnderecoResponseDTO;

public record FuncionarioResponseDTO(Long id, String cpf, String nome, String cargo) {
}
