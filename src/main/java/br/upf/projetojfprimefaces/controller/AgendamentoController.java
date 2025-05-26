package br.upf.projetojfprimefaces.controller;

import br.upf.projetojfprimefaces.entity.AgendamentoEntity;
import br.upf.projetojfprimefaces.facade.AgendamentoFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named(value = "agendamentoController")
@SessionScoped
public class AgendamentoController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private AgendamentoFacade ejbFacade;

    private AgendamentoEntity agendamento = new AgendamentoEntity();
    private List<AgendamentoEntity> agendamentoList = new ArrayList<>();
    private AgendamentoEntity selected;

    public AgendamentoEntity getSelected() {
        return selected;
    }

    public void setSelected(AgendamentoEntity selected) {
        this.selected = selected;
    }

    public AgendamentoEntity getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(AgendamentoEntity agendamento) {
        this.agendamento = agendamento;
    }

    public List<AgendamentoEntity> getAgendamentoList() {
        return ejbFacade.buscarTodos();
    }

    public void setAgendamentoList(List<AgendamentoEntity> agendamentoList) {
        this.agendamentoList = agendamentoList;
    }

    public AgendamentoEntity prepareAdicionar() {
        agendamento = new AgendamentoEntity();
        return agendamento;
    }

    public void adicionarAgendamento() {
        Date datahoraAtual = new Timestamp(System.currentTimeMillis());
        agendamento.setDatahorareg(datahoraAtual);
        persist(PersistAction.CREATE, "Agendamento incluído com sucesso!");
    }

    public void editarAgendamento() {
        persist(PersistAction.UPDATE, "Agendamento alterado com sucesso!");
    }

    public void deletarAgendamento() {
        persist(PersistAction.DELETE, "Agendamento excluído com sucesso!");
    }

    /**
     * Busca agendamentos por ID do funcionário.
     */
    public List<AgendamentoEntity> buscarPorFuncionario(Integer funcionarioId) {
        return ejbFacade.buscarPorFuncionarioId(funcionarioId);
    }

    public List<AgendamentoEntity> buscarPorCliente(Integer clienteId) {
        return ejbFacade.buscarPorClienteId(clienteId);
    }

    private void persist(PersistAction persistAction, String successMessage) {
        try {
            if (persistAction != null) {
                switch (persistAction) {
                    case CREATE:
                        ejbFacade.createReturn(agendamento);
                        break;
                    case UPDATE:
                        ejbFacade.edit(selected);
                        selected = null;
                        break;
                    case DELETE:
                        ejbFacade.remove(selected);
                        selected = null;
                        break;
                    default:
                        break;
                }
            }
            addSuccessMessage(successMessage);
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                addErrorMessage(msg);
            } else {
                addErrorMessage(ex.getLocalizedMessage());
            }
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }
}
