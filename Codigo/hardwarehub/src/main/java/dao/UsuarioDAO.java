package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UsuarioDAO extends DAO {
	
	public UsuarioDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	public boolean insert(Usuario usuario) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO usuario (u_ID , u_nome, u_CPF, u_email, u_senha, u_dataDeNascimento, u_sexo) "
				       + "VALUES ('" + usuario.getID() + "', '" +usuario.getNome() + "', '" + usuario.getCPF() + "', '" + usuario.getEmail() + "', '" + usuario.getSenha() + "', '" + usuario.getDataDeNascimento() + "', '" + usuario.getSexo() + "');";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Usuario get(int codigo) {
		Usuario usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario WHERE u_id=" + codigo;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 usuario = new Usuario(rs.getInt("u_Id"), rs.getString("u_nome"), rs.getString("u_CPF"), rs.getString("u_email"), rs.getString("u_senha"), rs.getString("u_dataDeNascimento"), rs.getString("u_sexo").charAt(0));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}
	
	
	public List<Usuario> get() {
		return get("");
	}

	
	public List<Usuario> getOrderByNome() {
		return get("u_nome");		
	}
	
	
	public List<Usuario> getOrderByCPF() {
		return get("u_CPF");		
	}
	
	
	public List<Usuario> getOrderByEmail() {
		return get("u_email");		
	}
	
	
	private List<Usuario> get(String orderBy) {	
	
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Usuario u = new Usuario(rs.getInt("u_Id"), rs.getString("u_nome"), rs.getString("u_CPF"), rs.getString("u_email"), rs.getString("u_senha"), rs.getString("u_dataDeNascimento"), rs.getString("u_sexo").charAt(0));
	            usuarios.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}


	public List<Usuario> getSexoMasculino() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario WHERE u_sexo LIKE 'M'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Usuario u = new Usuario(rs.getInt("u_Id"), rs.getString("u_nome"), rs.getString("u_CPF"), rs.getString("u_email"), rs.getString("u_senha"), rs.getString("u_dataDeNascimento"), rs.getString("u_sexo").charAt(0));
	            usuarios.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	
	public boolean update(Usuario usuario) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE usuario SET login = '" + usuario.getEmail() + "', CPF = '"  
				       + usuario.getCPF() + "', sexo = '" + usuario.getSexo() + "'"
					   + " WHERE u_nome = " + usuario.getNome();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int ID) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM usuario WHERE u_Id = " + ID;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean autenticar(String nome, String senha) {
		boolean resp = false;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario WHERE u_nome LIKE '" + nome + "' AND u_senha LIKE '" + senha  + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}	
	
	public int getMaxID() {
		try {
			Statement st = conexao.createStatement();
			String sql = "SELECT MAX(u_id) AS maxID FROM usuario";
			ResultSet rs = st.executeQuery(sql);
			int maxID = rs.getInt("maxID");
			return maxID;
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return 0;
		}
	}
}