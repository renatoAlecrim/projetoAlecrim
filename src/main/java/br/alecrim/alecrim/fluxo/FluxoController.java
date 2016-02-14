package br.alecrim.alecrim.fluxo;

import classesbase.ControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transacoes")
public class FluxoController extends ControllerBase<Fluxo, Long, FluxoService>{
        
}
