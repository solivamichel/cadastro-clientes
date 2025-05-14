package br.com.solivamichel.clienteapi.service;

import br.com.solivamichel.clienteapi.dto.request.ClienteRequest;
import br.com.solivamichel.clienteapi.dto.response.ClienteResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClienteService {

	ClienteResponse salvar(ClienteRequest request);

	List<ClienteResponse> listarTodos();

	ClienteResponse buscarPorId(Long id);

	ClienteResponse atualizar(Long id, ClienteRequest request);

	void deletar(Long id);

	void salvarImagem(Long id, MultipartFile logotipo);

	byte[] buscarLogotipo(Long id);
}
