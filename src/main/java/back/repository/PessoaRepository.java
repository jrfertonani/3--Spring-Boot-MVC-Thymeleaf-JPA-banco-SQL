package back.repository;

import back.model.Pessoa;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PessoaRepository  extends JpaRepository<Pessoa, Long> {

    @Query("select p from Pessoa p where p.nome like %?1%")
    List<Pessoa> findPessoaByName(String nome);

    @Query("select p from Pessoa p where p.sexopessoa = ?1")
    List<Pessoa> findPessoaBySexo(String sexo);

    @Query("select p from Pessoa p where p.nome like %?1% and p.sexopessoa = ?2")
    List<Pessoa> findPessoaByNameSexo(String nome, String sexopessoa);



}
