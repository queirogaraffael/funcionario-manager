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
        Optional<Funcionario> funcionario1 = funcionarioRepository.findByCpf(funcionario.getCpf());

        if (funcionario1.isPresent()) {
            throw new FuncionarioJaExisteException();
        } else {
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
        Optional<Funcionario> funcionario = funcionarioRepository.findByCpf(cpf);

        return funcionario.orElse(null);

    }


    @Transactional
    public Funcionario atualizaFuncionarioByCpf(String cpf, Funcionario funcionarioAtualizado) {
        Optional<Funcionario> funcionario = funcionarioRepository.findByCpf(cpf);

        if(funcionario.isPresent()){
            Funcionario funcionario1 = funcionario.get();

            funcionario1.setNome(funcionarioAtualizado.getNome());
            funcionario1.setCargo(funcionarioAtualizado.getCargo());
            funcionario1.setEndereco(funcionarioAtualizado.getEndereco());

            return funcionarioRepository.save(funcionario1);
        }else{
            throw  new ResourceNotFoundException(FUNCIONARIO_NAO_ENCONTRADO);
        }


    }


    @Transactional
    public void deletaFuncionarioByCpf(String cpf) {
        funcionarioRepository.deleteByCpf(cpf);
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






