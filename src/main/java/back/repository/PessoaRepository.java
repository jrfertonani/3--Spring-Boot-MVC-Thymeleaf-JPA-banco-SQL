package back.repository;

import back.model.Pessoa;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PessoaRepository  extends CrudRepository<Pessoa, Long> {



}
