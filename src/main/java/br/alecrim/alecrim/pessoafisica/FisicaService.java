package br.alecrim.alecrim.pessoafisica;

import br.alecrim.alecrim.cidade.Cidade;
import br.alecrim.alecrim.cidade.CidadeRepository;
import br.alecrim.alecrim.consultapersonalizada.ConstrutorDeSQL;
import br.alecrim.alecrim.consultapersonalizada.ParametrosConsulta;
import br.alecrim.alecrim.consultapersonalizada.RetornoConsultaPaginada;
import br.alecrim.alecrim.cpf.CPF;
import br.alecrim.alecrim.email.Email;
import br.alecrim.alecrim.endereco.Endereco;
import classesbase.ServiceBase;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class FisicaService extends ServiceBase<CriarPessoaFisica, Long, FisicaRepository> {

    @Autowired
    private CidadeRepository cidadeRepo;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    final String SQLConsultaFisicaCompleta = "SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pf.genero, pf.cpf as documento, p.telefone,"
            + " p.telefonesecundario, ende.bairro, ende.cep, ende.complemento, ende.logradouro, ende.numero, c.descricao, u.sigla "
            + " FROM pessoa p"
            + " LEFT JOIN pessoa_fisica pf "
            + "    ON pf.idpessoa = p.idpessoa "
            + " LEFT JOIN endereco ende "
            + "    ON p.endereco_id = ende.idendereco "
            + " LEFT JOIN endereco_cidade ec "
            + "    ON ende.idendereco = ec.endereco_id "
            + " LEFT JOIN cidade c "
            + "    ON ec.cidade_id = c.codigoibge "
            + " LEFT JOIN uf u "
            + "    ON c.estado_codigoestado = u.codigoestado "
            + " WHERE p.tipo_pessoa<>'JURÍDICA'";

    final String SQLConsultaFisica = "SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pf.genero, COALESCE(pf.cpf, pj.cnpj) as documento, p.telefone,"
            + " c.descricao, u.sigla "
            + "FROM pessoa p"
            + " LEFT JOIN pessoa_fisica pf "
            + "    ON pf.idpessoa = p.idpessoa"
            + " LEFT JOIN pessoa_juridica pj "
            + "    ON pj.idpessoa = p.idpessoa"
            + " LEFT JOIN endereco ende "
            + "    ON p.endereco_id = ende.idendereco"
            + " LEFT JOIN endereco_cidade ec "
            + "    ON ende.idendereco = ec.endereco_id"
            + " LEFT JOIN cidade c"
            + "    ON ec.cidade_id = c.codigoibge"
            + " LEFT JOIN uf u"
            + "    ON c.estado_codigoestado = u.codigoestado";
   /*         + " WHERE p.tipo_pessoa <> 'JURÍDICA'"
            + " UNION"
            + " SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pj.cnpj as documento, p.telefone,"
            + " p.telefonesecundario, ende.bairro, ende.cep, ende.complemento, ende.logradouro, ende.numero, c.descricao, u.sigla "
            + "FROM pessoa p"
            + " LEFT JOIN pessoa_juridica pj "
            + "    ON pj.idpessoa = p.idpessoa"
            + " LEFT JOIN endereco ende "
            + "    ON p.endereco_id = ende.idendereco"
            + " LEFT JOIN endereco_cidade ec "
            + "    ON ende.idendereco = ec.endereco_id"
            + " LEFT JOIN cidade c"
            + "    ON ec.cidade_id = c.codigoibge"
            + " LEFT JOIN uf u"
            + "    ON c.estado_codigoestado = u.codigoestado"
            + " WHERE p.tipo_pessoa = 'JURÍDICA'";*/

    @Override
    protected void setConstrutorDeSQL(br.alecrim.alecrim.consultapersonalizada.ConstrutorDeSQL aConstrutorDeSQL) {
        super.setConstrutorDeSQL(new ConstrutorDeSQL(PessoaFisica.class)); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void salvar(CriarPessoaFisica aPessoaFisica) {
        PessoaFisica pessoaFisica;
        Cidade cidade = cidadeRepo.findOne(aPessoaFisica.getCodigoibge());
        Endereco end = new Endereco(aPessoaFisica.getLogradouro(), aPessoaFisica.getNumero(), aPessoaFisica.getBairro(), aPessoaFisica.getComplemento(), aPessoaFisica.getCep(), cidade);
        pessoaFisica = new PessoaFisica(aPessoaFisica, end);

        try {
            repository.saveAndFlush(pessoaFisica);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void alterar(CriarPessoaFisica aFisica) {
        PessoaFisica fisica = repository.findOne(aFisica.getIdpessoa());
        fisica.alterar(aFisica);
        fisica.getEndereco().setCidade(cidadeRepo.findOne(aFisica.getCodigoibge()));
        repository.save(fisica);
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(SQLConsultaFisica);
    }
    
    public List<Map<String, Object>> listarFisicas() {
        return query.execute(SQLConsultaFisicaCompleta);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(SQLConsultaFisica, "p.nome", new ParametrosConsulta());
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta aParametrosConsulta) {
        return query.executeComPaginacao(SQLConsultaFisica, "p.nome", aParametrosConsulta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObjeto(Long aId) {
        return repository.findOne(aId);
    }

    public Map<String, String> verificarCpf(CPF aCpf, Long aUsuarioId) {
        Map<String, String> retorno = new HashMap<>();
        if (aCpf.valido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aCpf", aCpf.getCpf());
            params.addValue("aId", aUsuarioId);
            List<Map<String, Object>> usuario = query.execute("SELECT idpessoa, cpf FROM pessoa_fisica WHERE cpf = :aCpf AND idpessoa <> :aId", params);
            if (!usuario.isEmpty()) {
                retorno.put("retorno", "existe");
            } else {
                retorno.put("retorno", "valido");
            }
        } else {
            retorno.put("retorno", "invalido");
        }
        return retorno;
    }

    public Map<String, String> verificarCpf(CPF aCpf) {
        Map<String, String> retorno = new HashMap<>();
        if (aCpf.valido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aCpf", aCpf.getCpf());
            List<Map<String, Object>> usuario = query.execute("SELECT cpf FROM pessoa_fisica WHERE cpf = :aCpf", params);
            if (!usuario.isEmpty()) {
                retorno.put("retorno", "existe");
            } else {
                retorno.put("retorno", "valido");
            }
        } else {
            retorno.put("retorno", "invalido");
        }
        return retorno;
    }

    public boolean verificarEmail(Email aEmail) {
        if (aEmail != null && aEmail.verificarValido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail.getEmail());
            List<Map<String, Object>> usuario = query.execute("SELECT email FROM pessoa WHERE email = :aEmail AND tipo_pessoa = 'JURÍDICA'", params);
            if (!usuario.isEmpty()) {
                return false;
            }
            return true;
        } else {
            throw new RuntimeException("Campo email vazio!");
        }
    }

    public boolean verificarEmail(Email aEmail, Long aPessoaId) {
        if (aEmail != null && aEmail.verificarValido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail);
            params.addValue("aId", aPessoaId);
            List<Map<String, Object>> fisica = query.execute("SELECT id, email FROM pessoa WHERE email = :aEmail AND id <> :aId AND tipo_pessoa <> 'JURÍDICA'", params);
            if (!fisica.isEmpty()) {
                return false;
            }
            return true;
        } else {
            throw new RuntimeException("Campo email vazio!");
        }
    }   
    
}
