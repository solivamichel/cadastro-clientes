package br.com.solivamichel.clienteapi.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "logradouros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Logradouro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@ToString.Include
	private Long id;

	@Column(nullable = false)
	private String rua;

	@Column(nullable = false)
	private String cidade;

	@Column(nullable = false)
	private String estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
}

