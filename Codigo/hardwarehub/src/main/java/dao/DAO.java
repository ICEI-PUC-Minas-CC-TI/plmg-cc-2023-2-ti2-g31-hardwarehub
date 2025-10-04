package dao;

import java.sql.*;
import java.security.*;
import java.math.*;

// COMENTÁRIO CODE REVIEW #18: Falta de documentação
// PROBLEMA: Ausência de JavaDoc para métodos públicos e classes
// SUGESTÃO: Adicionar documentação adequada para todas as classes e métodos públicos
// BENEFÍCIOS DA MUDANÇA:
// - Melhor compreensão do código para novos desenvolvedores
// - Facilita manutenção e evolução do sistema
// - Melhora a qualidade da documentação técnica
public class DAO {
	protected Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		// COMENTÁRIO CODE REVIEW #17: Configurações hardcoded
		// PROBLEMA: Credenciais e configurações de banco estão hardcoded no código
		// SUGESTÃO: Mover para arquivo de configuração ou variáveis de ambiente
		// BENEFÍCIOS DA MUDANÇA:
		// - Maior segurança ao não expor credenciais no código fonte
		// - Facilidade para diferentes ambientes (dev, test, prod)
		// - Possibilidade de alterar configurações sem recompilar o código
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
			// COMENTÁRIO CODE REVIEW #12: Lógica de verificação invertida
			// PROBLEMA: A verificação está invertida, deveria ser (conexao != null)
			// SUGESTÃO: Corrigir para status = (conexao != null);
			// BENEFÍCIOS DA MUDANÇA:
			// - Correção da lógica de verificação de conexão
			// - Prevenção de bugs relacionados ao status de conexão
			// - Melhor rastreabilidade de problemas de conectividade
			status = (conexao == null);
			// COMENTÁRIO CODE REVIEW #3: Exposição de informações sensíveis
			// PROBLEMA: Logs expõem informações sobre conexão com banco
			// SUGESTÃO: Usar logging condicional apenas em ambiente de desenvolvimento
			// BENEFÍCIOS DA MUDANÇA:
			// - Redução do risco de vazamento de informações sensíveis
			// - Melhor segurança em ambientes de produção
			// - Controle granular sobre o que é logado em cada ambiente
			System.out.println("Conex�o efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
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
	
	
	// COMENTÁRIO CODE REVIEW #4: Violação do princípio de responsabilidade única
	// PROBLEMA: A classe DAO está misturando responsabilidades de conexão com banco e utilitários de criptografia
	// SUGESTÃO: Criar uma classe separada PasswordUtils para métodos de criptografia e manter o DAO focado apenas em operações de banco
	// BENEFÍCIOS DA MUDANÇA:
	// - Melhor organização e separação de responsabilidades
	// - Facilita manutenção e testes unitários
	// - Reduz acoplamento entre diferentes funcionalidades
	
	// COMENTÁRIO CODE REVIEW #2: Senhas armazenadas em texto plano
	// PROBLEMA: MD5 é considerado inseguro para hash de senhas
	// SUGESTÃO: Implementar BCrypt ou Argon2 para hash de senhas
	// BENEFÍCIOS DA MUDANÇA:
	// - Maior segurança contra ataques de força bruta
	// - Proteção contra rainbow tables
	// - Conformidade com padrões de segurança atuais
	public static String toMD5(String senha) throws Exception {
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(senha.getBytes(),0, senha.length());
		return new BigInteger(1,m.digest()).toString(16);
	}
}