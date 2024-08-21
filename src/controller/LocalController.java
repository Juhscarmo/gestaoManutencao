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
import dao.LocalDAO;
import model.Local;
import view.PrincipalView;

public class LocalController {
	
	//Atributos
	private Local local;
	private PrincipalView principalView;
	
	//Construtores
	public LocalController(Local local, PrincipalView tela) {
		this.local = local;
		this.local = new Local();
		this.principalView = tela;
		initView();
	}
	
	//Métodos
	private void initView() {
		principalView.setVisible(true);
		listar();
	}
	
	public void initController() {
		
		principalView.getButtonCadastrarLocal().addActionListener(e-> cadastrar());
		principalView.getButtonCadastrarLocal().addMouseListener(new MouseAdapter() {
			//Ao mouse entrar na caixa no botão
			public void mouseEntered(MouseEvent evt) {
				principalView.getButtonCadastrarLocal().setBackground(Color.decode("#A52A2A").darker()); //mudando a cor
			}
			//Ao mouse sair da caixa do botão
			public void mouseExited(MouseEvent evt) {
				principalView.getButtonCadastrarLocal().setBackground(Color.decode("#A52A2A")); //mudando a cor
			}
		});
		
	}
	
	private void cadastrar() {
		
		String nomeLocal = principalView.getCampoLocal().getText(); //nome do local
	    String equipeLocal = principalView.getCampoEquipe().getText(); //equipe do local
	    
		//Verificando se não há campos vazios
		if (nomeLocal.isEmpty() || equipeLocal.isEmpty()) {
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
		
		//Realizando cadastro
		local.setLocal(nomeLocal); 
	    local.setEquipe(equipeLocal);
		LocalDAO dao = new LocalDAO();
		try {
			dao.save(local);
		} catch (Exception ex) {
			Logger.getLogger(VeiculoController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		limpar();
		listar();
	}
	
	private void excluir(Local local) {
		
		LocalDAO dao = new LocalDAO();
		try {
			dao.remove(local.getLocal());
		} catch (Exception ex) {
			Logger.getLogger(VeiculoController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	private void listar() {
		
		LocalDAO dao = new LocalDAO();
		List<Local> locais = null;
		try {
			locais=dao.getLocais();
		} catch (Exception ex) {
			Logger.getLogger(VeiculoController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		//Limpando painel antes de adicionar novos locais e equipes
		principalView.getListagemLocais().removeAll();
		
		for(Local l : locais) {
			//Criando painel personalizado para cada local e equipe
			JPanel painelPersonalizado = principalView.configLocalCadastrado(
					l.getLocal(), l.getEquipe());
			//Adicionando ações do mouse no local cadastrado
			painelPersonalizado.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
	                painelPersonalizado.setBackground(Color.LIGHT_GRAY); //mudando cor
	            }
	            public void mouseExited(MouseEvent e) {
	                painelPersonalizado.setBackground(Color.WHITE); //mudando cor
	            }
			});
			
			//Recuperando botão de excluir do painel
			JButton buttonExcluirLocal = (JButton) painelPersonalizado.getComponent(1); //botão no segundo componente do painel
			//Ações do botão de excluir
			buttonExcluirLocal.addActionListener(e-> {
				excluir(l); 
				listar(); //atualiza após exclusão
			});
			buttonExcluirLocal.addMouseListener(new MouseAdapter() {
				//Ao mouse entrar na caixa no botão
				public void mouseEntered(MouseEvent evt) {
					buttonExcluirLocal.setBackground(Color.decode("#A52A2A").darker()); //mudando cor
				}
				//Ao mouse sair da caixa do botão
				public void mouseExited(MouseEvent evt) {
					buttonExcluirLocal.setBackground(Color.decode("#A52A2A")); //mudando cor
				}
			});
			
			//Adicionando painel a lista
			principalView.getListagemLocais().add(painelPersonalizado);
		}
		
		//Garatindo que as aplicações sejam feitas
		principalView.getListagemLocais().revalidate();
	    principalView.getListagemLocais().repaint();
		
	}
	
	private void limpar() {
		principalView.getCampoLocal().setText("");
		principalView.getCampoEquipe().setText("");	
	}
	
	//Getters e Setters
	public Local getLocal() {
		return local;
	}
	public void setLocal(Local local) {
		this.local = local;
	}
	public PrincipalView getTela() {
		return principalView;
	}
	public void setTela(PrincipalView tela) {
		this.principalView = tela;
	}
	
}
