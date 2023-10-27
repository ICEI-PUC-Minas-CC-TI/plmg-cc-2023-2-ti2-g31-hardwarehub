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
		
		System.out.println("\n\n==== Inserir usuário === ");
		Usuario usuario = new Usuario(7 , "Elton Jhon", "11223312321", "elton@gmail.com", "111111", "02/12/1999", 'M');
		if(usuarioDAO.insert(usuario) == true) {
			System.out.println("Inserção com sucesso -> " + usuario.toString());
		}
		
		System.out.println("\n\n==== Inserir produto=== ");
		Produto produto = new Produto(3 , "i5-12400", 2021, 2, 1200.90);
		if(produtoDAO.insert(produto) == true) {
			System.out.println("Inserção com sucesso -> " + produto.toString());
		}
		/*
		System.out.println("\n\n==== Testando autenticação ===");
		System.out.println("Usuário (" + usuario.getNome() + "): " + usuarioDAO.autenticar("Jeferson", "123123"));
			
		System.out.println("\n\n==== Mostrar usuários do sexo masculino === ");
		List<Usuario> usuarios = usuarioDAO.getSexoMasculino();
		for (Usuario u: usuarios) {
			System.out.println(u.toString());
		}

		System.out.println("\n\n==== Atualizar senha (código (" + usuario.getNome() + ") === ");
		usuario.setSenha(DAO.toMD5("pablo"));
		usuarioDAO.update(usuario);
		
		System.out.println("\n\n==== Testando autenticação ===");
		System.out.println("Usuário (" + usuario.getNome() + "): " + usuarioDAO.autenticar("Jeferson", DAO.toMD5("pablo")));		
		
		System.out.println("\n\n==== Invadir usando SQL Injection ===");
		System.out.println("Usuário (" + usuario.getNome() + "): " + usuarioDAO.autenticar("pablo", "x' OR 'x' LIKE 'x"));

		System.out.println("\n\n==== Mostrar usuários ordenados por código === ");
		usuarios = usuarioDAO.getOrderByNome();
		for (Usuario u: usuarios) {
			System.out.println(u.toString());
		}
		
		System.out.println("\n\n==== Excluir usuário (código " + usuario.getNome() + ") === ");
		usuarioDAO.delete(usuario.getNome());
		
		System.out.println("\n\n==== Mostrar usuários ordenados por login === ");
		usuarios = usuarioDAO.getOrderByCPF();
		for (Usuario u: usuarios) {
			System.out.println(u.toString());
		}*/
	}
}