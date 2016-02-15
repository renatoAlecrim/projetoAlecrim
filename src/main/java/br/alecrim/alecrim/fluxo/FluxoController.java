package br.alecrim.alecrim.fluxo;

import classesbase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transacoes")
public class FluxoController extends ControllerBase<CriarFluxo, Long, FluxoService>{
        
        @RequestMapping(value = "/getCliente", method = RequestMethod.GET)
        public List<Map<String, Object>> getClientes(){
            return service.getClientes();
        }
}
