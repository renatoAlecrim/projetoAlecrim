package br.alecrim.alecrim.fluxo;

import br.alecrim.alecrim.consultapersonalizada.ConstrutorDeSQL;
import br.alecrim.alecrim.pessoa.Pessoa;
import br.alecrim.alecrim.pessoa.TipoPessoa;
import br.alecrim.alecrim.pessoafisica.FisicaService;
import br.alecrim.alecrim.pessoajuridica.JuridicaService;
import classesbase.ServiceBase;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class FluxoService extends ServiceBase<CriarFluxo, Long, FluxoRepository> {

    @Autowired
    private FisicaService fisica;

    @Autowired
    private JuridicaService juridica;

    final String SQLConsultaFisica = "SELECT * FROM pessoa p";

    public FluxoService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Fluxo.class));
    }

    public List<Map<String, Object>> getClientes() {
        return query.execute(SQLConsultaFisica);
    }

    @Override
    public void alterar(CriarFluxo aEntidade) {
        Pessoa p;
        if (aEntidade.getTipo() == TipoPessoa.FÍSICA) {
            p = (Pessoa) fisica.getObjeto(aEntidade.getIdcliente());
        } else {
            p = (Pessoa) juridica.getObjeto(aEntidade.getIdcliente());
        }
        Fluxo f = repository.findOne(aEntidade.getIdfluxo());
        f.alterar(aEntidade, p);
        try {
            repository.save(f);
        } catch (Exception e) {
        }
    }

    @Override
    public void salvar(CriarFluxo aEntidade) {
        Pessoa p = null;
        if (aEntidade.getIdcliente() != null) {
            if (aEntidade.getTipo() == TipoPessoa.FÍSICA) {
                p = (Pessoa) fisica.getObjeto(aEntidade.getIdcliente());
            } else {
                p = (Pessoa) juridica.getObjeto(aEntidade.getIdcliente());
            }
            aEntidade.setNomecliente("");
        }
        Fluxo f = new Fluxo(aEntidade, p);
        try {
            repository.save(f);
        } catch (Exception e) {
        }
    }

    @Override
    public Object getObjeto(Long aId) {
        return repository.findOne(aId);
    }

}
