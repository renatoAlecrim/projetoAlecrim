package br.alecrim.alecrim.pessoafisica;

import br.alecrim.alecrim.consultapersonalizada.CampoConsulta;
import br.alecrim.alecrim.genero.Genero;
import br.alecrim.alecrim.cpf.CPF;
import br.alecrim.alecrim.email.Email;
import br.alecrim.alecrim.endereco.Endereco;
import br.alecrim.alecrim.pessoa.Pessoa;
import br.alecrim.alecrim.pessoa.TipoPessoa;
import br.alecrim.alecrim.telefone.Telefone;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cpf"}, name = "uk_cpf")})
public class PessoaFisica extends Pessoa implements Serializable {

    @CampoConsulta
    @Embedded
    @Column(unique = true, nullable = false)
    private CPF cpf;

    @CampoConsulta
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @CampoConsulta
    @Temporal(TemporalType.DATE)
    private Date datanascimento;

    public PessoaFisica() {
    }

    public PessoaFisica(String nome, Email email) {
        super(nome, email);
    }

    public PessoaFisica(CPF cpf, Genero genero, String nome, Telefone telefoneP, Telefone telefoneS, Email email, Endereco endereco, TipoPessoa tipoPessoa, Date datanasc) {
        super(nome, telefoneP, telefoneS, email, endereco, tipoPessoa);
        this.cpf = cpf;
        this.genero = genero;
        this.datanascimento = datanasc;
    }

    public PessoaFisica(CPF cpf, Genero genero, String nome, Telefone telefoneP, Telefone telefoneS, Email email) {
        super(nome, telefoneP, telefoneS, email);
        this.cpf = cpf;
        this.genero = genero;
    }
    public PessoaFisica(CriarPessoaFisica p, Endereco endereco) {
        this(p.getCpf(), p.getGenero(), p.getNome(), p.getTelefone(),p.getTelefonesecundario(), p.getEmail(), endereco, p.getTipoPessoa(), p.getDatanasc());
    }

    public void alterar(CriarPessoaFisica aFisica) {
        this.setNome(aFisica.getNome());
        this.setTelefone(aFisica.getTelefone());
        this.setTelefonesecundario(aFisica.getTelefonesecundario());
        this.setEmail(aFisica.getEmail());
        this.setDatanascimento(aFisica.getDatanasc());
        this.setTipoPessoa(aFisica.getTipo());
        this.setCpf(aFisica.getCpf());
        this.setGenero(aFisica.getGenero());
        this.getEndereco().setLogradouro(aFisica.getLogradouro());
        this.getEndereco().setNumero(aFisica.getNumero());
        this.getEndereco().setBairro(aFisica.getBairro());
        this.getEndereco().setComplemento(aFisica.getComplemento());
        this.getEndereco().setCep(aFisica.getCep());
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
//        cpf.setCpf(String.format("^(\\d{3}\\.?){3}\\-\\d{2}", cpf.getCpf()));
        this.cpf = cpf;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.cpf);
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
        final PessoaFisica other = (PessoaFisica) obj;
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        return true;
    }

}
