package app;

import java.util.List;

import dao.DAO;
import dao.ProdutoDAO;
import dao.UsuarioDAO;
import model.Produto;
import model.Usuario;
//import java.util.Scanner;

// COMENTÁRIO DE CODE REVIEW: Sugestão de melhoria - A classe Principal contém muito código comentado.
// Código comentado (linhas 20-54) dificulta manutenção e pode causar confusão sobre o que está realmente em uso.
// Se o código é necessário para testes, considere criar classes de teste adequadas (JUnit).
// Se não é mais necessário, remova-o completamente - o histórico fica no controle de versão (Git).
// Benefícios: Melhora legibilidade do código, facilita manutenção, evita confusão e mantém o código limpo e organizado.
public class Principal {
	
	// COMENTÁRIO DE CODE REVIEW: Problema de design - Classe de teste executando operações diretas no banco.
	// Esta classe está sendo usada como script de teste, mas executa operações reais no banco de dados.
	// Problemas: 1) Mistura código de teste com produção. 2) Sem rollback automático. 3) Pode afetar dados reais.
	// Sugestão: Criar testes unitários com JUnit e usar banco de dados de teste ou mocks.
	// Benefícios: Separa teste de produção, previne perda de dados, facilita automação de testes e melhora qualidade do código.
	public static void main(String[] args) throws Exception {
		
		
		//Scanner sc = new Scanner(System.in);
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		ProdutoDAO produtoDAO = new ProdutoDAO();
		/*
		System.out.println("\n\n==== Inserir usu�rio === ");
		Usuario usuario = new Usuario(9 , "Antonia", "14987257642", "antinia@hotmail.com", "112211", "12/03/1999", 'F');
		if(usuarioDAO.insert(usuario) == true) {
			System.out.println("Inser��o com sucesso -> " + usuario.toString());
		}
		
		System.out.println("\n\n==== Inserir produto=== ");
		Produto produto = new Produto(6 , "RTX 4090", 2023, 0, 12000.90);
		if(produtoDAO.insert(produto) == true) {
			System.out.println("Inser��o com sucesso -> " + produto.toString());
		}
		
		System.out.println("\n\n==== Testando autentica��o ===");
		System.out.println("Usu�rio (Joel): "+ usuarioDAO.autenticar("Joel", "009783"));
		
		
		System.out.println("\n\n==== Mostrar usu�rios do sexo masculino === ");
		List<Usuario> usuarios = usuarioDAO.getSexoMasculino();
		for (Usuario u: usuarios) {
			System.out.println(u.toString());
		}
		*/
		/*
		
		System.out.println("\n\n==== Atualizar senha (c�digo (" + usuario.getNome() + ") === ");
		usuario.setSenha(DAO.toMD5("pablo"));
		usuarioDAO.update(usuario);
		
		System.out.println("\n\n==== Testando autentica��o ===");
		System.out.println("Usu�rio (" + usuario.getNome() + "): " + usuarioDAO.autenticar("Jeferson", DAO.toMD5("pablo")));		
		
		// COMENTÁRIO DE CODE REVIEW: Problema crítico - O código demonstra conhecimento da vulnerabilidade de SQL Injection mas não corrige.
		// Este comentário mostra teste de SQL Injection, mas o código em UsuarioDAO.autenticar() permanece vulnerável.
		// Isso indica que a equipe sabe do problema mas não implementou a solução.
		// Ação necessária: Corrigir URGENTEMENTE usando PreparedStatement em todos os métodos DAO.
		// Benefícios: Elimina vulnerabilidades críticas de segurança, protege contra ataques e melhora confiabilidade do sistema.
		System.out.println("\n\n==== Invadir usando SQL Injection ===");
		System.out.println("Usu�rio (" + usuario.getNome() + "): " + usuarioDAO.autenticar("pablo", "x' OR 'x' LIKE 'x"));
		*/
	
		System.out.println("\n\n==== Mostrar usu�rios ordenados por nome === ");
		List<Usuario> usuarios = usuarioDAO.getOrderByNome();
		for (Usuario u: usuarios) {
			System.out.println(u.toString());
		}
		
		System.out.println("\n\n==== Excluir usu�rio (c�digo 9 ) === ");
		usuarioDAO.delete(9);
		
		System.out.println("\n\n==== Mostrar usu�rios ordenados por CPF === ");
		usuarios = usuarioDAO.getOrderByCPF();
		for (Usuario u: usuarios) {
			System.out.println(u.toString());
		}
	}
}