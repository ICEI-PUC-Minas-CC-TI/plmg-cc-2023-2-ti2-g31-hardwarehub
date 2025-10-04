package model;

// COMENTÁRIO DE CODE REVIEW: Sugestão de melhoria - A classe Produto não possui validações nos setters.
// Considere adicionar validações para garantir integridade dos dados (ex: preço não pode ser negativo, nome não pode ser vazio).
// Benefícios: Prevenir estados inválidos do objeto, falhas silenciosas em tempo de execução e garantir consistência dos dados.
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
	
	// COMENTÁRIO DE CODE REVIEW: Sugestão de melhoria - Falta implementação do método toString().
	// Um método toString() bem implementado facilita debugging e logging.
	// Sugestão: Implementar toString() para retornar uma representação legível do produto com todos os seus atributos.
	// Exemplo: return "Produto [ID=" + ID + ", nome=" + nome + ", ano=" + ano + ", tempoDeUso=" + tempoDeUso + ", preco=" + preco + "]";
	// Benefícios: Facilita debugging, melhora logging e torna o código mais legível durante desenvolvimento e manutenção.
	
}
	