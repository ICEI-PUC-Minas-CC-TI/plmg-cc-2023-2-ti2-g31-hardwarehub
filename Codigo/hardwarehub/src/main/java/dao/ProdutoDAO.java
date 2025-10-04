package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Produto;

// COMENTÁRIO CODE REVIEW #6: Ausência de interface para DAOs
// PROBLEMA: Não há interfaces definidas para os DAOs, dificultando testes e substituição de implementações
// SUGESTÃO: Criar interfaces IProdutoDAO e IUsuarioDAO e implementá-las nas classes concretas
// BENEFÍCIOS DA MUDANÇA:
// - Facilita criação de mocks para testes unitários
// - Permite substituição de implementações sem alterar código cliente
// - Melhora a testabilidade e manutenibilidade do código
public class ProdutoDAO extends DAO {
	
	public ProdutoDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	public boolean insert(Produto produto) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			// COMENTÁRIO CODE REVIEW #1: Vulnerabilidade crítica de SQL Injection
			// PROBLEMA: Consultas SQL construídas através de concatenação de strings sem sanitização
			// SUGESTÃO: Implementar PreparedStatements para todas as operações de banco de dados
			// BENEFÍCIOS DA MUDANÇA:
			// - Prevenção de ataques de SQL Injection
			// - Melhor performance através de cache de consultas
			// - Maior segurança e conformidade com padrões de segurança
			String sql = "INSERT INTO produto (p_id , p_nome, p_ano, p_tempoDeUso, p_preco) "
				       + "VALUES ('" + produto.getID() + "', '" +produto.getNome() + "', '" + produto.getAno() + "', '" + produto.getTempoDeUso() + "', '" + produto.getPreco() + "');";
			// COMENTÁRIO CODE REVIEW #3: Exposição de informações sensíveis
			// PROBLEMA: Consultas SQL sendo impressas no console, expondo dados sensíveis
			// SUGESTÃO: Remover ou usar logging condicional apenas em ambiente de desenvolvimento
			// BENEFÍCIOS DA MUDANÇA:
			// - Redução do risco de vazamento de informações sensíveis
			// - Melhor segurança em ambientes de produção
			// - Controle granular sobre o que é logado em cada ambiente
			System.out.println(sql);
			st.executeUpdate(sql);
			// COMENTÁRIO CODE REVIEW #13: Recursos não fechados adequadamente
			// PROBLEMA: Statement é fechado apenas em caso de sucesso, podendo causar vazamento de recursos
			// SUGESTÃO: Usar try-with-resources para garantir fechamento automático dos recursos
			// BENEFÍCIOS DA MUDANÇA:
			// - Prevenção de vazamentos de memória e recursos
			// - Código mais limpo e menos propenso a erros
			// - Melhor gerenciamento automático de recursos
			st.close();
			status = true;
		} catch (SQLException u) {  
			// COMENTÁRIO CODE REVIEW #11: Tratamento inadequado de exceções
			// PROBLEMA: SQLException está sendo encapsulada em RuntimeException sem contexto adequado
			// SUGESTÃO: Criar exceções customizadas ou tratar adequadamente
			// BENEFÍCIOS DA MUDANÇA:
			// - Melhor rastreabilidade de erros e debugging
			// - Tratamento mais específico e contextualizado
			// - Facilita manutenção e correção de problemas
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Produto get(int ID) {
		Produto produto = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			// 🔒 COMENTÁRIO CODE REVIEW #1: Vulnerabilidade crítica de SQL Injection
			// PROBLEMA: Consultas SQL construídas através de concatenação de strings sem sanitização
			// SUGESTÃO: Implementar PreparedStatements para todas as operações de banco de dados
			String sql = "SELECT * FROM produto WHERE p_id=" + ID;
			// 🔒 COMENTÁRIO CODE REVIEW #3: Exposição de informações sensíveis
			// PROBLEMA: Consultas SQL sendo impressas no console, expondo dados sensíveis
			// SUGESTÃO: Remover ou usar logging condicional apenas em ambiente de desenvolvimento
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	// COMENTÁRIO CODE REVIEW #19: Inconsistência nos nomes de campos
	        	// PROBLEMA: Uso inconsistente de nomes de colunas (ID vs p_id, nome vs p_nome)
	        	// SUGESTÃO: Padronizar os nomes de campos entre modelo e banco de dados
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
	
	
	public List<Produto> getOrderByCPF() {
		// COMENTÁRIO CODE REVIEW #14: Consultas SQL ineficientes
		// PROBLEMA: Método getOrderByCPF() está ordenando por ID em vez de CPF
		// SUGESTÃO: Corrigir os campos de ordenação e adicionar índices no banco de dados
		return get("ID");		
	}
	
	
	public List<Produto> getOrderByEmail() {
		return get("email");		
	}
	
	
	private List<Produto> get(String orderBy) {	
		// COMENTÁRIO CODE REVIEW #15: Falta de paginação
		// PROBLEMA: Método get() retorna todos os registros sem limite, podendo causar problemas de performance
		// SUGESTÃO: Implementar paginação com LIMIT e OFFSET
		// BENEFÍCIOS DA MUDANÇA:
		// - Melhor performance com grandes volumes de dados
		// - Redução do uso de memória
		// - Melhor experiência do usuário com carregamento mais rápido
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
	
	
	// COMENTÁRIO CODE REVIEW #20: Método autenticar inadequado para ProdutoDAO
	// PROBLEMA: Método autenticar não faz sentido para produtos e parece ser código copiado de UsuarioDAO
	// SUGESTÃO: Remover método desnecessário ou implementar funcionalidade específica para produtos se necessário
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