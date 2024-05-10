package br.com.motasystem.adopet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.motasystem.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.motasystem.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.motasystem.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.motasystem.adopet.api.service.AdocaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {
	
	@Autowired
	private AdocaoService adocaoService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> solicitar(@RequestBody @Valid SolicitacaoAdocaoDto dto) {
    	try {
    		this.adocaoService.solicitar(dto);
    		return ResponseEntity.ok("Adoção Solicitada com sucesso!!!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid AprovacaoAdocaoDto dto) {
    	this.adocaoService.aprovar(dto);
    	return ResponseEntity.ok().build();    	
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid ReprovacaoAdocaoDto dto) {
    	this.adocaoService.reprovar(dto);
        return ResponseEntity.ok().build();
    }

}
