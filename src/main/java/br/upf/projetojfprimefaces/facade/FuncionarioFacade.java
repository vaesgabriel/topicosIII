package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.FuncionarioEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class FuncionarioFacade extends AbstractFacade<FuncionarioEntity> {

    @PersistenceContext(unitName = "ProjetojfprimefacesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FuncionarioFacade() {
        super(FuncionarioEntity.class);
    }

    private List<FuncionarioEntity> entityList;

    /**
     * Buscar todos os funcionários
     */
    public List<FuncionarioEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT f FROM FuncionarioEntity f ORDER BY f.nome");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<FuncionarioEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

    /**
     * Buscar um funcionário por email e senha
     *
     * @param email
     * @param senha
     * @return
     */
    public FuncionarioEntity buscarPorEmail(String email, String senha) {
        try {
            Query query = getEntityManager().createQuery(
                    "SELECT f FROM FuncionarioEntity f "
                    + "WHERE LOWER(TRIM(f.email)) = LOWER(TRIM(:email)) AND TRIM(f.senha) = TRIM(:senha)"
            );
            query.setParameter("email", email);
            query.setParameter("senha", senha);

            List<FuncionarioEntity> resultado = query.getResultList();

            if (!resultado.isEmpty()) {
                return resultado.get(0);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar funcionário: " + e);
        }

        return null;
    }
}
