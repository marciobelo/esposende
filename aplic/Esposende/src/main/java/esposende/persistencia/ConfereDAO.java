package esposende.persistencia;

import esposende.entidade.Confere;

public interface ConfereDAO {
    Confere findById(long id);

    void update(Confere confere);
}
