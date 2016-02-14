package br.alecrim.alecrim.pessoajuridica;

import br.alecrim.alecrim.consultapersonalizada.CampoConsulta;
import br.alecrim.alecrim.cnpj.Cnpj;
import br.alecrim.alecrim.email.Email;
import br.alecrim.alecrim.endereco.Endereco;
import br.alecrim.alecrim.pessoa.Pessoa;
import br.alecrim.alecrim.pessoa.TipoPessoa;
import br.alecrim.alecrim.telefone.Telefone;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cnpj"}, name = "uk_cnpj")})
@Inheritance(strategy = InheritanceType.JOINED)
public class PessoaJuridica extends Pessoa implements Serializable {

    @CampoConsulta
    @Embedded
    @Column(unique = true, nullable = false)
    private Cnpj cnpj;
    
    @CampoConsulta
    @Column(unique = true, nullable = true)
    private String inscricaoEstadual;
    
    @CampoConsulta
    @Column(unique = true, nullable = true)
    private String inscricaoSuframa;
    
    public PessoaJuridica() {
    }

    public PessoaJuridica(Cnpj cnpj, String nome, Telefone telefoneP, Telefone telefoneS, Email email, Endereco endereco, TipoPessoa tipoPessoa) {
        super(nome, telefoneP, telefoneS, email, endereco, tipoPessoa);
        this.cnpj = cnpj;
    }

    public PessoaJuridica(Cnpj cnpj, String nome, Telefone telefoneP, Telefone telefoneS, Email email) {
        super(nome, telefoneP, telefoneS, email);
        this.cnpj = cnpj;
    }

    public PessoaJuridica(Cnpj cnpj, String inscricaoEstadual, String inscricaoSuframa, String nome, Telefone telefone, Telefone telefonesecundario, Email email, Endereco endereco, TipoPessoa tipoPessoa) {
        super(nome, telefone, telefonesecundario, email, endereco, tipoPessoa);
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.inscricaoSuframa = inscricaoSuframa;
    }

    public PessoaJuridica(Cnpj cnpj, String inscricaoEstadual, String inscricaoSuframa, String nome, Telefone telefone, Telefone telefonesecundario, Email email) {
        super(nome, telefone, telefonesecundario, email);
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.inscricaoSuframa = inscricaoSuframa;
    }

    public PessoaJuridica(CriarPessoaJuridica p, Endereco end) {
        this(p.getCnpj(), p.getInscricaoEstadual(),p.getInscricaoSuframa(), p.getNome(), p.getTelefone(), p.getTelefonesecundario(), p.getEmail(), end, p.getTipo());
    }

    public void alterar(CriarPessoaJuridica aJuridica) {
        this.setNome(aJuridica.getNome());
        this.setTelefone(aJuridica.getTelefone());
        this.setTelefonesecundario(aJuridica.getTelefonesecundario());
        this.setEmail(aJuridica.getEmail());
        this.setTipoPessoa(TipoPessoa.JURÍDICA);
        this.setCnpj(aJuridica.getCnpj());
        this.getEndereco().setLogradouro(aJuridica.getLogradouro());
        this.getEndereco().setNumero(aJuridica.getNumero());
        this.getEndereco().setBairro(aJuridica.getBairro());
        this.getEndereco().setComplemento(aJuridica.getComplemento());
        this.getEndereco().setCep(aJuridica.getCep());
        this.setInscricaoEstadual(aJuridica.getInscricaoEstadual());
        this.setInscricaoSuframa(aJuridica.getInscricaoSuframa());
    }

    public Cnpj getCnpj() {
        return cnpj;
    }

    public void setCnpj(Cnpj cnpj) {
//        cnpj.setCnpj(String.format("/^d{2}.d{3}.d{3}/d{4}-d{2}$/", cnpj));
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getInscricaoSuframa() {
        return inscricaoSuframa;
    }

    public void setInscricaoSuframa(String inscricaoSuframa) {
        this.inscricaoSuframa = inscricaoSuframa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.cnpj);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PessoaJuridica other = (PessoaJuridica) obj;
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        return true;
    }

}
