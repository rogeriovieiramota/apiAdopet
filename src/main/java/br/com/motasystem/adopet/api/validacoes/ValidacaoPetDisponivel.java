package br.com.motasystem.adopet.api.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.motasystem.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.motasystem.adopet.api.exception.ValidacaoException;
import br.com.motasystem.adopet.api.model.Pet;
import br.com.motasystem.adopet.api.repository.PetRepository;

@Component
public class ValidacaoPetDisponivel implements ValidacaoSolicitacaoAdocao {
	
	@Autowired
	private PetRepository petRepository;

	public void validar(SolicitacaoAdocaoDto dto) {
		
		Pet pet = petRepository.getReferenceById(dto.idPet());
		
        if (pet.getAdotado()) {
        	throw new ValidacaoException("Pet já foi adotado!");
        }			
		
	}	
	
}
