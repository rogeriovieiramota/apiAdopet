package br.com.motasystem.adopet.api.validacoes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.motasystem.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.motasystem.adopet.api.exception.ValidacaoException;
import br.com.motasystem.adopet.api.model.Adocao;
import br.com.motasystem.adopet.api.model.StatusAdocao;
import br.com.motasystem.adopet.api.model.Tutor;
import br.com.motasystem.adopet.api.repository.AdocaoRepository;
import br.com.motasystem.adopet.api.repository.TutorRepository;

@Component
public class ValidacaoTutorComLimiteDeAdocoes implements ValidacaoSolicitacaoAdocao {
	
	@Autowired
	private AdocaoRepository adocaoRepository;
	
	@Autowired
	private TutorRepository tutorRepository;
	
	public void validar(SolicitacaoAdocaoDto dto) {
		
		List<Adocao> adocoes = adocaoRepository.findAll();
		
		Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());
				
        for (Adocao a : adocoes) {
            int contador = 0;
            if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.APROVADO) {
                contador = contador + 1;
            }
            if (contador == 5) {
                throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
            }
        }        
		
	}

}
