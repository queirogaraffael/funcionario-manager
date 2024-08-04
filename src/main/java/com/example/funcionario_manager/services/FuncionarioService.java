package com.example.funcionario_manager.services;

import com.example.funcionario_manager.entities.Endereco;
import com.example.funcionario_manager.entities.Funcionario;
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
    public Funcionario criaFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }


    @Transactional(readOnly = true)
    public Page<Funcionario> getFuncionariosPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return funcionarioRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    public Funcionario getFuncionarioById(Long id) {
        return funcionarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionario com este ID não encontrado."));
    }


    @Transactional
    public Funcionario atualizaFuncionarioById(Long id, Funcionario novoFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionario com este ID não encontrado."));

        funcionario.setNome(novoFuncionario.getNome());
        funcionario.setCargo(novoFuncionario.getCargo());
        funcionario.setEndereco(novoFuncionario.getEndereco());

        return funcionarioRepository.save(funcionario);
    }


    @Transactional
    public void deletaFuncionarioById(Long id) {
        funcionarioRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public Endereco getEnderecoDeFuncionarioById(Long idFuncionario) {
        return funcionarioRepository.findEnderecoByFuncionarioId(idFuncionario);
    }


    @Transactional
    public Endereco atualizaEnderecoById(Long idFuncionario, Endereco novoEndereco) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario).orElseThrow(() -> new ResourceNotFoundException("Funcionario com este Id não encontrado."));

        funcionario.setEndereco(novoEndereco);

        funcionarioRepository.save(funcionario);

        return novoEndereco;
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






