package back.controller;

import back.model.Pessoa;
import back.model.Telefone;
import back.repository.PessoaRepository;
import back.repository.ProfissaoRepository;
import back.repository.TelefoneRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private ProfissaoRepository profissaoRepository;

    @Autowired
    private ReportUtil reportUtil;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
    public ModelAndView inicio(){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaobj", new Pessoa());
      //  Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
        modelAndView.addObject("pessoas");
        modelAndView.addObject("profissoes", profissaoRepository.findAll());
        return modelAndView;
    }


    @RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
    public ModelAndView salvar (@Valid Pessoa pessoa, BindingResult bindingResult){

        pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getId()));

        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
            modelAndView.addObject("pessoas", pessoasIt);
            modelAndView.addObject("pessoaobj", pessoa);

            List<String> msg = new ArrayList<String>();
            for(ObjectError objectError : bindingResult.getAllErrors()){
                msg.add(objectError.getDefaultMessage()); // vem das anotaçoes nas Entity
            }
            modelAndView.addObject("msg",msg);

            return modelAndView;
        }

         pessoaRepository.save(pessoa);

        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoasIt);
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    };

    @RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
    public ModelAndView pessoas(){
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoasIt);
        andView.addObject("pessoaobj", new Pessoa());
        return  andView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/editarpessoa/{idpessoa}")
    public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa){
        Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaobj", pessoa.get());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/removerpessoa/{idpessoa}")
    public ModelAndView exclui(@PathVariable("idpessoa") Long idpessoa){
            pessoaRepository.deleteById(idpessoa);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaobj", pessoaRepository.findAll());
        modelAndView.addObject("pessoaobj", new Pessoa());
        return modelAndView;
    }

    @PostMapping("**/pesquisarpessoa")
    public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa,
                                  @RequestParam("pesqsexo") String pesqsexo) {

        List<Pessoa> pessoas = pesquisarLogica(nomepesquisa, pesqsexo);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa"); // Verifique se o caminho da sua página está correto
        modelAndView.addObject("pessoas", pessoas);
        modelAndView.addObject("pessoaobj", new Pessoa());
        return modelAndView;
    }

    // Função auxiliar para não repetir código de busca
    private List<Pessoa> pesquisarLogica(String nome, String sexo) {
        if (nome != null && !nome.isEmpty() && sexo != null && !sexo.isEmpty()) {
            return pessoaRepository.findPessoaByNameSexo(nome, sexo);
        } else if (nome != null && !nome.isEmpty()) {
            return pessoaRepository.findPessoaByName(nome);
        } else if (sexo != null && !sexo.isEmpty()) {
            return pessoaRepository.findPessoaBySexo(sexo);
        } else {
            return (List<Pessoa>) pessoaRepository.findAll();
        }
    }

    @GetMapping("**/imprimirpdf")
    public void imprimePdf(@RequestParam("nomepesquisa") String nomepesquisa,
                           @RequestParam("pesqsexo") String pesqsexo,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {

        List<Pessoa> pessoas = pesquisarLogica(nomepesquisa, pesqsexo); // chama uma função comum de busca

        byte[] pdf = reportUtil.gerarRelatorio(pessoas, "RelatorioPessoa", request.getServletContext());
        response.setContentLength(pdf.length);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"relatorio.pdf\"");
        response.getOutputStream().write(pdf);
        response.getOutputStream().flush();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/telefones/{idpessoa}")
    public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa){
        Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaobj", pessoa.get());
        modelAndView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "**/addfonePessoa/{pessoaid}")
    public ModelAndView addfonePessoa(@Valid Telefone telefone,BindingResult bindingResult, @PathVariable("pessoaid") Long pessoaid ){
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
            Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
            modelAndView.addObject("pessoaobj", pessoa);
            modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));

            List<String> msg = new ArrayList<String>();
            for(ObjectError objectError : bindingResult.getAllErrors()){
                msg.add(objectError.getDefaultMessage()); // vem das anotaçoes nas Entity
            }
            modelAndView.addObject("msg",msg);

            return modelAndView;
        }

        Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
        telefone.setPessoa(pessoa);
        telefoneRepository.save(telefone);

        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaobj", pessoa);
        modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/removertelefone/{idtelefone}")
    public ModelAndView removertelefone(@PathVariable("idtelefone") Long idtelefone){

        Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();

        telefoneRepository.deleteById(idtelefone);

        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaobj", pessoa);
        modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));

        return modelAndView;
    }


}
