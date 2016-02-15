package br.alecrim.alecrim.fluxo;

import br.alecrim.alecrim.consultapersonalizada.CampoConsulta;
import br.alecrim.alecrim.pessoa.Pessoa;
import br.alecrim.alecrim.pessoa.TipoPessoa;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class CriarFluxo {
    private Long idfluxo;
    private String descricao;
    private float valor;
    private boolean pago;
    private Date dataentrada;
    private Date dataentrega;
    private Long idcliente;
    private String nomecliente;
    private TipoPessoa tipo;
    
    public CriarFluxo() {
    }

    public CriarFluxo(Long idfluxo, String descricao, float valor, boolean pago, Date dataentrada, Date dataentrega, Long idcliente, String nomecliente, TipoPessoa tipo) {
        this.idfluxo = idfluxo;
        this.descricao = descricao;
        this.valor = valor;
        this.pago = pago;
        this.dataentrada = dataentrada;
        this.dataentrega = dataentrega;
        this.idcliente = idcliente;
        this.nomecliente = nomecliente;
        this.tipo = tipo;
    }

    public Long getIdfluxo() {
        return idfluxo;
    }

    public void setIdfluxo(Long idfluxo) {
        this.idfluxo = idfluxo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public Date getDataentrada() {
        return dataentrada;
    }

    public void setDataentrada(Date dataentrada) {
        this.dataentrada = dataentrada;
    }

    public Date getDataentrega() {
        return dataentrega;
    }

    public void setDataentrega(Date dataentrega) {
        this.dataentrega = dataentrega;
    }

    public Long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public String getNomecliente() {
        return nomecliente;
    }

    public void setNomecliente(String nomecliente) {
        this.nomecliente = nomecliente;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.idfluxo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CriarFluxo other = (CriarFluxo) obj;
        if (!Objects.equals(this.idfluxo, other.idfluxo)) {
            return false;
        }
        return true;
    }

}
