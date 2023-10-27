package model;

public class Produto {
	private int ID;
	private String nome;
	private int ano;
	private int tempoDeUso;
	private double preco;
	
	//----------------------Construtores---------------------------
	
	public Produto() {
		ID = -1;
		nome = "";
		ano = -1;
		tempoDeUso = -1;
		preco = -1;
	}
	
	public Produto(int ID, String nome, int ano, int tempoDeUso, double preco) {
		this.ID = ID;
		this.nome = nome;
		this.ano = ano;
		this.tempoDeUso = tempoDeUso;
		this.preco = preco;
	}

	
//--------------------------Gets e Sets------------------------------
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getTempoDeUso() {
		return tempoDeUso;
	}

	public void setTempoDeUso(int tempoDeUso) {
		this.tempoDeUso = tempoDeUso;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	
}
	