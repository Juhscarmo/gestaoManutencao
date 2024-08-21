package dao;

import model.Veiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import factory.ConnectionFactory;


public class VeiculoDAO {
	
	//Salvando os dados do veiculo
	public void save(Veiculo veiculo) {
		
		String sql = "INSERT INTO veiculo (placaVeiculo, tipoVeiculo)" + " VALUES(?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			//Cria uma conexão com o banco de dados
			conn = ConnectionFactory.createConnectionToMySQL();

			//Cria um PreparedStatment, classe usada para executar a query
			pstm = conn.prepareStatement(sql);

			//Adiciona o valor do primeiro parâmetro da sql
			pstm.setString(1, veiculo.getPlaca());
			//Adiciona o valor do segundo parâmetro da sql
			pstm.setString(2, veiculo.getTipoVeiculo());

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
	
	//Removendo os dados pela placa
	public void remove(String placaVeiculo) {
		
		String sql = "DELETE FROM veiculo WHERE placaVeiculo = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, placaVeiculo);
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
	public List<Veiculo> getVeiculos(){
		
		String sql = "SELECT * FROM veiculo ORDER BY tipoVeiculo";
		List<Veiculo> veiculos = new ArrayList<Veiculo>();
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
				Veiculo veiculo = new Veiculo();
				
				//Recupera a placa do banco e atribui ela ao objeto
				veiculo.setPlaca(rset.getString("placaVeiculo"));

				//Recupera o tipo do banco e atribui ele ao objeto
				veiculo.setTipoVeiculo(rset.getString("tipoVeiculo"));

				//Adiciona o veiculo recuperado na lista de veiculos
				veiculos.add(veiculo);
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
		return veiculos;
	}
	
}
