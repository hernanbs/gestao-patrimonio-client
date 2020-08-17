package br.com.gestao.entity;


public class Patrimonio {
    private int id;  
    private int idMarca; 
    private String nome; 
    private String descricao;
    private int numTombo;
    
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
}
