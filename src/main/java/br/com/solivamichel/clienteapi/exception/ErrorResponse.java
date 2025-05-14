package br.com.solivamichel.clienteapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private Object message;
}
