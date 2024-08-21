package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import factory.ConnectionFactory;
import model.Usuario;

public class UsuarioDAO {
	
	//Salvando usuário
	public void save(Usuario usuario) {
		
		String sql = "INSERT INTO pessoa (nomeUser, usuario, senha)" + " VALUES(?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			//Cria uma conexão com o banco de dados
			conn = ConnectionFactory.createConnectionToMySQL();

			//Cria um PreparedStatment, classe usada para executar a query
			pstm = conn.prepareStatement(sql);

			//Adiciona o valor do primeiro parâmetro da sql
			pstm.setString(1, usuario.getNome());
			//Adiciona o valor do segundo parâmetro da sql
			pstm.setString(2, usuario.getLogin());
			//Adiciona o valor do terceiro parâmetro da sql
			pstm.setString(3, usuario.getSenha());

			//Executa a sql para inserção dos dados
			pstm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//Fecha as conexões
			try{
				if(pstm != null){
					pstm.close();
				}
				if(conn != null){
					conn.close();
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	//Validando login
	public boolean validarLogin(String usuario, String senha) throws Exception {
		
		String sql = "SELECT * FROM pessoa WHERE usuario = ? AND senha = ?";
		try (Connection conn = ConnectionFactory.createConnectionToMySQL();
	             PreparedStatement pstm = conn.prepareStatement(sql)) {
	             
	            pstm.setString(1, usuario);
	            pstm.setString(2, senha);
	            
	            try (ResultSet rs = pstm.executeQuery()) {
	                return rs.next(); // Retorna true se encontrou um usuário com esse login e senha
	            }
	        }
	    }
	
	//Verificando logins iguais
	public boolean loginIgual(String login) throws Exception {
	    String sql = "SELECT * FROM pessoa WHERE usuario = ?";
	    try (Connection conn = ConnectionFactory.createConnectionToMySQL();
	         PreparedStatement pstm = conn.prepareStatement(sql)) {
	        
	        pstm.setString(1, login);
	        
	        try (ResultSet rs = pstm.executeQuery()) {
	            return rs.next(); //se encontrou login igual
	        }
	    }
	}
	
}
