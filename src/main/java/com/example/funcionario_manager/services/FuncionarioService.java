package com.example.funcionario_manager.services;

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

import java.util.Optional;

@Service
public class FuncionarioService {

    private static final String FUNCIONARIO_NAO_ENCONTRADO = "Funcionario não encontrado.";

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    @Transactional
    public Funcionario criaFuncionario(Funcionario funcionario) {
        Optional<Funcionario> funcionario1 = funcionarioRepository.findById(funcionario.getCpf());

        if(funcionario1.isPresent()){
            throw new FuncionarioJaExisteException();
        }else{
            return funcionarioRepository.save(funcionario);
        }

    }


    @Transactional(readOnly = true)
    public Page<Funcionario> getFuncionariosPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return funcionarioRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    public Funcionario getFuncionarioByCpf(String cpf) {
        return funcionarioRepository.findById(cpf).orElseThrow(() -> new ResourceNotFoundException(FUNCIONARIO_NAO_ENCONTRADO));
    }


    @Transactional
    public Funcionario atualizaFuncionarioByCpf(String cpf, Funcionario funcionarioAtualizado) {
        Funcionario funcionario = funcionarioRepository.findById(cpf).orElseThrow(() -> new ResourceNotFoundException(FUNCIONARIO_NAO_ENCONTRADO));

        funcionario.setNome(funcionarioAtualizado.getNome());
        funcionario.setCargo(funcionarioAtualizado.getCargo());
        funcionario.setEndereco(funcionarioAtualizado.getEndereco());

        return funcionarioRepository.save(funcionario);
    }


    @Transactional
    public void deletaFuncionarioByCpf(String cpf) {
        funcionarioRepository.deleteById(cpf);
    }


    @Transactional(readOnly = true)
    public Page<Funcionario> buscaFuncionariosPorNome(String nome, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return funcionarioRepository.findByNamePaginados(nome, pageable);
    }


    @Transactional(readOnly = true)
    public Page<Funcionario> buscaFuncionariosPorCargo(String cargo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return funcionarioRepository.findByCargoPaginados(cargo, pageable);
    }


    @Transactional(readOnly = true)
    public Page<Funcionario> buscaFuncionariosPorCidade(String cidade, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return funcionarioRepository.findByCidadePaginados(cidade, pageable);
    }


}






