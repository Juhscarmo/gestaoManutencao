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

public class CadastroView extends JFrame {

    private JTextField txtNome;
    private JTextField txtUser;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmaSenha;
    private JLabel msg;
    private JButton buttonCadastrar;

    public CadastroView() {

        // Configuração da janela
        setTitle("Tela de Cadastro");
        setSize(900, 800); // tamanho da janela
        setLocationRelativeTo(null); // centralizando a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel de fundo
        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(Color.decode("#4F4F4F"));
        painelFundo.setLayout(new GridBagLayout());

        // Painel central
        JPanel painelCentral = new JPanel();
        painelCentral.setBackground(Color.decode("#363636"));
        painelCentral.setLayout(new GridBagLayout());
        painelCentral.setPreferredSize(new Dimension(400, 400)); // definindo tamanho

        GridBagConstraints g = new GridBagConstraints(); // especificando restrições de layout
        g.insets = new Insets(10, 10, 10, 10); // espaço de 10 pixels de margem ao redor dos componentes
        g.fill = GridBagConstraints.HORIZONTAL; // componentes preenchem horizontalmente o espaço disponível

        // Título 'Cadastro'
        JLabel titulo = new JLabel("Cadastro", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        // definindo posição
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        painelCentral.add(titulo, g);

        // Campo de nome
        JLabel nome = new JLabel("Nome:");
        nome.setForeground(Color.WHITE);
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 1;
        painelCentral.add(nome, g);

        txtNome = new JTextField(15);
        txtNome.setForeground(Color.WHITE); // cor do texto
        txtNome.setBackground(Color.decode("#363636")); // cor de fundo
        txtNome.setCaretColor(Color.WHITE); // cor do cursor
        // Removendo borda padrão e adicionando inferior
        txtNome.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        g.gridx = 1;
        g.gridy = 1;
        painelCentral.add(txtNome, g);

        // Campo de usuário
        JLabel user = new JLabel("Usuário:");
        user.setForeground(Color.WHITE);
        g.gridx = 0;
        g.gridy = 2;
        painelCentral.add(user, g);

        txtUser = new JTextField(15);
        txtUser.setForeground(Color.WHITE); // cor do texto
        txtUser.setBackground(Color.decode("#363636")); // cor de fundo
        txtUser.setCaretColor(Color.WHITE); // cor do cursor
        // Removendo borda padrão e adicionando inferior
        txtUser.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        g.gridx = 1;
        g.gridy = 2;
        painelCentral.add(txtUser, g);

        // Campo de senha
        JLabel senha = new JLabel("Senha:");
        senha.setForeground(Color.WHITE);
        g.gridx = 0;
        g.gridy = 3;
        painelCentral.add(senha, g);

        txtSenha = new JPasswordField(15);
        txtSenha.setForeground(Color.WHITE); // cor do texto
        txtSenha.setBackground(Color.decode("#363636")); // cor de fundo
        txtSenha.setCaretColor(Color.WHITE); // cor do cursor
        // Removendo borda padrão e adicionando inferior
        txtSenha.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        g.gridx = 1;
        g.gridy = 3;
        painelCentral.add(txtSenha, g);

        // Campo de confirmação de senha
        JLabel confirmaSenha = new JLabel("Confirme Senha:");
        confirmaSenha.setForeground(Color.WHITE);
        g.gridx = 0;
        g.gridy = 4;
        painelCentral.add(confirmaSenha, g);

        txtConfirmaSenha = new JPasswordField(15);
        txtConfirmaSenha.setForeground(Color.WHITE); // cor do texto
        txtConfirmaSenha.setBackground(Color.decode("#363636")); // cor de fundo
        txtConfirmaSenha.setCaretColor(Color.WHITE); // cor do cursor
        // Removendo borda padrão e adicionando inferior
        txtConfirmaSenha.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        g.gridx = 1;
        g.gridy = 4;
        painelCentral.add(txtConfirmaSenha, g);

        // Botão de cadastro
        buttonCadastrar = new JButton("Cadastrar-se");
        buttonCadastrar.setFocusPainted(false); // retirando a borda de foco padrão
        buttonCadastrar.setBackground(Color.decode("#A52A2A")); // definindo cor do botão
        buttonCadastrar.setForeground(Color.WHITE); // definindo cor do texto
        buttonCadastrar.setBorder(BorderFactory.createLineBorder(Color.decode("#A52A2A"))); // definindo cor da borda
        buttonCadastrar.setPreferredSize(new Dimension(100, 27)); // definindo tamanho
        buttonCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); // mudando o cursor para sinalizar que é clicável
        g.gridx = 0;
        g.gridy = 5;
        g.gridwidth = 2;
        painelCentral.add(buttonCadastrar, g);

        // Mensagem de cadastro
        msg = new JLabel("", SwingConstants.CENTER);
        msg.setForeground(Color.WHITE);
        g.gridy = 6;
        painelCentral.add(msg, g);

        // Adicionando painel central ao painel de fundo
        painelFundo.add(painelCentral);
        // Adicionando painel de fundo ao frame
        add(painelFundo);
       //Tornando a tela visível
      	setVisible(true);
       
    }
    
    //Getters
    public JTextField getTxtNome() {
    	return txtNome;
    }
    public JTextField getTxtUser() {
    	return txtUser;
    }
    public JPasswordField getTxtSenha() {
    	return txtSenha;
    }
    public JPasswordField getTxtConfirmaSenha() {
    	return txtConfirmaSenha;
    }
    public JLabel getMsg() {
    	return msg;
    }
    public JButton getButtonCadastrar() {
    	return buttonCadastrar;
    }

}
