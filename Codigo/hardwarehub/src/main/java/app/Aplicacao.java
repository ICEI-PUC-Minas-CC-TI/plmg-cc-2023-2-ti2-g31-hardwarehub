package app;

import service.ProdutoService;
import service.UsuarioService;

import static spark.Spark.*;

// COMENTÁRIO DE CODE REVIEW: Sugestão de melhoria - A classe Aplicacao está muito limitada e falta configuração adequada.
// 1. Falta tratamento de CORS - importante para aplicações web modernas.
// 2. Não há endpoints para operações importantes (GET, UPDATE, DELETE).
// 3. Falta configuração de recursos estáticos para servir HTML/CSS/JS.
// 4. Não há tratamento global de exceções - erros podem expor detalhes internos.
// Sugestão: Adicionar configuração de CORS, endpoints completos e tratamento de exceções.
// Benefícios: Melhora funcionalidade da API, facilita integração frontend, melhora segurança e robustez da aplicação.
public class Aplicacao {

	private static ProdutoService produtoService = new ProdutoService();
	private static UsuarioService usuarioService = new UsuarioService();
	
	// COMENTÁRIO DE CODE REVIEW: Sugestão de melhoria - Configuração da API REST está incompleta.
	// 1. Porta hardcoded: usar variável de ambiente para permitir configuração flexível.
	// 2. Apenas endpoints POST: faltam GET, PUT/PATCH, DELETE para CRUD completo.
	// 3. Falta endpoint de autenticação para usuários.
	// 4. Nomenclatura inconsistente: "/cadastro" para usuário mas "/produto" para produto.
	// Sugestão: Padronizar rotas (/api/usuarios, /api/produtos) e implementar todos os métodos HTTP necessários.
	// Benefícios: Melhora flexibilidade de configuração, completa funcionalidade CRUD, melhora segurança e padroniza a API.
	public static void main(String[] args) {
		
		port(5678);
		post("/produto", (request, response) -> produtoService.add(request, response));
		post("/cadastro", (request, response) -> usuarioService.add(request, response));
	}
}
