package br.com.solivamichel.clienteapi.service;

import br.com.solivamichel.clienteapi.dto.request.ClienteRequest;
import br.com.solivamichel.clienteapi.dto.response.ClienteResponse;
import br.com.solivamichel.clienteapi.dto.response.LogradouroResponse;
import br.com.solivamichel.clienteapi.entity.Cliente;
import br.com.solivamichel.clienteapi.entity.Logradouro;
import br.com.solivamichel.clienteapi.exception.BusinessException;
import br.com.solivamichel.clienteapi.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;

	@Override
	@Transactional
	public ClienteResponse salvar(ClienteRequest request) {
		validarEmailDuplicado(request.getEmail());
		Cliente cliente = construirCliente(request);
		Cliente salvo = clienteRepository.save(cliente);
		return toResponse(salvo);
	}

	@Override
	public List<ClienteResponse> listarTodos() {
		return clienteRepository.findAll()
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public ClienteResponse buscarPorId(Long id) {
		Cliente cliente = buscarClienteOuErro(id);
		return toResponse(cliente);
	}

	@Override
	@Transactional
	public ClienteResponse atualizar(Long id, ClienteRequest request) {
		Cliente existente = buscarClienteOuErro(id);
		atualizarDadosCliente(existente, request);
		Cliente atualizado = clienteRepository.save(existente);
		return toResponse(atualizado);
	}

	@Override
	@Transactional
	public void deletar(Long id) {
		Cliente cliente = buscarClienteOuErro(id);
		clienteRepository.delete(cliente);
	}

	@Override
	@Transactional
	public void salvarImagem(Long id, MultipartFile imagem) {
		if (imagem == null || imagem.isEmpty()) {
			throw new BusinessException("Imagem inválida: arquivo vazio.");
		}

		String contentType = imagem.getContentType();
		if (contentType == null ||
				!(contentType.equalsIgnoreCase("image/png") ||
						contentType.equalsIgnoreCase("image/jpg") ||
						contentType.equalsIgnoreCase("image/jpeg"))) {
			throw new BusinessException("Tipo de arquivo inválido. Aceito apenas PNG, JPG ou JPEG.");
		}

		Cliente cliente = buscarClienteOuErro(id);
		try {
			cliente.setLogotipo(imagem.getBytes());
			clienteRepository.save(cliente);
		} catch (IOException e) {
			throw new BusinessException("Erro ao salvar imagem.");
		}
	}

	private Cliente construirCliente(ClienteRequest request) {
		Cliente cliente = Cliente.builder()
				.nome(request.getNome())
				.email(request.getEmail())
				.build();

		if (request.getLogotipo() != null && !request.getLogotipo().isEmpty()) {
			cliente.setLogotipo(extrairBytes(request.getLogotipo()));
		}

		List<Logradouro> logradouros = criarLogradouros(request, cliente);
		cliente.setLogradouros(logradouros);
		return cliente;
	}

	private void atualizarDadosCliente(Cliente cliente, ClienteRequest request) {
		cliente.setNome(request.getNome());
		cliente.setEmail(request.getEmail());

		if (request.getLogotipo() != null && !request.getLogotipo().isEmpty()) {
			cliente.setLogotipo(extrairBytes(request.getLogotipo()));
		}

		cliente.getLogradouros().clear();
		cliente.getLogradouros().addAll(criarLogradouros(request, cliente));
	}

	private List<Logradouro> criarLogradouros(ClienteRequest request, Cliente cliente) {
		return request.getLogradouros().stream()
				.map(l -> Logradouro.builder()
						.rua(l.getRua())
						.cidade(l.getCidade())
						.estado(l.getEstado())
						.cliente(cliente)
						.build())
				.collect(Collectors.toList());
	}

	private Cliente buscarClienteOuErro(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + id));
	}

	private void validarEmailDuplicado(String email) {
		if (clienteRepository.existsByEmail(email)) {
			throw new BusinessException("E-mail já cadastrado.");
		}
	}

	private byte[] extrairBytes(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (IOException e) {
			throw new BusinessException("Erro ao processar arquivo de imagem.");
		}
	}

	private ClienteResponse toResponse(Cliente cliente) {
		List<LogradouroResponse> logradouros = cliente.getLogradouros().stream()
				.map(log -> new LogradouroResponse(
						log.getId(),
						log.getRua(),
						log.getCidade(),
						log.getEstado()))
				.collect(Collectors.toList());

		return new ClienteResponse(
				cliente.getId(),
				cliente.getNome(),
				cliente.getEmail(),
				logradouros
		);
	}

	@Override
	public byte[] buscarLogotipo(Long id) {
		Cliente cliente = buscarClienteOuErro(id);
		return cliente.getLogotipo();
	}
}