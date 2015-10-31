package esposende.persistencia;

import esposende.entidade.NumeroProtocolar;

public interface NumeroProtocolarDAO {
    public NumeroProtocolar gerarNovoProcotolo();

    NumeroProtocolar obterPorId(Long idNumeroProtocolar);
}
