package br.alecrim.alecrim.eventos;

import br.alecrim.alecrim.consultapersonalizada.ConstrutorDeSQL;
import br.alecrim.alecrim.consultapersonalizada.ParametrosConsulta;
import br.alecrim.alecrim.consultapersonalizada.RetornoConsultaPaginada;
import classesbase.ServiceBase;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class EventosService extends ServiceBase<Eventos, Long, EventosRepository> {

    private final String sql
            = "      select e.descricao, "
            + " 	    e.horainicial, "
            + " 	    e.datainicial, "
            + " 	    e.datafinal, "
            + " 	    e.visualizarnocalendario, "
            + "             'EVENTO' as tipo, "
            + "             '' as idade, "
            + "             'event-notif' as tipoevento "
            + "  from eventos e "
            + " union all "
            + "select 'Aniversário '||p.nome as descricao, "
            + "       '00:00:00' as horainicial, "
            + "       date(to_char(pf.datanascimento,'DD/MM')||'/'||to_char(CURRENT_DATE,'YYYY')) as datainicial,  "
            + "       date(to_char(pf.datanascimento,'DD/MM')||'/'||to_char(CURRENT_DATE,'YYYY')) as datafinal, "
            + "       true as visualizarnocalendario, "
            + "       'ANIVERSARIO' as tipo, "
            + "       to_char(age(to_date(to_char(CURRENT_DATE,'DD/MM/YYYY'),'DD/MM/YYYY'),pf.datanascimento),'YY') as idade, "
            + "       'event-aniver' as tipoevento "
            + "  from pessoa p "
            + " inner join pessoa_fisica pf "
            + "    on pf.idpessoa = p.idpessoa ";

    public EventosService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Eventos.class));
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta aParametrosConsulta) {
        String palavraChave = aParametrosConsulta.getPalavraChave();
        if(palavraChave.equalsIgnoreCase("sim"))
            aParametrosConsulta.setPalavraChave("TRUE");
        if(palavraChave.equalsIgnoreCase("não"))
            aParametrosConsulta.setPalavraChave("FALSE");
        
        return super.listar(aParametrosConsulta); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Map<String, Object>> carregaCalendario() {
        return super.query.execute(sql);
    }

}
