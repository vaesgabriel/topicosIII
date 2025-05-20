package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.AgendamentoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AgendamentoFacade extends AbstractFacade<AgendamentoEntity> {

    @PersistenceContext(unitName = "ProjetojfprimefacesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgendamentoFacade() {
        super(AgendamentoEntity.class);
    }

    private List<AgendamentoEntity> entityList;

    public List<AgendamentoEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT a FROM AgendamentoEntity a ORDER BY a.dataHora DESC");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<AgendamentoEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

    public List<AgendamentoEntity> buscarPorFuncionarioId(Integer funcionarioId) {
        List<AgendamentoEntity> resultado = new ArrayList<>();
        try {
            Query query = getEntityManager()
                    .createQuery("SELECT a FROM AgendamentoEntity a WHERE a.funcionario.id = :funcionarioId");
            query.setParameter("funcionarioId", funcionarioId);
            if (!query.getResultList().isEmpty()) {
                resultado = (List<AgendamentoEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return resultado;
    }

    public List<AgendamentoEntity> buscarPorVeiculoId(Integer veiculoId) {
        List<AgendamentoEntity> resultado = new ArrayList<>();
        try {
            Query query = getEntityManager()
                    .createQuery("SELECT a FROM AgendamentoEntity a WHERE a.veiculo.id = :veiculoId");
            query.setParameter("veiculoId", veiculoId);
            if (!query.getResultList().isEmpty()) {
                resultado = (List<AgendamentoEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return resultado;
    }
}
