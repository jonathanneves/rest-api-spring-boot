package com.algaworks.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.osworks.domain.exception.NegocioException;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;

//Não deve colocar Regras de Negócio de alteração no Banco de Dados Controller, como acesso ao Repositorio para Update Delete or Insert.
@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	 
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if(clienteExistente != null && !clienteExistente.equals(cliente)) { //cliente é diferente do cliente encontrado com mesmo email?
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
		}
			
		return clienteRepository.save(cliente);	
	}
	
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
}
