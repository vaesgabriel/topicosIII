package br.upf.projetojfprimefaces.controller;

import br.upf.projetojfprimefaces.entity.FuncionarioEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private br.upf.projetojfprimefaces.facade.FuncionarioFacade ejbFacade;

    // Objeto que representa o funcionário logando no sistema
    private FuncionarioEntity funcionario;

    public LoginController() {
    }

    /**
     * Método utilizado para inicializar a entidade após a construção do bean
     */
    @PostConstruct
    public void init() {
        funcionario = new FuncionarioEntity();
    }

    /**
     * Método utilizado para validar login (email e senha)
     *
     * @return página de destino se sucesso, ou null se falha
     */
    public String validarLogin() {
        String email = funcionario.getEmail().trim();
        String senha = funcionario.getSenha().trim();

        FuncionarioEntity funcionarioDB = ejbFacade.buscarPorEmail(email, senha);

        if (funcionarioDB != null && funcionarioDB.getId() != null) {
            return "/funcionario.xhtml?faces-redirect=true";
        } else {
            FacesMessage fm = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Falha no Login!",
                    "Email ou senha incorretos!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
    }

    public FuncionarioEntity getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioEntity funcionario) {
        this.funcionario = funcionario;
    }
}
