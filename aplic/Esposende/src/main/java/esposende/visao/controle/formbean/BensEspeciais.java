package esposende.visao.controle.formbean;

import esposende.entidade.Baixa;
import esposende.entidade.BemPermanente;
import esposende.entidade.Confere;
import esposende.entidade.TermoSubRogo;

import java.text.SimpleDateFormat;

public class BensEspeciais {

	private BemPermanente bem;
	private TermoSubRogo termoSubRogo;
	private Confere.TipoSituacaoConfere situacao;
	private Baixa baixa;

	public BensEspeciais(BemPermanente bem, Baixa baixa) {
		this.bem = bem;
		this.baixa = baixa;
	}

	public BensEspeciais(BemPermanente bem, TermoSubRogo termoSubRogo, Confere.TipoSituacaoConfere situacao) {
		this.bem = bem;
		this.termoSubRogo = termoSubRogo;
		this.situacao = situacao;
	}

	public BemPermanente getBem() {
		return bem;
	}

	public void setBem(BemPermanente bem) {
		this.bem = bem;
	}

	public TermoSubRogo getTermoSubRogo() {
		return termoSubRogo;
	}

	public void setTermoSubRogo(TermoSubRogo termoSubRogo) {
		this.termoSubRogo = termoSubRogo;
	}

	public Confere.TipoSituacaoConfere getSituacao() {
		return situacao;
	}

	public void setSituacao(Confere.TipoSituacaoConfere situacao) {
		this.situacao = situacao;
	}

	public String getDetalhes() {
		if (termoSubRogo != null) {
			return termoSubRogo.getProtocolo().toString();
		}
		if (situacao != null) {
			if (situacao.equals(Confere.TipoSituacaoConfere.NAO_LOCALIZADO)) {
				return "Não Localizado";
			}
			if (situacao.equals(Confere.TipoSituacaoConfere.DANIFICADO)) {
				return "Danificado";
			}
		}

		if (baixa != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(baixa.getDataBaixaContabil() != null ? baixa.getDataBaixaContabil() : baixa.getDataCriacao());
		}

		if (termoSubRogo == null && situacao == null) {
			return "Sim";
		}

		return "";
	}
}
