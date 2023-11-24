package app;

import service.ProdutoService;
import service.UsuarioService;

import static spark.Spark.*;

public class Aplicacao {

	private static ProdutoService produtoService = new ProdutoService();
	private static UsuarioService usuarioService = new UsuarioService();
	
	public static void main(String[] args) {
		
		port(5678);
		post("/produto", (request, response) -> produtoService.add(request, response));
		post("/cadastro", (request, response) -> usuarioService.add(request, response));
	}
}