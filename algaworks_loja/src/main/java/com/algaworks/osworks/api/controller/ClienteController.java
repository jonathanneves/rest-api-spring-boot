package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroCliente;
	
	@GetMapping
	public List<Cliente> listar() {
		//return clienteRepository.findByNome("João da Silva");
		//return clienteRepository.findByNomeContaining("Mar");
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {	//@PathVariable: pega id do GetMapping {clienteId}
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		//Configurando o Status, se cliente extiste 200 OK, senão 404 NOT FOUND
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) //Troca Status HTTP de 200 OK para 201 CREATED
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return cadastroCliente.salvar(cliente);
	}
	
	/*
	 * @Valid ativa validação do Model.
	 * @PathVariable é o tipo de dado mapeado após "/". 
	 * RequestBody é qual corpo da classe
	 */
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente){ 
		
		//Id cliente existe? Senão existe retorna Erro 404 NOT FOUND
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteId); //Para atualizar o cliente atual, e não inserir um novo. Pois id está vazio
		cadastroCliente.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		
		//Id cliente existe? Senão existe retorna Erro 404 NOT FOUND
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroCliente.excluir(clienteId);
		return ResponseEntity.noContent().build(); //Status 204 NO CONTENT
	}
	/* 
	 * 1- COM ENTITY MANAGER
	@PersistenceContext
	private EntityManager manager;
	
	@GetMapping("/clientes")
	public List<Cliente> listar() {
		return manager.createQuery("from Cliente", Cliente.class)
				.getResultList();
	}
	*/
	
	/*
	 * 2- SEM BANCO DE DADOS
	@GetMapping("/clientes")
	public List<Cliente> listar() {
	var cliente1 = new Cliente(1L,"João das Neves","joaoteste@gmail.com.br","34 99999-1111");
	var cliente2 = new Cliente(2L,"Maria","mariateste@gmail.com.br","34 88888-1111");
	var cliente3 = new Cliente(3L,"Paul","pauloteste@gmail.com.br","34 77777-1111");
	
	return Arrays.asList(cliente1, cliente2, cliente3);
	}
	*/
	
}
