package br.com.gestao.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.google.gson.JsonObject;

import br.com.gestao.entity.Marca;
import br.com.gestao.entity.Patrimonio;
import br.com.gestao.services.MarcaService;
import br.com.gestao.services.PatrimonioService;
import br.com.gestao.services.UtilService;

@ManagedBean (name = "patrimonioBean")
@SessionScoped
public class PatrimonioBean {
	
    private int id;  // obrigatorio
    private int idMarca; // obrigatorio
    private String nome; // obrigatorio
    private String descricao;
    private int numTombo;
    
    private List<Patrimonio> listaPatrimonio;
    private List<Patrimonio> listaPesquisada;
    Patrimonio patrSelecionado;
    
    
	@PostConstruct
    public void init(){
    	this.setPatrSelecionado(new Patrimonio());
    	this.loadTablePatrimonios();
    }
    

/** Getters e Setters */	

	public List<Patrimonio> getListaPesquisada() {
		return listaPesquisada;
	}
	public void setListaPesquisada(List<Patrimonio> listaPesquisada) {
		this.listaPesquisada = listaPesquisada;
	}
	public Patrimonio getPatrSelecionado() {
		return patrSelecionado;
	}
	public void setPatrSelecionado(Patrimonio patrSelecionado) {
		this.patrSelecionado = patrSelecionado;
	}
	public List<Patrimonio> getListaPatrimonio() {
		return listaPatrimonio;
	}
	public void setListaPatrimonio(List<Patrimonio> listaPatrimonio) {
		this.listaPatrimonio = listaPatrimonio;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdMarca() {
		return idMarca;
	}
	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getNumTombo() {
		return numTombo;
	}
	public void setNumTombo(int numTombo) {
		this.numTombo = numTombo;
	}

	
/** Services */	
	public List<Patrimonio> listarPatrimonios()  {
		try {
			return PatrimonioService.listPatrimonios();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Patrimonio getPatrimonioById(int idPatrimonio)  {
		try {
			return PatrimonioService.getPatrimonioById(idPatrimonio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void addPatrimonioService(Patrimonio patr)  {
		try {
			PatrimonioService.addPatrimonio(patr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void editPatrimonioService (Patrimonio marca) {
		try {
			PatrimonioService.editPatrimonio(marca);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deletePatrimonioService (Patrimonio marca) {
		try {
			PatrimonioService.deletePatrimonio(marca);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Patrimonio> pesquisarPatrimonioByMarca (int idMarca) {
		try {
			return UtilService.pesquisarPatrimonioByMarca(idMarca);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	
/** Ações */
	
	public String handleToEditPatrimonio(int id, int idMarca, String nome, String descricao, int numTombo) {
    	this.setId(id);
    	this.setIdMarca(idMarca);
    	this.setNome(nome);
    	this.setDescricao(descricao);
    	this.setNumTombo(numTombo);
		
		return "editar-patrimonio?faces-redirect=true";
	}
	
	public void adicionarPatrimonio() {
		this.patrSelecionado.setIdMarca(this.getIdMarca());
		this.patrSelecionado.setNome(this.getNome());
		this.patrSelecionado.setDescricao(this.getDescricao());
		this.addPatrimonioService(patrSelecionado);
		this.loadTablePatrimonios();
	}
	
	public void editarPatrimonio() {
		System.out.println("EDITANDO EDITANDO " + this.getId() + "      " + this.getNome());
		this.patrSelecionado.setId(this.getId());
		this.patrSelecionado.setIdMarca(this.getIdMarca());
		this.patrSelecionado.setNome(this.getNome());
		this.patrSelecionado.setDescricao(this.getDescricao());
		this.editPatrimonioService(patrSelecionado);
		this.loadTablePatrimonios();
	}
	
	public String deletarPatrimonio(int id) {
		System.out.println("Deletando = " + id );
		this.patrSelecionado.setId(id);
		this.deletePatrimonioService(patrSelecionado);
		this.loadTablePatrimonios();
		return "lista-patrimonios?faces-redirect=true";
	}
	
	
	public void loadTablePatrimonios() {
		this.setListaPatrimonio(this.listarPatrimonios());
	}
	
	public String handleAddPatrimonio () {
		this.setId(0);
		this.setIdMarca(0);
		this.setNome("");
		this.setDescricao("");
		this.setNumTombo(0);
		return "add-patrimonio";
	}
	
	public void getPatrimonioByMarca () {
		this.setListaPesquisada(this.pesquisarPatrimonioByMarca(this.getIdMarca()));
	}
	
	public void pesquisarPatrimonio () {
		Patrimonio patrimonio = this.getPatrimonioById(this.getId());
		if (patrimonio != null) {
	    	this.setId(patrimonio.getId());
	    	this.setIdMarca(patrimonio.getIdMarca());
	    	this.setNome(patrimonio.getNome());
	    	this.setDescricao(patrimonio.getDescricao());
	    	this.setNumTombo(patrimonio.getNumTombo());
		} else {
			this.limparDados();
		}

	}
	public void limparDados() {
    	this.setId(0);
    	this.setIdMarca(0);
    	this.setNome("");
    	this.setDescricao("");
    	this.setNumTombo(0);
	}


}
