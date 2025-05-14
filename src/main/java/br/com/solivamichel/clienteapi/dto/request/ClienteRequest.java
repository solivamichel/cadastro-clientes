package br.com.solivamichel.clienteapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteRequest {

	@NotBlank
	@Size(max = 255)
	private String nome;

	@Email
	@NotBlank
	@Size(max = 255)
	private String email;

	private MultipartFile logotipo;

	@NotNull
	private List<LogradouroRequest> logradouros;

}
