package back.repository;

import back.model.Profissao;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProfissaoRepository extends CrudRepository<Profissao, Long> {



}
