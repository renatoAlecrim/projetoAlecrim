package br.alecrim.alecrim.fluxo;

import br.alecrim.alecrim.consultapersonalizada.CampoConsulta;
import br.alecrim.alecrim.pessoa.Pessoa;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Fluxo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @CampoConsulta
    private Long idfluxo;
    
    @CampoConsulta
    private String descricao;
    
    @CampoConsulta
    private float valor;

    @CampoConsulta
    private boolean pago;

    @CampoConsulta
    private Date dataentrada;

    @CampoConsulta
    private Date dataentrega;

    @CampoConsulta
    @ManyToOne
    @JoinColumn(name = "cliente")
    private Pessoa cliente;
    
    @CampoConsulta
    private String nomecliente;

    public Fluxo(String descricao, float valor, boolean pago, Date dataEntrada, Date dataEntrega, Pessoa cliente) {
        this.descricao = descricao;
        this.valor = valor;
        this.pago = pago;
        this.dataentrada = dataEntrada;
        this.dataentrega = dataEntrega;
        this.cliente = cliente;
    }

    public Fluxo(Long idfluxo, String descricao, float valor, boolean pago, Date dataentrada, Date dataentrega, String nomecliente) {
        this.idfluxo = idfluxo;
        this.descricao = descricao;
        this.valor = valor;
        this.pago = pago;
        this.dataentrada = dataentrada;
        this.dataentrega = dataentrega;
        this.nomecliente = nomecliente;
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

    public String getNomecliente() {
        return nomecliente;
    }

    public void setNomecliente(String nomecliente) {
        this.nomecliente = nomecliente;
    }
    
    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.idfluxo);
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
        final Fluxo other = (Fluxo) obj;
        if (!Objects.equals(this.idfluxo, other.idfluxo)) {
            return false;
        }
        return true;
    }
    
}
