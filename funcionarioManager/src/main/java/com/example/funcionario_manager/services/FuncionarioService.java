package com.example.funcionario_manager.services;

import com.example.funcionario_manager.dtos.funcionario.FuncionarioRequestDTO;
import com.example.funcionario_manager.dtos.funcionario.FuncionarioResponseDTO;
import com.example.funcionario_manager.dtos.funcionario.FuncionarioUpdateDTO;
import com.example.funcionario_manager.entities.Endereco;
import com.example.funcionario_manager.entities.Funcionario;
import com.example.funcionario_manager.exceptions.FuncionarioJaExisteException;
import com.example.funcionario_manager.exceptions.ResourceNotFoundException;
import com.example.funcionario_manager.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    @Transactional
    public FuncionarioResponseDTO criaFuncionario(FuncionarioRequestDTO dto) {

        if (funcionarioRepository.existsByCpf(dto.cpf())) {
            throw new FuncionarioJaExisteException("Funcionário Já existe");
        }

        Funcionario funcionario = new Funcionario();

        funcionario.setCpf(dto.cpf());
        funcionario.setNome(dto.nome());
        funcionario.setCargo(dto.cargo());

        Endereco endereco = new Endereco();
        endereco.setRua(dto.enderecoRequestDTO().rua());
        endereco.setCidade(dto.enderecoRequestDTO().cidade());
        endereco.setEstado(dto.enderecoRequestDTO().estado());

        funcionario.setEndereco(endereco);

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        return new FuncionarioResponseDTO(funcionarioSalvo.getId(), funcionarioSalvo.getCpf(), funcionarioSalvo.getNome(), funcionarioSalvo.getCargo());

    }


    @Transactional(readOnly = true)
    public Page<FuncionarioResponseDTO> getFuncionariosPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return funcionarioRepository.buscaTodosPaginados(pageable);
    }


    @Transactional(readOnly = true)
    public FuncionarioResponseDTO getFuncionarioByCpf(String cpf) {
        return funcionarioRepository.findDTOByCpf(cpf).orElseThrow(() -> new ResourceNotFoundException("Usuário não existe."));
    }


    @Transactional
    public FuncionarioResponseDTO atualizaFuncionarioByCpf(String cpf, FuncionarioUpdateDTO funcionarioAtualizado) {

        Funcionario funcionario = funcionarioRepository.findByCpf(cpf).orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado."));

        funcionario.setNome(funcionarioAtualizado.nome());
        funcionario.setCargo(funcionarioAtualizado.cargo());

        Funcionario funcionarioSaved = funcionarioRepository.save(funcionario);

        return new FuncionarioResponseDTO(funcionarioSaved.getId(), funcionarioSaved.getCpf(), funcionarioSaved.getNome(), funcionarioSaved.getCargo());

    }


    @Transactional
    public void deletaFuncionarioByCpf(String cpf) {
        funcionarioRepository.deleteByCpf(cpf);
    }


    @Transactional(readOnly = true)
    public Page<FuncionarioResponseDTO> buscaFuncionariosPorNome(String nome, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return funcionarioRepository.findByNamePaginados(nome, pageable);
    }


    @Transactional(readOnly = true)
    public Page<FuncionarioResponseDTO> buscaFuncionariosPorCargo(String cargo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return funcionarioRepository.findByCargoPaginados(cargo, pageable);
    }


    @Transactional(readOnly = true)
    public Page<FuncionarioResponseDTO> buscaFuncionariosPorCidade(String cidade, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return funcionarioRepository.findByCidadePaginados(cidade, pageable);
    }


}






