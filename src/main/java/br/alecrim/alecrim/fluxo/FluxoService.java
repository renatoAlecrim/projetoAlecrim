package br.alecrim.alecrim.fluxo;

import br.alecrim.alecrim.consultapersonalizada.ConstrutorDeSQL;
import classesbase.ServiceBase;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class FluxoService extends ServiceBase<Fluxo, Long, FluxoRepository> {

    public FluxoService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Fluxo.class));
    }

}
