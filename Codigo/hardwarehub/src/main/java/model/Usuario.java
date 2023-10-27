package model;


public class Usuario {
	private int ID;
	private String nome;
	private String CPF;
	private String email;
	private String senha;
	private String dataDeNascimento;
	private char sexo;
	
	//-------------------------Construtores--------------------------
	
	public Usuario() {
		ID = -1;
		nome = "";
		CPF = "";
		email = "";
		senha = "";
		dataDeNascimento = "";
		sexo = 'n';
	}
	
	public Usuario(int ID, String nome, String CPF, String email, String senha, String dataDeNascimento, char sexo) {
		this.ID = ID;
		this.nome = nome;
		this.CPF = CPF;
		this.email = email;
		this.senha = senha;
		this.dataDeNascimento = dataDeNascimento;
		this.sexo = sexo;
	}
	
	//-------------------------Get and Sets--------------------------

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(String dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}
	
	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	
	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", CPF=" + CPF + ", sexo=" + sexo + ", email=" + email + ", senha=" + senha + "Data de Nascimento" + dataDeNascimento + "]";
	}
	
}
