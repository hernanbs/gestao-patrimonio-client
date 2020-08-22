package br.com.gestao.managedbean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.servlet.http.Part;

import br.com.gestao.entity.Marca;
import br.com.gestao.services.MarcaService;


@ManagedBean (name = "marcaBean")
@SessionScoped
public class MarcaBean {
	
	int id ;
	String nome;
	List<Marca> listaMarca;
	Marca marcaSelecionada;
	Part filePDF;

	@PostConstruct
    public void init(){
    	this.setMarcaSelecionada(new Marca());
    	this.loadTableMarcas();
    	
    }
	
	
/** Getters e Setters */	
	public Part getFilePDF() {
		return filePDF;
	}
	public void setFilePDF(Part filePDF) {
		this.filePDF = filePDF;
	}
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
	
	public void uploadFile() {
		try(InputStream fileInputStream = this.getFilePDF().getInputStream() ) {
			MarcaService.uploadMarca(this.getFilePDF());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void downloadFile() {
		try {
			MarcaService.downloadMarca("Desafio SOS Tecnologia.pdf", "C:\\Users\\herna\\Desktop\\hernan\\01_testando_download");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
