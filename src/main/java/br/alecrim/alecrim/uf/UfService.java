package br.alecrim.alecrim.uf;

import br.alecrim.alecrim.consultapersonalizada.ConstrutorDeSQL;
import classesbase.ServiceBase;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class UfService extends ServiceBase<UF, Long, UFRepository>{

    public UfService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(UF.class));
    }

}
