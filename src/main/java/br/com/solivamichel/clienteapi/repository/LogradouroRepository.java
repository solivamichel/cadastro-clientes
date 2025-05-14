package br.com.solivamichel.clienteapi.repository;

import br.com.solivamichel.clienteapi.entity.Logradouro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogradouroRepository extends JpaRepository<Logradouro, Long> {

	List<Logradouro> findByClienteId(Long clienteId);
}