package br.alecrim.alecrim.itemacesso;

import br.alecrim.alecrim.consultapersonalizada.QueryPersonalizada;
import br.alecrim.alecrim.cidade.CidadeRepository;
import br.alecrim.alecrim.perfildeacesso.PerfilDeAcesso;
import br.alecrim.alecrim.perfildeacesso.PerfilDeAcessoRepository;
import br.alecrim.alecrim.uf.UFRepository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class InicializadorItemAcesso {

    @Autowired
    private ItemAcessoRepository repo;

    @Autowired
    private CidadeRepository cidRepo;

    @Autowired
    private UFRepository UfRepo;
    @Autowired
    private PerfilDeAcessoRepository perfilRepo;

    @Autowired
    protected QueryPersonalizada query;

    private ItemAcesso getItemAcesso(List<ItemAcesso> lista, String nome, String rota) {

        for (ItemAcesso item : lista) {
            if (item.getNome().equals(nome) && item.getRota().equals(rota)) {
                return item;
            }
        }
        return null;
    }

    @PostConstruct
    public void inicializar() {
        carregarUF();
        carregarEstados();

        List<ItemAcesso> itensAcesso = new ArrayList<>();

        itensAcesso = repo.findAll();

        ItemAcesso menu;
        menu = this.getItemAcesso(itensAcesso, "Menu", "/");
        if (menu == null) {
            menu = new ItemAcesso("Menu", "/", "fa-bars");
            repo.save(menu);
            itensAcesso.add(menu);
        }
        //PESSOA
        ItemAcesso menuPessoa;
        menuPessoa = this.getItemAcesso(itensAcesso, "Gerenciar Pessoa", "");
        if (menuPessoa == null) {
            menuPessoa = new ItemAcesso("Gerenciar Pessoa", "", "fa-user", menu);
            itensAcesso.add(menuPessoa);
        }

        ItemAcesso menuPessoaListar;
        menuPessoaListar = this.getItemAcesso(itensAcesso, " Listar Pessoa", "#/Pessoa/listar");
        if (menuPessoaListar == null) {
            menuPessoaListar = new ItemAcesso(" Listar Pessoa", "#/Pessoa/listar", "fa-list", menuPessoa);
            itensAcesso.add(menuPessoaListar);
        }

        // PESSOA FISICA
        ItemAcesso menuFisicaNovo;
        menuFisicaNovo = this.getItemAcesso(itensAcesso, "Cadastrar Pessoa Física", "#/Fisica/nova");
        if (menuFisicaNovo == null) {
            menuFisicaNovo = new ItemAcesso("Cadastrar Pessoa Física", "#/Fisica/nova", "fa-plus", menuPessoa);
            itensAcesso.add(menuFisicaNovo);
        }
        // PESSOA JURIDICA
        ItemAcesso menuJuridicaNovo;
        menuJuridicaNovo = this.getItemAcesso(itensAcesso, "Cadastrar Pessoa Jurídica", "#/Juridica/nova");
        if (menuJuridicaNovo == null) {
            menuJuridicaNovo = new ItemAcesso("Cadastrar Pessoa Jurídica", "#/Juridica/nova", "fa-plus", menuPessoa);
            itensAcesso.add(menuJuridicaNovo);
        }

        //EVENTOS
        ItemAcesso menuEventos;
        menuEventos = this.getItemAcesso(itensAcesso, "Gerenciar Eventos", "");
        if (menuEventos == null) {
            menuEventos = new ItemAcesso("Gerenciar Eventos", "", "fa-calendar", menu);
            itensAcesso.add(menuEventos);
        }

        ItemAcesso menuEventosListar;
        menuEventosListar = this.getItemAcesso(itensAcesso, " Listar Eventos", "#/Eventos/listar");
        if (menuEventosListar == null) {
            menuEventosListar = new ItemAcesso(" Listar Eventos", "#/Eventos/listar", "fa-list-alt", menuEventos);
            itensAcesso.add(menuEventosListar);
        }

        ItemAcesso menuEventosNovo;
        menuEventosNovo = this.getItemAcesso(itensAcesso, "Cadastrar Eventos", "#/Eventos/novo");
        if (menuEventosNovo == null) {
            menuEventosNovo = new ItemAcesso("Cadastrar Eventos", "#/Eventos/novo", "fa-plus", menuEventos);
            itensAcesso.add(menuEventosNovo);
        }
        
        //FLUXO
        ItemAcesso menuFluxo;
        menuFluxo = this.getItemAcesso(itensAcesso, "Gerenciar Transações", "");
        if (menuFluxo == null) {
            menuFluxo = new ItemAcesso("Gerenciar Transações", "", "fa-calendar", menu);
            itensAcesso.add(menuFluxo);
        }

        ItemAcesso menuFluxoListar;
        menuFluxoListar = this.getItemAcesso(itensAcesso, " Listar Transações", "#/Fluxo/listar");
        if (menuFluxoListar == null) {
            menuFluxoListar = new ItemAcesso(" Listar Transações", "#/Fluxo/listar", "fa-list-alt", menuFluxo);
            itensAcesso.add(menuFluxoListar);
        }

        ItemAcesso menuFluxoNovo;
        menuFluxoNovo = this.getItemAcesso(itensAcesso, "Cadastrar Transações", "#/Fluxo/novo");
        if (menuFluxoNovo == null) {
            menuFluxoNovo = new ItemAcesso("Cadastrar Transações", "#/Fluxo/novo", "fa-plus", menuFluxo);
            itensAcesso.add(menuFluxoNovo);
        }

        //USUARIO
        ItemAcesso menuUsuario;
        menuUsuario = this.getItemAcesso(itensAcesso, "Gerenciar Usuário", "");
        if (menuUsuario == null) {
            menuUsuario = new ItemAcesso("Gerenciar Usuário", "", "fa-user", menu);
            itensAcesso.add(menuUsuario);
        }

        ItemAcesso menuUsuarioListar;
        menuUsuarioListar = this.getItemAcesso(itensAcesso, " Listar Usuário", "#/Usuario/listar");
        if (menuUsuarioListar == null) {
            menuUsuarioListar = new ItemAcesso(" Listar Usuário", "#/Usuario/listar", "fa-list", menuUsuario);
            itensAcesso.add(menuUsuarioListar);
        }

        ItemAcesso menuUsuarioNovo;
        menuUsuarioNovo = this.getItemAcesso(itensAcesso, "Cadastrar Usuário", "#/Usuario/novo");
        if (menuUsuarioNovo == null) {
            menuUsuarioNovo = new ItemAcesso("Cadastrar Usuário", "#/Usuario/novo", "fa-plus", menuUsuario);
            itensAcesso.add(menuUsuarioNovo);
        }

        //PERFIL
        ItemAcesso menuPerfil;
        menuPerfil = this.getItemAcesso(itensAcesso, "Gerenciar Perfil", "");
        if (menuPerfil == null) {
            menuPerfil = new ItemAcesso("Gerenciar Perfil", "", "fa-pencil", menu);
            itensAcesso.add(menuPerfil);
        }

        ItemAcesso menuPerfilListar;
        menuPerfilListar = this.getItemAcesso(itensAcesso, " Listar Perfil", "#/Perfil/listar");
        if (menuPerfilListar == null) {
            menuPerfilListar = new ItemAcesso(" Listar Perfil", "#/Perfil/listar", "fa-list", menuPerfil);
            itensAcesso.add(menuPerfilListar);
        }

        ItemAcesso menuPerfilNovo;
        menuPerfilNovo = this.getItemAcesso(itensAcesso, "Cadastrar Perfil", "#/Perfil/novo");
        if (menuPerfilNovo == null) {
            menuPerfilNovo = new ItemAcesso("Cadastrar Perfil", "#/Perfil/novo", "fa-plus", menuPerfil);
            itensAcesso.add(menuPerfilNovo);
        }

        for (ItemAcesso ia : itensAcesso) {
            repo.save(ia);
        }

        if (perfilRepo.count() == 0) {
            PerfilDeAcesso perfilAdm = new PerfilDeAcesso("Administrador", new HashSet<>(repo.findAll()));
            perfilRepo.save(perfilAdm);
        }

    }

    public void carregarUF() {
        final String FILE_NAME_UF = "src/main/java/SCRIPTS/uf.txt";
        carregarScript(new File(FILE_NAME_UF));
    }

    public void carregarEstados() {
        final String FILE_NAME_CIDADES = "src/main/java/SCRIPTS/cidades.txt";
        carregarScript(new File(FILE_NAME_CIDADES));
    }

    public void carregarScript(File arquivo) {
        try {
            FileInputStream fileInputStream = new FileInputStream(arquivo);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                try {
                    query.execute(linha);
                } catch (Exception e) {
                }
            }

            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (Exception ex) {
            throw new RuntimeException("Falha ao carregar script");
        }
    }
}
