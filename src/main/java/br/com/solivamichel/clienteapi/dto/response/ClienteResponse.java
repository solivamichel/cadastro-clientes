package br.com.solivamichel.clienteapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteResponse {
	private Long id;
	private String nome;
	private String email;
	private List<LogradouroResponse> logradouros;
}
