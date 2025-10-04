package service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import dao.ProdutoDAO;
import model.Produto;
import spark.Request;
import spark.Response;


public class ProdutoService {

	private ProdutoDAO produtoDAO;

	public ProdutoService() {
		// COMENTÁRIO CODE REVIEW #5: Falta de injeção de dependência
		// PROBLEMA: As classes Service estão criando instâncias dos DAOs diretamente, criando alto acoplamento
		// SUGESTÃO: Implementar injeção de dependência através de construtor ou framework como Spring
		// BENEFÍCIOS DA MUDANÇA:
		// - Redução do acoplamento entre camadas
		// - Facilita criação de testes unitários com mocks
		// - Melhor flexibilidade para substituir implementações
		produtoDAO = new ProdutoDAO();
	}

	public Object add(Request request, Response response) {
		// COMENTÁRIO CODE REVIEW #10: Falta de validação de entrada
		// PROBLEMA: Não há validação dos parâmetros recebidos via request antes de processar
		// SUGESTÃO: Implementar validação de entrada para todos os campos obrigatórios
		// BENEFÍCIOS DA MUDANÇA:
		// - Prevenção de erros em tempo de execução
		// - Melhor segurança contra dados maliciosos
		// - Melhor experiência do usuário com mensagens de erro claras
		int ID = Integer.parseInt(request.queryParams("ID"));
		String nome = request.queryParams("nome");
		int ano = Integer.parseInt(request.queryParams("ano"));
		int tempoDeUso = Integer.parseInt(request.queryParams("tempoDeUso"));
		float preco = Float.parseFloat(request.queryParams("dataValidade"));

		//int id = produtoDAO.getMaxId() + 1;

		Produto produto = new Produto(ID, nome, ano, tempoDeUso, preco);

		produtoDAO.insert(produto);

		response.status(201); // 201 Created
		return ID;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Produto produto = (Produto) produtoDAO.get(id);
		
		if (produto != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<produto>\n" + 
            		"\t<id>" + produto.getID() + "</id>\n" +
            		"\t<nome>" + produto.getNome() + "</nome>\n" +
            		"\t<ano>" + produto.getAno() + "</ano>\n" +
            		"\t<tempoDeUso>" + produto.getTempoDeUso() + "</tempoDeUso>\n" +
            		"\t<preco>" + produto.getPreco() + "</preco>\n" +
            		"</produto>\n";
        } else {
            response.status(404); // 404 Not found
            return "Produto " + id + " n�o encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
		Produto produto = (Produto) produtoDAO.get(id);

        if (produto != null) {
        	produto.setNome(request.queryParams("nome"));
        	produto.setAno(Integer.parseInt(request.queryParams("ano")));
        	produto.setTempoDeUso(Integer.parseInt(request.queryParams("tempoDeUso")));
        	produto.setPreco(Float.parseFloat(request.queryParams("preco")));

        	produtoDAO.update(produto);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto n�o encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Produto produto = (Produto) produtoDAO.get(id);

        if (produto != null) {

            produtoDAO.delete(produto.getNome());

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto n�o encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		// COMENTÁRIO CODE REVIEW #16: Uso inadequado de StringBuffer
		// PROBLEMA: StringBuffer sendo usado para operações simples onde StringBuilder seria mais eficiente
		// SUGESTÃO: Usar StringBuilder para operações não thread-safe ou considerar usar bibliotecas de serialização XML
		// BENEFÍCIOS DA MUDANÇA:
		// - Melhor performance em operações não thread-safe
		// - Código mais limpo e moderno
		// - Melhor uso de recursos de memória
		StringBuffer returnValue = new StringBuffer("<produtos type=\"array\">");
		for (Produto produto : produtoDAO.get()) {
			returnValue.append("\n<produto>\n" +  
            		"\t<id>" + produto.getID() + "</id>\n" +
            		"\t<nome>" + produto.getNome() + "</nome>\n" +
            		"\t<ano>" + produto.getAno() + "</ano>\n" +
            		"\t<tempoDeUso>" + produto.getTempoDeUso() + "</tempoDeUso>\n" +
            		"\t<preco>" + produto.getPreco() + "</preco>\n" +
            		"</produto>\n");
		}
		returnValue.append("</produtos>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}