package br.com.motasystem.adopet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.motasystem.adopet.api.model.Abrigo;

public interface AbrigoRepository extends JpaRepository<Abrigo, Long> {
    boolean existsByNome(String nome);

    boolean existsByTelefone(String telefone);

    boolean existsByEmail(String email);

    Abrigo findByNome(String nome);
}
