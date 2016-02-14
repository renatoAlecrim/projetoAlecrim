package br.alecrim.alecrim.usuario.sessaousuario;

import br.alecrim.alecrim.usuario.Usuario;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SessaoUsuario {
    private Usuario usuario;
      
    public void setUsuario(Usuario aUsuario){
        this.usuario = aUsuario;
    }
    
    public Usuario getUsuario(){
        return this.usuario;
    }
}
