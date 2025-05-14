package br.com.solivamichel.clienteapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogradouroRequest {

	@NotBlank
	private String rua;

	@NotBlank
	private String cidade;

	@NotBlank
	private String estado;
}
