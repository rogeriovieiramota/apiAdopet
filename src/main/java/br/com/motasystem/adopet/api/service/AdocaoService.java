package br.com.motasystem.adopet.api.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.motasystem.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.motasystem.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.motasystem.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.motasystem.adopet.api.model.Adocao;
import br.com.motasystem.adopet.api.model.Pet;
import br.com.motasystem.adopet.api.model.Tutor;
import br.com.motasystem.adopet.api.repository.AdocaoRepository;
import br.com.motasystem.adopet.api.repository.PetRepository;
import br.com.motasystem.adopet.api.repository.TutorRepository;
import br.com.motasystem.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;

@Service
public class AdocaoService {
	
    @Autowired
    private AdocaoRepository repository;
    
    @Autowired
    private PetRepository petRepository;
    
    @Autowired
    private TutorRepository tutorRepository;            
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private List<ValidacaoSolicitacaoAdocao> validacoes;
	
	public void solicitar(SolicitacaoAdocaoDto dto) {
		
		Pet pet = petRepository.getReferenceById(dto.idPet());
		Tutor tutor= tutorRepository.getReferenceById(dto.idTutor());		
		
		//chamar os Principios da Estratégias vulgo Padrão Strategy
		//mandar executar pelo Padrão Chain of Responsibility
		
		validacoes.forEach(v->v.validar(dto));		
		
        Adocao adocao = new Adocao(tutor, pet, dto.motivo());        
        
        repository.save(adocao);
    
        emailService.enviarEmail(adocao.getPet().getAbrigo().getEmail(), 
        		adocao.getPet().getAbrigo().getNome() +"!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação.", 
        		"Solicitação de Adoção!");
		
	}
	
	public void aprovar(AprovacaoAdocaoDto dto) {
		
		Adocao adocao = repository.getReferenceById(dto.idAdocao());
		adocao.marcarComoAprovado();
		
		//repository.save(adocao); Não seria necessario declarar, pois implicamente o set ja atualiza o registro
        
        emailService.enviarEmail(adocao.getPet().getAbrigo().getEmail(), 
        		"Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet.", 
        		"Adoção aprovada");      
		
	}
	
	public void reprovar(ReprovacaoAdocaoDto dto) {
		
		Adocao adocao = repository.getReferenceById(dto.idAdocao());
		
		adocao.marcarComoReprovada(dto.justificativa());
        
        //repository.save(adocao);

        emailService.enviarEmail(adocao.getPet().getAbrigo().getEmail(), 
        		"Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +" com a seguinte justificativa: " +adocao.getJustificativaStatus(), 
        		"Adoção reprovada!!!");      
        
	}

}
