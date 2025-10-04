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
		produtoDAO = new ProdutoDAO();
	}

	// COMENTÁRIO DE CODE REVIEW: Problemas de validação e erro de nomenclatura.
	// 1. Falta validação: não verifica se os parâmetros são nulos antes de fazer parse - pode causar NullPointerException.
	// 2. Erro de nomenclatura: queryParams("dataValidade") deveria ser "preco" - está pegando o parâmetro errado.
	// 3. Falta tratamento de NumberFormatException para entradas inválidas.
	// Sugestão: Validar parâmetros obrigatórios e retornar 400 Bad Request para entradas inválidas.
	// Benefícios: Previne erros de runtime, melhora robustez da API e fornece feedback claro para clientes sobre entradas inválidas.
	public Object add(Request request, Response response) {
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

	// COMENTÁRIO DE CODE REVIEW: Inconsistência entre busca e deleção.
	// O método busca produto por ID mas deleta por NOME (produto.getNome()).
	// Isso pode deletar produtos com mesmo nome ou falhar se o método delete() espera ID.
	// Verificando ProdutoDAO.delete(), ele espera ID mas usa coluna "u_Id" incorreta.
	// Sugestão: Alterar para produtoDAO.delete(id) para manter consistência.
	// Benefícios: Garante deleção precisa, evita deleções incorretas e mantém consistência na API.
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