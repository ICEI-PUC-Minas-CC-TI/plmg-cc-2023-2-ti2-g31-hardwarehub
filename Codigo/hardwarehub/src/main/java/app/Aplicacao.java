package app;

import service.ProdutoService;
import service.UsuarioService;

import static spark.Spark.*;

public class Aplicacao {

	// COMENTÁRIO CODE REVIEW #5: Falta de injeção de dependência
	// PROBLEMA: As classes Service estão sendo criadas como campos estáticos, criando alto acoplamento
	// SUGESTÃO: Implementar injeção de dependência através de construtor ou framework como Spring
	// BENEFÍCIOS DA MUDANÇA:
	// - Redução do acoplamento entre componentes
	// - Facilita testes unitários e integração
	// - Melhor flexibilidade para configuração e substituição
	private static ProdutoService produtoService = new ProdutoService();
	private static UsuarioService usuarioService = new UsuarioService();
	
	public static void main(String[] args) {
		
		port(5678);
		post("/produto", (request, response) -> produtoService.add(request, response));
		post("/cadastro", (request, response) -> usuarioService.add(request, response));
	}
}
