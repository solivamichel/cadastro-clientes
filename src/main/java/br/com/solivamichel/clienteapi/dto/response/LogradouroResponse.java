package br.com.solivamichel.clienteapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogradouroResponse {

	private Long id;
	private String rua;
	private String cidade;
	private String estado;
}