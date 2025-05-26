package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.ClienteEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ClienteFacade extends AbstractFacade<ClienteEntity> {

    @PersistenceContext(unitName = "ProjetojfprimefacesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(ClienteEntity.class);
    }

    private List<ClienteEntity> entityList;

    /**
     * Buscar todos os clientes
     */
    public List<ClienteEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT c FROM ClienteEntity c ORDER BY c.nome");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<ClienteEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

    public ClienteEntity buscarPorEmailECpf(String email, String cpf) {
        try {
            Query query = getEntityManager().createQuery(
                    "SELECT c FROM ClienteEntity c "
                    + "WHERE LOWER(TRIM(c.email)) = LOWER(TRIM(:email)) AND TRIM(c.cpf) = TRIM(:cpf)"
            );
            query.setParameter("email", email);
            query.setParameter("cpf", cpf);

            List<ClienteEntity> resultado = query.getResultList();

            if (!resultado.isEmpty()) {
                return resultado.get(0);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente: " + e);
        }

        return null;
    }

}
