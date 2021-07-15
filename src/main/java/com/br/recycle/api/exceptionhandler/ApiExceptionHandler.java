package com.br.recycle.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.br.recycle.api.exception.BadRequestException;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.exception.EntityNotFoundException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.MethodNotAllowedException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.NotAcceptableException;
import com.br.recycle.api.exception.TokenRefreshException;
import com.br.recycle.api.exception.UnprocessableEntityException;
import com.br.recycle.api.exception.UserInvalidException;
import com.br.recycle.api.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String GENERIC_FINAL_USER_ERROR_MESSAGE
		= "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
				+ "o problema persistir, entre em contato com o administrador do sistema.";
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

	    return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", 
				ex.getRequestURL());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_FINAL_USER_ERROR_MESSAGE)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(
					(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
	
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	@ExceptionHandler({ Exception.class, InternalServerException.class })
	public ResponseEntity<Object> handleUncaughtException(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
		ProblemType problemType = ProblemType.SYSTEM_ERROR;
		String detail = GENERIC_FINAL_USER_ERROR_MESSAGE;

		log.error(ex.getMessage(), ex);
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	/**
	 * Método responsável por tratar a exceção do tipo <b>NO CONTENT - 204</b>,
	 * quando a requisição é realizada com sucesso, mas a base de dados não tem conteúdo.
	 * Essa resposta não tem um conteúdo para apresentar.
	 * @param {@code Exception} - exception
	 * @param {@code WebRequest} - request
	 */
	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<?> handleNoContent(Exception exception, WebRequest request) {		
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Método responsável por tratar a exceção do tipo <b>BAD REQUEST - 400</b>,
	 * quando um valor do campo está inválido.
	 * @param {@code Exception} - exception
	 * @param {@code WebRequest} - request
	 * @return {@code ResponseEntity<Object>} 
	 * 		- retorna um objeto em formato JSON com o retorno 400.
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequest(Exception exception, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.INVALID_DATA;
		String detail = exception.getMessage();
		
		log.error(exception.getMessage(), exception);
		
		Problem problem = createProblemBuilder(httpStatus, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(exception, problem, new HttpHeaders(), httpStatus, request);
	}
	
	/**
	 * Método responsável por tratar a exceção do tipo <b>NOT ACCEPTABLE - 406</b>,
	 * quando uma resposta no servidor não é aceita
	 * @param {@code Exception} - exception
	 * @param {@code WebRequest} - request
	 * @return {@code ResponseEntity<Object>} 
	 * 		- retorna um objeto em formato JSON com o retorno 406.
	 */
	@ExceptionHandler(NotAcceptableException.class)
	public ResponseEntity<Object> handleNotAcceptable(Exception exception, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
		ProblemType problemType = ProblemType.ENTITY_IN_USE;
		String detail = exception.getMessage();
		
		log.error(exception.getMessage(), exception);
		
		Problem problem = createProblemBuilder(httpStatus, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(exception, problem, new HttpHeaders(), httpStatus, request);
	}
	
	/**
	 * Método responsável por tratar a exceção do tipo <b>UNPROCESSABLE ENTITY - 422</b>,
	 * quando uma entidade não é processada pelo servidor
	 * @param {@code Exception} - exception
	 * @param {@code WebRequest} - request
	 * @return {@code ResponseEntity<Object>} 
	 * 		- retorna um objeto em formato JSON com o retorno 422.
	 */
	@ExceptionHandler(UnprocessableEntityException.class)
	public ResponseEntity<Object> handleUnprocessableEntitys(Exception exception, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		ProblemType problemType = ProblemType.UNPROCESSABLE_ENTITY;
		String detail = exception.getMessage();
		
		log.error(exception.getMessage(), exception);
		
		Problem problem = createProblemBuilder(httpStatus, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(exception, problem, new HttpHeaders(), httpStatus, request);
	}
	
	@ExceptionHandler(MethodNotAllowedException.class)
	public ResponseEntity<Object> handleMethodNotAllowed(Exception exception, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
		ProblemType problemType = ProblemType.METHOD_NOT_ALLOWED;
		String detail = exception.getMessage();
		
		log.error(exception.getMessage(), exception);
		
		Problem problem = createProblemBuilder(httpStatus, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(exception, problem, new HttpHeaders(), httpStatus, request);
	}
	
	/**
	 * Método responsável por tratar a exceção do tipo <b>FORBIDDEN - 403</b>,
	 * quando um valor do campo está inválido.
	 * @param {@code Exception} - exception
	 * @param {@code WebRequest} - request
	 * @return {@code ResponseEntity<Object>} 
	 * 		- retorna um objeto em formato JSON com o retorno 403.
	 */
	@ExceptionHandler({ TokenRefreshException.class, UserInvalidException.class })
	public ResponseEntity<Object> handleForbidden(Exception exception, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		ProblemType problemType = ProblemType.ACESS_DENIED;
		String detail = exception.getMessage();
		
		log.error(exception.getMessage(), exception);
		
		Problem problem = createProblemBuilder(httpStatus, problemType, detail)
				.userMessage("O usuário não tem permissão para acessar neste momento.")
				.build();
		
		return handleExceptionInternal(exception, problem, new HttpHeaders(), httpStatus, request);
	}
	
	/*@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleEntityNotFoundException(AccessDeniedException ex, WebRequest request) {

		HttpStatus status = HttpStatus.FORBIDDEN;
		ProblemType problemType = ProblemType.ACESS_DENIED;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.userMessage("Você não possui permissão para executar essa operação.")
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}*/
	
	@ExceptionHandler({ EntityNotFoundException.class, UserNotFoundException.class })
	public ResponseEntity<?> handleEntityNotFoundException(Exception ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTITY_IN_USE;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.BUSINESS_ERROR;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.INVALID_PARAMETER;

		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_FINAL_USER_ERROR_MESSAGE)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.unwrapInvocationTargetException(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request); 
		}
		
		ProblemType problemType = ProblemType.UNREADABLE_MESSAGE;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_FINAL_USER_ERROR_MESSAGE)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.UNREADABLE_MESSAGE;
		String detail = String.format("A propriedade '%s' não existe. "
				+ "Corrija ou remova essa propriedade e tente novamente.", path);

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_FINAL_USER_ERROR_MESSAGE)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.UNREADABLE_MESSAGE;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_FINAL_USER_ERROR_MESSAGE)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}


	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder()
				.timestamp(OffsetDateTime.now())
				.title(status.getReasonPhrase())
				.status(status.value())
				.userMessage(GENERIC_FINAL_USER_ERROR_MESSAGE)
				.build();
		} else if (body instanceof String) {
			body = Problem.builder()
				.timestamp(OffsetDateTime.now())
				.title((String) body)
				.status(status.value())
				.userMessage(GENERIC_FINAL_USER_ERROR_MESSAGE)
				.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, BindingResult bindingResult) {
		ProblemType problemType = ProblemType.INVALID_DATA;
	    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
	    
	    List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
	    		.map(objectError -> {
	    			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
	    			
	    			String name = objectError.getObjectName();
	    			
	    			if (objectError instanceof FieldError) {
	    				name = ((FieldError) objectError).getField();
	    			}
	    			
	    			return Problem.Object.builder()
	    				.name(name)
	    				.userMessage(message)
	    				.build();
	    		})
	    		.collect(Collectors.toList());
	    
	    Problem problem = createProblemBuilder(status, problemType, detail)
	        .userMessage(detail)
	        .objects(problemObjects)
	        .build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
			ProblemType problemType, String detail) {
		
		return Problem.builder()
			.timestamp(OffsetDateTime.now())
			.status(status.value())
			.type(problemType.getUri())
			.title(problemType.getTitle())
			.detail(detail);
	}

	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
	}
	
}