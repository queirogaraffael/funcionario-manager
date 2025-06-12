package com.example.funcionario_manager.dtos.funcionario;

import com.example.funcionario_manager.dtos.endereco.EnderecoRequestDTO;

public record FuncionarioRequestDTO(String cpf, String nome, String cargo, EnderecoRequestDTO enderecoRequestDTO) {
}
