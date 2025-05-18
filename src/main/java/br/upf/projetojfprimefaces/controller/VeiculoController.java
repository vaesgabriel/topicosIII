package br.upf.projetojfprimefaces.controller;

import br.upf.projetojfprimefaces.entity.VeiculoEntity;
import br.upf.projetojfprimefaces.facade.VeiculoFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class VeiculoController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private VeiculoFacade veiculoFacade;

    private VeiculoEntity veiculo;
    private List<VeiculoEntity> veiculos;

    @PostConstruct
    public void init() {
        veiculo = null; // ← começa sem seleção
        veiculos = veiculoFacade.findAll();
    }

    // Método chamado ao clicar em "Incluir Veículo"
    public void novo() {
        this.veiculo = new VeiculoEntity();
    }

    // Salvar novo ou editar existente
    public void salvar() {
        if (veiculo != null) {
            if (veiculo.getId() == null) {
                veiculoFacade.create(veiculo);
            } else {
                veiculoFacade.edit(veiculo);
            }
            veiculo = null; // ← limpa seleção após salvar
            veiculos = veiculoFacade.findAll(); // Atualiza a tabela
        }
    }

    // Excluir veículo
    public void excluir(VeiculoEntity v) {
        if (v != null && v.getId() != null) {
            veiculoFacade.remove(v);
            veiculos = veiculoFacade.findAll();
            veiculo = null; // ← limpa seleção após excluir
        }
    }

    // Getters e Setters
    public VeiculoEntity getVeiculo() {
        return veiculo; // ← NÃO cria novo objeto aqui
    }

    public void setVeiculo(VeiculoEntity veiculo) {
        this.veiculo = veiculo;
    }

    public List<VeiculoEntity> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<VeiculoEntity> veiculos) {
        this.veiculos = veiculos;
    }

    public void atualizarDetalhes() {
        // opcional
    }
}
