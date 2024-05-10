package br.com.motasystem.adopet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.motasystem.adopet.api.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
