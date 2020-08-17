package br.com.gestao.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;

import br.com.gestao.entity.Marca;
import br.com.gestao.entity.Patrimonio;
import br.com.gestao.services.MarcaService;




@ManagedBean (name = "marcaBean")
@SessionScoped
public class MarcaBean {
	
	int id ;
	String nome;
	List<Marca> listaMarca;
	Marca marcaSelecionada;

	@PostConstruct
    public void init(){
    	this.setMarcaSelecionada(new Marca());
    	Marca a = this.getMarcaById(3);
    	this.setId(a.getId());
    	this.setNome(a.getNome());
    	
    	this.loadTableMarcas();
    	
    }
	
/** Getters e Setters */	
	public Marca getMarcaSelecionada() {
		return marcaSelecionada;
	}
	public void setMarcaSelecionada(Marca marcaSelecionada) {
		this.marcaSelecionada = marcaSelecionada;
	}
	public List<Marca> getListaMarca() {
		return listaMarca;
	}
	public void setListaMarca(List<Marca> listaMarca) {
		this.listaMarca = listaMarca;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
/** Services */
	public List<Marca> listarMarcas()  {
		try {
			return MarcaService.listMarcas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Marca getMarcaById(int idMarca)  {
		try {
			return MarcaService.getMarcaById(idMarca);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void addMarcaService(Marca marca)  {
		try {
			MarcaService.addMarca(marca);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void editMarcaService (Marca marca) {
		try {
			MarcaService.editMarca(marca);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteMarcaService (Marca marca) {
		try {
			MarcaService.deleteMarca(marca);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
/** Ações */
	public String handleToEditMarca(int id, String nome) {
		this.setId(id);
		this.setNome(nome);
		
		return "editar-marca?faces-redirect=true";
	}
	public String handleToAddMarca() {
		this.setId(0);
		this.setNome("");
		return "add-marca";
	}
	
	
	public void editarMarca() {
		System.out.println("EDITANDO EDITANDO " + this.getId() + "      " + this.getNome());
		this.marcaSelecionada.setId(this.getId());
		this.marcaSelecionada.setNome(this.getNome());
		this.editMarcaService(marcaSelecionada);
		this.loadTableMarcas();
	}
	
	public String deletarMarca(int id) {
		System.out.println("Deletando = " + id );
		this.marcaSelecionada.setId(id);
		this.deleteMarcaService(marcaSelecionada);
		this.loadTableMarcas();
		return "lista-marcas?faces-redirect=true";
	}
	
	public void adicionarMarca() {
		System.out.println(this.getNome());
		this.marcaSelecionada.setNome(this.getNome());
		this.addMarcaService(marcaSelecionada);
		this.loadTableMarcas();
	}

	public void loadTableMarcas() {
		this.setListaMarca(this.listarMarcas());
	}
	
	public void pesquisarMarca () {
		Marca marca = this.getMarcaById(this.getId());
		if (marca != null) {
	    	this.setId(marca.getId());
	    	this.setNome(marca.getNome());
		}else {
			this.limparDados();
		}

	}
	public void limparDados() {
    	this.setId(0);
    	this.setNome("");
	}
	
}
