package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import factory.ConnectionFactory;
import model.Local;
import model.Manutencao;
import model.Usuario;
import model.Veiculo;

public class ManutencaoDAO {
	
	//Salvando dados da manutenção
	public void save(Manutencao manutencao) {
		
		String sql = "INSERT INTO manutencao (dataManutencao, horaManutencao, descManutencao, status, usuario, nomeLocal, placaVeiculo)" + 
					" VALUES(?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			//Cria uma conexão com o banco de dados
			conn = ConnectionFactory.createConnectionToMySQL();

			//Cria um PreparedStatment, classe usada para executar a query
			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			//Adiciona o valor do primeiro parâmetro da sql
			pstm.setString(1, manutencao.getDataManutencao());
			//Adiciona o valor do segundo parâmetro da sql
			pstm.setString(2, manutencao.getHoraManutencao());
			//Adiciona o valor do terceiro parâmetro da sql
			pstm.setString(3, manutencao.getDescricao());
			//Adiciona o valor do quarto parâmetro da sql
			pstm.setBoolean(4, manutencao.isStatus());
			//Adiciona o valor do quinto parâmetro da sql
			pstm.setString(5, manutencao.getUsuario().getLogin());
			//Adiciona o valor do sexto parâmetro da sql
			pstm.setString(6, manutencao.getLocal().getLocal());
			//Adiciona o valor do sétimo parâmetro da sql
			pstm.setString(7, manutencao.getVeiculo().getPlaca());
			//Executa a sql para inserção dos dados
			pstm.execute();
			
			//Captura o ID gerado automaticamente
			try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                manutencao.setIdManutencao(generatedKeys.getInt(1));
	            }
	        }
			
			if (pstm.getUpdateCount()>0)
				JOptionPane.showMessageDialog(null,"Salvo com sucesso!");
			else
				JOptionPane.showMessageDialog(null,"Não foi possível inserir!!");
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
	
	//Atualizando os dados
	public void update(int id, boolean novoStatus) {
		
		//O usuário poderá apenas mudar o status da manutenção
		String sql = "UPDATE manutencao SET status = ? WHERE idManutencao = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			//Cria uma conexão com o banco
			conn = ConnectionFactory.createConnectionToMySQL();

			//Cria um PreparedStatment, classe usada para executar a query
			pstm = conn.prepareStatement(sql);

			//Adiciona o valor do primeiro parâmetro da sql
			pstm.setBoolean(1, novoStatus);
			
			//Adiciona o valor do segundo parâmetro da sql
			pstm.setInt(2, id);
			
			//Executa a sql para inserção dos dados
			pstm.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//Fecha as conexÃµes
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
	public List<Manutencao> getManutencoes(){
		
		String sql = "SELECT * FROM manutencao ORDER BY dataManutencao";
		List<Manutencao> manutencoes = new ArrayList<Manutencao>();
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
				Manutencao manutencao = new Manutencao();
				
				//Recupera a data do banco e atribui ela ao objeto
				manutencao.setDataManutencao(rset.getString("dataManutencao"));
				
				//Recupera a hora do banco e atribui ela ao objeto
				manutencao.setHoraManutencao(rset.getString("horaManutencao"));
				
				//Recupera a descrição do banco e atribui ela ao objeto
				manutencao.setDescricao(rset.getString("descManutencao"));
				
				//Recupera o status do banco e atribui ele ao objeto
				manutencao.setStatus(rset.getBoolean("status"));

				//Adiciona a manutenção recuperada na lista de manutenção
				manutencoes.add(manutencao);
				
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
		return manutencoes;
	}
	
	//Manutenções com filtro
	public List<Manutencao> getManutencoesFiltradas(String dataManutencao, Local local, Veiculo veiculo, String status){
		
		StringBuilder sql = new StringBuilder("SELECT idManutencao, dataManutencao, horaManutencao, descManutencao, status, nomeLocal, placaVeiculo, usuario FROM manutencao WHERE 1=1");
		
		if (dataManutencao != null && !dataManutencao.trim().isEmpty()) {
            sql.append(" AND dataManutencao = ?");
        }
        if (local != null && !local.getLocal().equals("TODOS")) {
            sql.append(" AND nomeLocal = ?");
        }
        if (veiculo != null && !veiculo.getPlaca().equals("TODOS")) {
            sql.append(" AND placaVeiculo = ?");
        }
        if (status != null && !status.equals("TODOS")) {
            sql.append(" AND status = ?");
        }
        sql.append(" ORDER BY dataManutencao");

        List<Manutencao> manutencoes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        
        try {
            conn = ConnectionFactory.createConnectionToMySQL();
            pstm = conn.prepareStatement(sql.toString());

            int paramIndex = 1;

            if (dataManutencao != null && !dataManutencao.trim().isEmpty()) {
                pstm.setString(paramIndex++, dataManutencao);
            }
            if (local != null && !local.getLocal().equals("TODOS")) {
                pstm.setString(paramIndex++, local.getLocal());
            }
            if (veiculo != null && !veiculo.getPlaca().equals("TODOS")) {
                pstm.setString(paramIndex++, veiculo.getPlaca());
            }
            if (status != null && !status.equals("TODOS")) {
                pstm.setBoolean(paramIndex++, status.equals("CONCLUIDO"));
            }

            rset = pstm.executeQuery();

            while (rset.next()) {
                Manutencao manutencao = new Manutencao();
                manutencao.setIdManutencao(rset.getInt("idManutencao"));
                manutencao.setDataManutencao(rset.getString("dataManutencao"));
                manutencao.setHoraManutencao(rset.getString("horaManutencao"));
                manutencao.setDescricao(rset.getString("descManutencao"));
                manutencao.setStatus(rset.getBoolean("status"));
                Local l = new Local();
                l.setLocal(rset.getString("nomeLocal"));
                manutencao.setLocal(l);
                Veiculo v = new Veiculo();
                v.setPlaca(rset.getString("placaVeiculo"));
                manutencao.setVeiculo(v);
                Usuario u = new Usuario();
                u.setLogin(rset.getString("usuario"));
                manutencao.setUsuario(u);
                manutencoes.add(manutencao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset != null) rset.close();
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return manutencoes;
	}
	
}
