package br.com.solivamichel.clienteapi.controller;

import br.com.solivamichel.clienteapi.dto.request.ClienteRequest;
import br.com.solivamichel.clienteapi.dto.response.ClienteResponse;
import br.com.solivamichel.clienteapi.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

	private final ClienteService clienteService;

	@PostMapping
	public ResponseEntity<ClienteResponse> salvarCliente(@Valid @RequestBody ClienteRequest request) {
		ClienteResponse response = clienteService.salvar(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<ClienteResponse>> listarTodos() {
		List<ClienteResponse> clientes = clienteService.listarTodos();
		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
		ClienteResponse response = clienteService.buscarPorId(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponse> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
		ClienteResponse response = clienteService.atualizar(id, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
		clienteService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/logotipo")
	public ResponseEntity<byte[]> buscarLogotipo(@PathVariable Long id) {
		byte[] imagem = clienteService.buscarLogotipo(id);
		if (imagem == null || imagem.length == 0) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.body(imagem);
	}

	@PostMapping("/{id}/logotipo")
	public ResponseEntity<Void> salvarLogotipo(@PathVariable Long id, @RequestParam("logotipo") MultipartFile logotipo) {
		clienteService.salvarImagem(id, logotipo);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}