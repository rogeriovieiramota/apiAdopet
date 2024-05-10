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
public class ValidacaoTutorComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao {
	
	@Autowired
	private AdocaoRepository adocaoRepository;	
	
	@Autowired
	private TutorRepository tutorRepository;
	
	public void validar(SolicitacaoAdocaoDto dto) {
		
		List<Adocao> adocoes = adocaoRepository.findAll();
		
		Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());		
		
        for (Adocao a : adocoes) {
            if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
            	throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
            }
        }
        		
	}

}
