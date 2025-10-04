package dao;

import java.sql.*;
import java.security.*;
import java.math.*;

public class DAO {
	// COMENTÁRIO DE CODE REVIEW: Sugestão de melhoria - A classe DAO contém configurações de banco de dados hardcoded diretamente no código.
	// Para melhorar a manutenibilidade e segurança, considere mover essas configurações para um arquivo de propriedades ou variáveis de ambiente.
	// Benefícios: Facilita mudanças de ambiente (desenvolvimento, teste, produção) sem recompilação e evita exposição de credenciais no código.
	protected Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		// COMENTÁRIO DE CODE REVIEW: Problema de segurança - Credenciais de banco de dados expostas no código fonte.
		// A senha "1234" é extremamente fraca e não deveria estar no código. Considere usar um gerenciador de segredos ou variáveis de ambiente.
		// Sugestão: Implementar um sistema de configuração externa para credenciais sensíveis.
		// Benefícios: Reduz risco de vazamento de credenciais, facilita rotação de senhas e melhora a segurança geral do sistema.
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "HardwareHub";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "postgres";
		String password = "1234";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conex�o efetuada com o postgres!");
		} catch (ClassNotFoundException e) {
			// COMENTÁRIO DE CODE REVIEW: Sugestão de melhoria - O tratamento de exceções está apenas logando no console.
			// Para um sistema em produção, considere usar um framework de logging (Log4j, SLF4J) e implementar estratégias de retry para falhas de conexão.
			// Benefícios: Melhor rastreabilidade de problemas e maior robustez do sistema. 
			System.err.println("Conex�o N�O efetuada com o postgres -- Driver n�o encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conex�o N�O efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	
	// COMENTÁRIO DE CODE REVIEW: Problema de segurança crítico - MD5 é um algoritmo de hash criptográfico obsoleto e inseguro.
	// MD5 é vulnerável a ataques de colisão e não é recomendado para hash de senhas. Use algoritmos seguros como bcrypt, scrypt ou Argon2.
	// Sugestão: Implementar bcrypt com salt para hash de senhas, garantindo maior segurança contra ataques de força bruta.
	// Benefícios: Proteção robusta contra ataques de força bruta, resistência a ataques de colisão e conformidade com padrões de segurança atuais.
	public static String toMD5(String senha) throws Exception {
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(senha.getBytes(),0, senha.length());
		return new BigInteger(1,m.digest()).toString(16);
	}
}