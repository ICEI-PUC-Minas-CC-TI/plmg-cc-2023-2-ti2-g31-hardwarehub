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
            return "Usuario " + id + " não encontrado.";
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
            return "Produto não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":ID"));

        Usuario usuario = (Usuario) usuarioDAO.get(id);

        if (usuario != null) {

            usuarioDAO.delete(usuario.getNome());

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
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