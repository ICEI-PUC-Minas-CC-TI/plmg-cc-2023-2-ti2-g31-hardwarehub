package app;

import java.util.List;

import dao.DAO;
import dao.ProdutoDAO;
import dao.UsuarioDAO;
import model.Produto;
import model.Usuario;
//import java.util.Scanner;

public class Principal {
	
	public static void main(String[] args) throws Exception {
		
		
		//Scanner sc = new Scanner(System.in);
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		ProdutoDAO produtoDAO = new ProdutoDAO();
		// COMENTÁRIO CODE REVIEW #9: Código comentado desnecessário
		// PROBLEMA: Grandes blocos de código comentado poluem o código e dificultam a manutenção
		// SUGESTÃO: Remover código comentado ou usar sistema de controle de versão para histórico
		// BENEFÍCIOS DA MUDANÇA:
		// - Código mais limpo e legível
		// - Redução da confusão durante manutenção
		// - Melhor organização do código fonte
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