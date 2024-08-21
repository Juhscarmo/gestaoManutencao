package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import dao.VeiculoDAO;
import model.Veiculo;
import view.PrincipalView;

public class VeiculoController {
	
	//Atributos
	private Veiculo veiculo;
	private PrincipalView principalView;
	
	//Construtores
	public VeiculoController(Veiculo veiculo, PrincipalView tela) {
		this.veiculo = veiculo;
		this.veiculo = new Veiculo();
		this.principalView = tela;
		initView();
	}
	
	//Métodos
	private void initView() {
		principalView.setVisible(true);
		listar();
	}
	
	public void initController() {
		
		principalView.getButtonCadastrarVeiculo().addActionListener(e-> cadastrar());
		principalView.getButtonCadastrarVeiculo().addMouseListener(new MouseAdapter() {
			//Ao mouse entrar na caixa do botão cadastrar
	        public void mouseEntered(MouseEvent evt) {
	        	principalView.getButtonCadastrarVeiculo().setBackground(Color.decode("#A52A2A").darker()); //mudando a cor
	        }
	       //Ao mouse sair da caixa do botão cadastrar
	        public void mouseExited(MouseEvent evt) {
	            principalView.getButtonCadastrarVeiculo().setBackground(Color.decode("#A52A2A")); //mudando a cor
	        }
		});
		
	}
	
	private void cadastrar() {
		
		String placaVeiculo = principalView.getCampoPlaca().getText(); //placa do veículo
		String tipoVeiculo = principalView.getCampoTipo().getText(); //tipo do veículo
		
		//Verificando se há campos vazios
		if (placaVeiculo.isEmpty() || tipoVeiculo.isEmpty()) {
			JDialog dialog = new JDialog();
	        dialog.setAlwaysOnTop(true);
	        dialog.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
	        JOptionPane.showMessageDialog(dialog,
	                "Você deve preencher todos os campos!",
	                "Erro de Cadastro",
	                JOptionPane.ERROR_MESSAGE);
	        dialog.dispose();
	        return; //não realiza o cadastro
	    }
		
		//Realizando o cadastro
		veiculo.setPlaca(placaVeiculo); 
		veiculo.setTipoVeiculo(tipoVeiculo);
		VeiculoDAO dao = new VeiculoDAO();
		try {
			dao.save(veiculo);
		} catch (Exception ex) {
			Logger.getLogger(VeiculoController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		limpar();
		listar();
	}
	
	private void excluir(Veiculo veiculo) {
		
		VeiculoDAO dao = new VeiculoDAO();
		try {
			dao.remove(veiculo.getPlaca());
		} catch (Exception ex) {
			Logger.getLogger(VeiculoController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	private void listar() {
		
		VeiculoDAO dao = new VeiculoDAO();
		List<Veiculo> veiculos = null;
		try {
			veiculos=dao.getVeiculos();
		} catch (Exception ex) {
			Logger.getLogger(VeiculoController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		//Limpando painel antes de adicionar novo veiculo
		principalView.getListagemVeiculos().removeAll();
		
		for(Veiculo l : veiculos) {
			//Criando painel personalizado para cada veiculo
			JPanel painelPersonalizado = principalView.configVeiculoCadastrado(
					l.getPlaca(), l.getTipoVeiculo());
			//Adicionando ações do mouse no veiculo cadastrado
			painelPersonalizado.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
	                painelPersonalizado.setBackground(Color.LIGHT_GRAY); //mudando cor
	            }
	            public void mouseExited(MouseEvent e) {
	                painelPersonalizado.setBackground(Color.WHITE); //mudando cor
	            }
			});
			
			//Recuperando botão de excluir do painel
			JButton buttonExcluirVeiculo = (JButton) painelPersonalizado.getComponent(1); //botão no segundo componente do painel
			//Ações do botão de excluir
			buttonExcluirVeiculo.addActionListener(e-> {
				excluir(l);
				listar(); //atualiza após exclusão
			});
			buttonExcluirVeiculo.addMouseListener(new MouseAdapter() {
				//Ao mouse entrar na caixa no botão
				public void mouseEntered(MouseEvent evt) {
					buttonExcluirVeiculo.setBackground(Color.decode("#A52A2A").darker()); //mudando cor
				}
				//Ao mouse sair da caixa do botão
				public void mouseExited(MouseEvent evt) {
					buttonExcluirVeiculo.setBackground(Color.decode("#A52A2A")); //mudando cor
				}
			});
			
			//Adicionando painel a lista
			principalView.getListagemVeiculos().add(painelPersonalizado);
		}
		
		//Garantindo que as aplicações sejam feitas
		principalView.getListagemVeiculos().revalidate();
		principalView.getListagemVeiculos().repaint();
		
	}
	
	private void limpar() {
		principalView.getCampoPlaca().setText("");
		principalView.getCampoTipo().setText("");
	}
	
	//Getters e Setters
	public Veiculo getVeiculo() {
		return veiculo;
	}
	
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	public PrincipalView getTela() {
		return principalView;
	}
	
	public void setTela(PrincipalView tela) {
		this.principalView = tela;
	}

}
