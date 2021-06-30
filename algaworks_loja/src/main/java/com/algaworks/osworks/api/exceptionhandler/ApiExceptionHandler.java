package com.algaworks.osworks.api.exceptionhandler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	//Já se encontra em português, mas para conhecimeto de como traduzir/editar a mensagem.
	@Autowired 
	private MessageSource messageSource;
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontradaException(NegocioException ex, WebRequest request) {
		
		var status = HttpStatus.NOT_FOUND;
		
		var problema = new Problema(status.value(), ex.getMessage());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		
		var status = HttpStatus.BAD_REQUEST;
		
		var problema = new Problema(status.value(), ex.getMessage());
		/*problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());*/
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var campos = new ArrayList<Problema.Campo>();
		
		for(ObjectError error: ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();		//error.getObjectName();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());		//error.getDefaultMessage();
			
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		String titulo = "Um ou mais campos estão inválidos! "
				+ "Faça o preenchimento correto e tente novamente.";
		var problema = new Problema(status.value(), titulo, campos);
		/*problema.setStatus(status.value());
		problema.setTitulo(titulo);
		problema.setDataHora(OffsetDateTime.now());
		problema.setCampos(campos);*/
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}
}
