package service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;


public class UsuarioService {

	private UsuarioDAO usuarioDAO;

	public UsuarioService() {
		usuarioDAO = new UsuarioDAO();
	}

	// COMENTÁRIO DE CODE REVIEW: Problemas críticos de segurança e validação.
	// 1. Senha não está sendo hasheada antes de salvar - armazenamento em texto plano é vulnerabilidade crítica.
	// 2. Falta validação de CPF, email e outros campos obrigatórios.
	// 3. Não verifica se os parâmetros são nulos antes de usar - pode causar NullPointerException.
	// 4. Falta tratamento de NumberFormatException.
	// Sugestão: Hash da senha com bcrypt, validar todos os campos e retornar 400 Bad Request para entradas inválidas.
	// Benefícios: Protege dados sensíveis, previne erros de runtime, garante integridade dos dados e melhora segurança geral.
	public Object add(Request request, Response response) {
		int ID = Integer.parseInt(request.queryParams("ID"));
		String nome = request.queryParams("nome");
		String CPF = request.queryParams("CPF");
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");
		String dataDeNascimento = request.queryParams("dataDeNascimento");
		char sexo = request.queryParams("sexo").charAt(0);

		//int id = produtoDAO.getMaxId() + 1;

		Usuario usuario = new Usuario(ID, nome, CPF, email, senha, dataDeNascimento, sexo);

		usuarioDAO.insert(usuario);

		response.status(201); // 201 Created
		return ID;
	}

	// COMENTÁRIO DE CODE REVIEW: Problemas críticos de segurança e nomenclatura.
	// 1. VULNERABILIDADE CRÍTICA: Está retornando a senha do usuário na resposta - nunca exponha senhas em APIs.
	// 2. Tag XML incorreta: usando "<produto>" em vez de "<usuario>".
	// 3. Falta sanitização dos dados - pode causar XML Injection.
	// Sugestão: Remover campo senha da resposta e corrigir tags XML para "usuario".
	// Benefícios: Protege dados sensíveis, melhora segurança da API, evita XML Injection e garante resposta correta.
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":ID"));
		
		Usuario usuario = (Usuario) usuarioDAO.get(id);
		
		if (usuario != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<produto>\n" + 
            		"\t<id>" + usuario.getID() + "</id>\n" +
            		"\t<nome>" + usuario.getNome() + "</nome>\n" +
            		"\t<CPF>" + usuario.getCPF() + "</CPF>\n" +
            		"\t<email>" + usuario.getEmail() + "</email>\n" +
            		"\t<senha>" + usuario.getSenha() + "</senha>\n" +
            		"\t<dataDeNascimento>" + usuario.getDataDeNascimento() + "</dataDeNascimento>\n" +
            		"\t<sexo>" + usuario.getSexo() + "</sexo>\n" +
            		"</produto>\n";
        } else {
            response.status(404); // 404 Not found
            return "Usuario " + id + " n�o encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":ID"));
        
		Usuario usuario = (Usuario) usuarioDAO.get(id);

        if (usuario != null) {
        	usuario.setNome(request.queryParams("nome"));
        	usuario.setCPF(request.queryParams("CPF"));
        	usuario.setEmail(request.queryParams("email"));
        	usuario.setSenha(request.queryParams("senha"));
        	usuario.setDataDeNascimento(request.queryParams("dataDeNascimento"));
        	usuario.setSexo(request.queryParams("sexo").charAt(0));

        	usuarioDAO.update(usuario);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto n�o encontrado.";
        }

	}

	// COMENTÁRIO DE CODE REVIEW: Inconsistência entre busca e deleção.
	// O método busca usuário por ID mas deleta por NOME (usuario.getNome()).
	// Nomes podem se repetir, causando deleção incorreta. O método UsuarioDAO.delete() espera ID.
	// Sugestão: Alterar para usuarioDAO.delete(id) para manter consistência e precisão.
	// Benefícios: Garante deleção precisa, evita deleções incorretas e mantém consistência na identificação de usuários.
	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":ID"));

        Usuario usuario = (Usuario) usuarioDAO.get(id);

        if (usuario != null) {

            usuarioDAO.delete(usuario.getNome());

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto n�o encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<produtos type=\"array\">");
		for (Usuario usuario : usuarioDAO.get()) {
			returnValue.append("\n<produto>\n" +  
            		"\t<id>" + usuario.getID() + "</id>\n" +
            		"\t<nome>" + usuario.getNome() + "</nome>\n" +
            		"\t<CPF>" + usuario.getCPF() + "</CPF>\n" +
            		"\t<email>" + usuario.getEmail() + "</email>\n" +
            		"\t<senha>" + usuario.getSenha() + "</senha>\n" +
            		"\t<dataDeNascimento>" + usuario.getDataDeNascimento() + "</dataDeNascimento>\n" +
            		"\t<sexo>" + usuario.getSexo() + "</sexo>\n" +
            		"</produto>\n");
		}
		returnValue.append("</produtos>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}