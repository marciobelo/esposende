package esposende.entidade;

/**
 * Tipos de ocorrências que ou alteram o cadastro de um
 * bem permanente ou agregam informação sobre o mesmo.
 */
public enum TipoRegistroOcorrencia {

    CRIACAO("Criação"),
    ALTERACAO_DESCRICAO("Alteração da descrição"),
    ALTERACAO_RESPONSAVEL("Alteração do responsável"),
    ALTERACAO_SITUACAO("Alteração da situação"),
    ALTERACAO_ORIGEM("Alteração da origem"),
    ALTERACAO_LOCAL("Alteração do local"),
    TEXTO_LIVRE("Texto livre"),
	INVENTARIO("Informação de inventário"),
	ALTERACAO_VALOR_AQUISICAO("Alteração do valor de aquisição"),
	EM_BAIXA("Foi incluído em documento de baixa"),
	BAIXADO("Processo de baixa concluído"),
	REMOVIDO_BAIXA("Foi removido de documento de baixa");

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	private TipoRegistroOcorrencia(String descricao) {
		this.descricao = descricao;
	}
}
