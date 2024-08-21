package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import factory.ConnectionFactory;
import model.Local;

public class LocalDAO {
	
	//Salvando dados do local
	public void save(Local local) {
		
		String sql = "INSERT INTO local (nomeLocal, equipeLocal)" + " VALUES(?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			//Cria uma conexão com o banco de dados
			conn = ConnectionFactory.createConnectionToMySQL();

			//Cria um PreparedStatment, classe usada para executar a query
			pstm = conn.prepareStatement(sql);

			//Adiciona o valor do primeiro parâmetro da sql
			pstm.setString(1, local.getLocal());
			//Adiciona o valor do segundo parâmetro da sql
			pstm.setString(2, local.getEquipe());

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
	
	//Removendo os dados pelo nome do local
	public void remove(String nomeLocal) {
		
		String sql = "DELETE FROM local WHERE nomeLocal = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, nomeLocal);
			pstm.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
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
	
	//Listando os dados
	public List<Local> getLocais(){
		
		String sql = "SELECT * FROM local ORDER BY nomeLocal";
		List<Local> locais = new ArrayList<Local>();
		Connection conn = null;
		PreparedStatement pstm = null;
		
		//Classe que vai recuperar os dados do banco de dados
		ResultSet rset = null;

		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = conn.prepareStatement(sql);
			rset = pstm.executeQuery();

			//Enquanto existir dados no banco de dados, faça:
			while(rset.next()){
				Local local = new Local();
				
				//Recupera o local do banco e atribui ela ao objeto
				local.setLocal(rset.getString("nomeLocal"));

				//Recupera a equipe do banco e atribui ele ao objeto
				local.setEquipe(rset.getString("equipeLocal"));

				//Adiciona o local recuperado na lista de locais
				locais.add(local);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try{
				if(rset != null){
					rset.close();
				}

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
		return locais;
	}
	
}
