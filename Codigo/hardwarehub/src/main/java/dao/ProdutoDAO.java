package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Produto;

public class ProdutoDAO extends DAO {
	
	public ProdutoDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	// COMENTÁRIO DE CODE REVIEW: Vulnerabilidade crítica de segurança - SQL Injection.
	// A concatenação direta de valores na query SQL permite ataques de SQL Injection.
	// Sugestão: Usar PreparedStatement com parâmetros para prevenir SQL Injection.
	// Exemplo: PreparedStatement ps = conexao.prepareStatement("INSERT INTO produto (p_id, p_nome, p_ano, p_tempoDeUso, p_preco) VALUES (?, ?, ?, ?, ?)");
	// Benefícios: Previne ataques de SQL Injection, melhora performance (queries pré-compiladas) e garante integridade dos dados.
	public boolean insert(Produto produto) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO produto (p_id , p_nome, p_ano, p_tempoDeUso, p_preco) "
				       + "VALUES ('" + produto.getID() + "', '" +produto.getNome() + "', '" + produto.getAno() + "', '" + produto.getTempoDeUso() + "', '" + produto.getPreco() + "');";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	// COMENTÁRIO DE CODE REVIEW: Problema de segurança - SQL Injection e inconsistência de nomenclatura.
	// 1. Concatenação direta permite SQL Injection - usar PreparedStatement.
	// 2. Inconsistência: query usa "p_id" mas ResultSet acessa "ID" - pode causar SQLException.
	// 3. Tratamento genérico de Exception pode mascarar problemas específicos.
	// Benefícios: Previne SQL Injection, evita erros de runtime e facilita debugging com tratamento específico de exceções.
	public Produto get(int ID) {
		Produto produto = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE p_id=" + ID;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 produto = new Produto(rs.getInt("ID"), rs.getString("nome"), rs.getInt("ano"), rs.getInt("tempoDeUso"), rs.getFloat("preco"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produto;
	}
	
	
	public List<Produto> get() {
		return get("");
	}

	
	public List<Produto> getOrderByNome() {
		return get("nome");		
	}
	
	
	// COMENTÁRIO DE CODE REVIEW: Erro conceitual - método getOrderByCPF() está ordenando por "ID" em vez de CPF.
	// Produto não possui campo CPF, apenas ID, nome, ano, tempoDeUso e preco.
	// Sugestão: Renomear para getOrderById() ou remover se não for necessário.
	// Benefícios: Evita confusão conceitual, melhora clareza do código e previne erros de lógica.
	public List<Produto> getOrderByCPF() {
		return get("ID");		
	}
	
	
	// COMENTÁRIO DE CODE REVIEW: Erro conceitual - método getOrderByEmail() está tentando ordenar por "email".
	// Produto não possui campo email, apenas ID, nome, ano, tempoDeUso e preco.
	// Sugestão: Remover este método ou implementar ordenação por um campo válido do Produto.
	// Benefícios: Evita erros de SQL, melhora consistência do modelo de dados e facilita manutenção.
	public List<Produto> getOrderByEmail() {
		return get("email");		
	}
	
	
	private List<Produto> get(String orderBy) {	
	
		List<Produto> produtos = new ArrayList<Produto>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Produto u = new Produto(rs.getInt("ID"), rs.getString("nome"), rs.getInt("ano"), rs.getInt("tempoDeUso"), rs.getFloat("preco"));
	            produtos.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produtos;
	}
	
	
	// COMENTÁRIO DE CODE REVIEW: Múltiplos problemas de segurança e lógica.
	// 1. SQL Injection: concatenação direta de valores na query.
	// 2. Lógica incorreta: WHERE p_nome = " + produto.getNome() sem aspas - causará erro SQL.
	// 3. Atualização de ID: geralmente ID não deve ser alterado, é chave primária.
	// 4. Falta validação: não verifica se o produto existe antes de atualizar.
	// Benefícios: Previne SQL Injection, evita erros de SQL, mantém integridade referencial e garante operações seguras.
	public boolean update(Produto produto) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE produto SET nome = '" + produto.getNome() + "', ID = '"  
				       + produto.getID() + "', preco = '" + produto.getPreco() + "'"
					   + " WHERE p_nome = " + produto.getNome();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	// COMENTÁRIO DE CODE REVIEW: Problemas de segurança e nomenclatura.
	// 1. SQL Injection: concatenação direta permite ataques.
	// 2. Nomenclatura incorreta: "u_Id" deveria ser "p_id" (tabela produto, não usuario).
	// 3. Falta validação: não verifica se o produto existe antes de deletar.
	// 4. Falta verificação de dependências: produtos podem estar sendo referenciados em outras tabelas.
	// Benefícios: Previne SQL Injection, evita erros de SQL, mantém integridade referencial e garante deleção segura.
	public boolean delete(int ID) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM produto WHERE u_Id = " + ID;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	// COMENTÁRIO DE CODE REVIEW: Problemas conceituais e de segurança.
	// 1. Método "autenticar" não faz sentido para Produto - autenticação é para usuários.
	// 2. SQL Injection: concatenação direta permite ataques.
	// 3. Uso incorreto de LIKE para ID numérico - deveria usar =.
	// 4. Sugestão: Renomear para "existe" ou "buscarPorIdENome" se realmente necessário.
	// Benefícios: Melhora clareza conceitual, previne SQL Injection e otimiza performance com operadores apropriados.
	public boolean autenticar(int ID, String nome) {
		boolean resp = false;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE p_nome LIKE '" + nome + "' AND p_id LIKE '" + ID  + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}	
	
	// COMENTÁRIO DE CODE REVIEW: Inconsistência de nomenclatura e tratamento de exceções.
	// 1. Query usa "id" mas alias é "p_id" - inconsistência pode causar SQLException.
	// 2. ResultSet acessa "id" em vez do alias "p_id".
	// 3. Retorno 0 em caso de erro pode ser confundido com ID válido.
	// 4. Sugestão: Usar Optional<Integer> ou lançar exceção específica para indicar falha.
	// Benefícios: Evita erros de runtime, melhora clareza do código e facilita tratamento de erros.
	public int getMaxID() {
		try {
			Statement st = conexao.createStatement();
			String sql = "SELECT MAX(id) AS p_id FROM produto";
			ResultSet rs = st.executeQuery(sql);
			int maxID = rs.getInt("id");
			return maxID;
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return 0;
		}
	}
}