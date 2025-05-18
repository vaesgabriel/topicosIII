package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.VeiculoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class VeiculoFacade extends AbstractFacade<VeiculoEntity> {

    @PersistenceContext(unitName = "ProjetojfprimefacesPU")
    private EntityManager em;

    public VeiculoFacade() {
        super(VeiculoEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    // Se quiser expor explicitamente o método create (opcional, já está herdado)
    @Override
    public void create(VeiculoEntity entity) {
        getEntityManager().persist(entity);
    }

    // Caso queira também atualizar (edit)
    @Override
    public void edit(VeiculoEntity entity) {
        getEntityManager().merge(entity);
    }

    // E para deletar
    @Override
    public void remove(VeiculoEntity entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    // Lista todos
    @Override
    public List<VeiculoEntity> findAll() {
        return super.findAll();
    }
}
