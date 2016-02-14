package br.alecrim.alecrim.eventos;

import classesbase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/eventos")
public class EventosController extends ControllerBase<Eventos, Long, EventosService> {
        
    @RequestMapping(value = "/carregaCalendario", method = RequestMethod.GET)
    public List<Map<String, Object>> carregaCalendario() {
        return service.carregaCalendario();
    }
}
