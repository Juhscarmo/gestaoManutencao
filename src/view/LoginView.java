package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginView extends JFrame {

	private JTextField txtUser;
	private JPasswordField txtSenha;
	private JLabel msg;
	private JButton buttonLogin;
	private JLabel labelCadastrar;
	
	public LoginView() {
		
		//Configuração da janela
		setTitle("Tela de Login");
		setSize(900, 800); //tamanho da janela
		setLocationRelativeTo(null); //centralizando a janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Painel de fundo
		JPanel painelFundo = new JPanel();
		painelFundo.setBackground(Color.decode("#4F4F4F"));
		painelFundo.setLayout(new GridBagLayout());
		
		//Painel central
		JPanel painelCentral = new JPanel();
		painelCentral.setBackground(Color.decode("#363636"));
		painelCentral.setLayout(new GridBagLayout());
		painelCentral.setPreferredSize(new Dimension(400, 300)); //definindo tamanho
		
		GridBagConstraints g = new GridBagConstraints(); //especificando restrições de layout
		g.insets = new Insets(10, 10, 10, 10); //espaço de 10 pixels de margem ao redor dos componentes
		g.fill = GridBagConstraints.HORIZONTAL; //componentes preenchem horizontalmente o espaço disponível
		
		//Título 'Login'
		JLabel titulo = new JLabel("Login", SwingConstants.CENTER);
		titulo.setForeground(Color.WHITE);
		titulo.setFont(new Font("Arial", Font.BOLD, 20));
		//definindo posição
		g.gridx = 0;
		g.gridy = 0; 
		g.gridwidth = 2; 
		painelCentral.add(titulo, g);
		
		//Usuário
		JLabel user = new JLabel("Usuário:");
		user.setForeground(Color.WHITE);
		g.gridx = 0; 
		g.gridy = 1; 
		g.gridwidth = 1; 
		painelCentral.add(user, g);
		
		//Campo para preencher usuário
		txtUser = new JTextField(15);
		txtUser.setForeground(Color.WHITE); //cor do texto
		txtUser.setBackground(Color.decode("#363636")); //cor de fundo
		txtUser.setCaretColor(Color.WHITE); //cor do cursor
		//Removendo borda padrão e adicionando inferior
		txtUser.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		g.gridx = 1; 
		g.gridy = 1; 
		painelCentral.add(txtUser, g);
		
		//Senha
		JLabel senha = new JLabel("Senha:");
		senha.setForeground(Color.WHITE);
		g.gridx = 0; 
		g.gridy = 2; 
		painelCentral.add(senha, g);
		
		//Campo para preencher senha
		txtSenha = new JPasswordField(15);
		txtSenha.setForeground(Color.WHITE); //cor do texto
		txtSenha.setBackground(Color.decode("#363636")); //cor de fundo
		txtSenha.setCaretColor(Color.WHITE); //cor do cursor
		//Removendo borda padrão e adicionando inferior
		txtSenha.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		g.gridx = 1; 
		g.gridy = 2; 
		painelCentral.add(txtSenha, g);
		
		//Botão de login
		buttonLogin = new JButton("Login");
		buttonLogin.setFocusPainted(false); //retirando a borda de foco padrão
		buttonLogin.setBackground(Color.decode("#A52A2A")); //definindo cor do botão
		buttonLogin.setForeground(Color.WHITE); //definindo cor do texto
		buttonLogin.setBorder(BorderFactory.createLineBorder(Color.decode("#A52A2A"))); //definindo cor da borda
		buttonLogin.setPreferredSize(new Dimension(100, 27)); //definindo tamanho
		buttonLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); //mudando o cursor para sinalizar que é clicável
		g.gridx = 0;
		g.gridy = 3; 
		g.gridwidth = 2; 
		painelCentral.add(buttonLogin, g);
		
		//Opção de cadastro
		labelCadastrar = new JLabel("<html><u><i>Não possui uma conta? Cadastre-se</i></u></html>", SwingConstants.CENTER);
		labelCadastrar.setForeground(Color.WHITE);
		labelCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); //mudando o cursor para sinalizar que é clicável
		g.gridx = 0;
		g.gridy = 4;
		g.gridwidth = 2;
		painelCentral.add(labelCadastrar, g);
		
		//Mensagem de login
		msg = new JLabel("", SwingConstants.CENTER);
		msg.setForeground(Color.WHITE);
		g.gridy = 5;
		painelCentral.add(msg, g);
		
		//Adicionando painel central ao painel de fundo
		painelFundo.add(painelCentral);
		//Adicionando painel de fundo ao frame
		add(painelFundo);
		//Tornando a tela visível
		setVisible(true);
	}
	
	//Getters
	public JTextField getTxtUser() {
		return txtUser;
	}
	public JPasswordField getTxtSenha() {
		return txtSenha;
	}
	public JLabel getMsg() {
		return msg;
	}
	public JButton getButtonLogin() {
		return buttonLogin;
	}
	public JLabel getLabelCadastrar() {
		return labelCadastrar;
	}
	
}
