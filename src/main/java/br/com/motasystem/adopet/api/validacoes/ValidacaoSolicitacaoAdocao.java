package br.com.motasystem.adopet.api.validacoes;

import br.com.motasystem.adopet.api.dto.SolicitacaoAdocaoDto;

public interface ValidacaoSolicitacaoAdocao {
	
	//public void validar(SolicitacaoAdocaoDto dto); pelo java nao é necessario declarar o public
	void validar(SolicitacaoAdocaoDto dto);

}
