package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import model.Local;
import model.Usuario;
import model.UsuarioLogado;
import model.Veiculo;

public class PrincipalView extends JFrame {

	//Atributos
	private JButton buttonSelecionado; //campo para manter o botão atualmente selecionado
	private ArrayList<JButton> buttonLista;
	//Componentes do veiculo
	private JButton buttonCadastrarVeiculo;
	private JButton buttonExcluirVeiculo;
	private JTextField campoPlaca;
	private JTextField campoTipo;
	private JPanel veiculoCadastrado;
	private JPanel listagemVeiculos;
	//Componentes do local
	private JButton buttonCadastrarLocal;
	private JTextField campoLocal;
	private JTextField campoEquipe;
	private JButton buttonExcluirLocal;
	private JPanel localCadastrado;
	private JPanel listagemLocais;
	//Componentes da manutenção/relatório
	private JButton buttonSalvarManutencao;
	private JTextArea campoDescricao;
	private JComboBox<Local> comboBoxLocalManutencao;
	private JComboBox<Veiculo> comboBoxVeiculoManutencao;
	private JComboBox<Local> comboBoxLocalRelatorio;
	private JComboBox<Veiculo> comboBoxVeiculoRelatorio;
	private JComboBox<String> comboBoxStatus;
	private JButton buttonConsultar;
	private JTextField campoData;
	private JButton buttonManutencao;
	private JButton buttonRelatorio;
	
	public PrincipalView() {
		
		//Criando frame principal
		JFrame frame = new JFrame("Tela Principal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.getContentPane().setBackground(Color.decode("#4F4F4F"));
		
		//Criando painel lateral esquerdo (menu)
		JPanel menu = new JPanel();
		menu.setLayout(new GridBagLayout());
		menu.setBackground(Color.decode("#363636"));
		menu.setPreferredSize(new Dimension(300, frame.getHeight()));
		GridBagConstraints g = new GridBagConstraints(); //especificando restrições de layout
		g.fill = GridBagConstraints.HORIZONTAL; //componentes preenchem horizontalmente o espaço disponível
		g.insets = new Insets(10, 10, 10, 10); //espaço de 10 pixels de margem ao redor dos componentes
		g.gridx = 0;
		
		//Criando barra superior do painel lateral
		JPanel barraSuperior = new JPanel();
		barraSuperior.setLayout(new GridBagLayout());
		barraSuperior.setBackground(Color.decode("#A52A2A"));
		barraSuperior.setPreferredSize(new Dimension(300, 100));
		
		//Adicionando ícone a barra superior
		ImageIcon icon = new ImageIcon("C:\\Users\\jusca\\Documents\\Imagens projeto\\icon_Barra.png"); //carrega a imagem
		//Redimensinando a imagem
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImage); //criando ImageIcon a partir da imagem redimensionada
		JLabel icone = new JLabel(newIcon); //criando JLabel com a imagem redimensionada
		g.gridx = 0;
		g.gridy = 1;
		barraSuperior.add(icone, g);
		
		//Adicionando mensagem com o login do user na barra superior
		UsuarioLogado usuarioLogado = UsuarioLogado.getInstance();
		Usuario usuario = usuarioLogado.getUsuario();
		JLabel nomeUser = new JLabel("Bem vindo(a), " + usuario.getLogin());
		nomeUser.setForeground(Color.WHITE);
		nomeUser.setFont(new Font("Arial", Font.BOLD, 15));
		nomeUser.setBackground(Color.decode("#A52A2A"));
		g.gridy = 1;
		g.gridx = 1;
		barraSuperior.add(nomeUser, g);
		
		//Adicionando a barra superior ao menu
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 1;
		g.insets = new Insets(0, 0, 10, 0); //criando espaço em torno do componente (inferior)
		menu.add(barraSuperior, g);
		
		//Criando espaço entre a barra superior e o menu
		g.gridy++;
		menu.add(Box.createRigidArea(new Dimension(0, 20)), g); //adiciona um espaço rígido no menu (linha 1, coluna 0)
		
		//Inicializando a lista de botões
		buttonLista = new ArrayList<>();
		
		//Botões do menu
		//Botão de locais
		JButton buttonLocais = buttonsMenu("Locais");
		g.gridy++;
		menu.add(buttonLocais, g);
		
		//Botão de veículos
		JButton buttonVeiculos = buttonsMenu("Veículos");
		g.gridy++;
		menu.add(buttonVeiculos, g);
		
		//Botão de manutenção
		buttonManutencao = buttonsMenu("Nova Manutenção");
		g.gridy++;
		menu.add(buttonManutencao, g);
		
		//Botão de relatório
		buttonRelatorio = buttonsMenu("Listagem");
		g.gridy++;
		menu.add(buttonRelatorio, g);
		
		//Criando espaço antes do botão de sair
		g.gridy++;
		g.weighty = 1.0; //peso para empurrar o botão de sair para o fundo
		menu.add(Box.createRigidArea(new Dimension(0, 200)), g);
		
		//Botão de sair
		JButton buttonSair = configButtonSair();
		g.gridy++;
		g.weighty = 0.0;
		menu.add(buttonSair, g); //adicionando botão de sair ao menu
		
		//Adicionando o menu ao frame
		g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;
		g.fill = GridBagConstraints.VERTICAL;
		frame.add(menu, g);
		
		//Painel central para exibir conteúdo
		JPanel painelCentral = new JPanel();
		painelCentral.setLayout(new CardLayout());
		painelCentral.setBackground(Color.decode("#4F4F4F"));
		painelCentral.setPreferredSize(new Dimension(frame.getWidth() - 200, frame.getHeight()));
		painelCentral.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.decode("#4F4F4F"))); //adicionando borda
		//Adicionando painel central ao frame
		g.gridx = 1;
		g.gridy = 0;
		g.fill = GridBagConstraints.BOTH;
		g.weightx = 1.0;
		g.weighty = 1.0;
		frame.add(painelCentral, g);
		
		//Criando paineis de conteúdo
		JPanel painel = criarPainelBanner();
		JPanel locais = criarPainelLocais();
		JPanel veiculos = criarPainelVeiculos();
		JPanel manutencao = criarPainelManutencao();
		JPanel relatorio = criarPainelRelatorio();
		
		//Adicionando os paineis de conteúdo ao painel central
		painelCentral.add(painel, "Painel");
		painelCentral.add(locais, "Locais");
		painelCentral.add(veiculos, "Veículos");
		painelCentral.add(manutencao, "Manutenção");
		painelCentral.add(relatorio, "Listagem");
		
		//Ação para os botões de conteúdo
		//Ação para o botão de LOCAIS
		buttonLocais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) painelCentral.getLayout();
				cl.show(painelCentral, "Locais"); //exibe o painel associado a chave
				buttonSelecionado(buttonLocais); //destaca o botão
			}
		});
		
		//Ação para o botão de VEÍCULOS
		buttonVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) painelCentral.getLayout();
				cl.show(painelCentral, "Veículos"); //exibe o painel associado a chave
				buttonSelecionado(buttonVeiculos); //destaca o botão
			}
		});
		
		//Ação para o botão de MANUTENÇÃO
		buttonManutencao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) painelCentral.getLayout();
				cl.show(painelCentral, "Manutenção"); //exibe o painel associado a chave
				buttonSelecionado(buttonManutencao); //destaca o botão
			}
		});
		
		//Ação para o botão de RELATÓRIO
		buttonRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) painelCentral.getLayout();
				cl.show(painelCentral, "Listagem"); //exibe o painel associado a chave
				buttonSelecionado(buttonRelatorio); //destaca o botão
			}
		});
		
		frame.setSize(900, 800); //tamanho da janela
		frame.setLocationRelativeTo(null); //centralizando a janela
		frame.setVisible(true); //tornando a tela visível
		
	}
	
	//MÉTODOS
	//Painel de BANNER
	private JPanel criarPainelBanner() {
		JPanel painelBanner = new JPanel(new GridBagLayout());
	    painelBanner.setBackground(Color.decode("#363636"));
	    GridBagConstraints g = new GridBagConstraints();
	    g.fill = GridBagConstraints.HORIZONTAL;
	    g.insets = new Insets(10, 10, 10, 10);
	    g.gridx = 0;
	    g.gridy = 0;
	    g.gridwidth = 2;
	    
	    //Mensagem 
	    JLabel tituloBanner = new JLabel("<html><i>Sistema de Manutenção</i></html>");
	    tituloBanner.setFont(new Font("Arial", Font.BOLD, 30));
	    tituloBanner.setForeground(Color.DARK_GRAY);
	    painelBanner.add(tituloBanner, g);
	    
	    //Adicionando imagem
	    ImageIcon icon = new ImageIcon("C:\\Users\\jusca\\Documents\\Imagens projeto\\icon_Banner.png");
	    //Redimensionando a imagem
	    Image image = icon.getImage();
		Image newImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImage); //criando ImageIcon a partir da imagem redimensionada
		JLabel icone = new JLabel(newIcon); //criando JLabel com a imagem redimensionada
	    painelBanner.add(icone, g);
	    
	    return painelBanner;
	}

	//Painel de LOCAIS
	private JPanel criarPainelLocais() {
	    JPanel locais = new JPanel(new GridBagLayout());
	    locais.setBackground(Color.decode("#4F4F4F"));
	    GridBagConstraints g = new GridBagConstraints();
	    g.fill = GridBagConstraints.HORIZONTAL; //componentes preenchem horizontalmente
	    g.insets = new Insets(10, 10, 10, 10);
	    g.gridx = 0;
	    g.gridy = 0;
	    g.gridwidth = 2; //colunas ocupadas

	    //Título de cadastro
	    JLabel tituloCadastro = new JLabel("<html><u>Cadastro de Locais</u></html>");
	    tituloCadastro.setFont(new Font("Arial", Font.BOLD, 20));
	    tituloCadastro.setForeground(Color.WHITE);
	    locais.add(tituloCadastro, g);

	    //Local
	    g.gridy++;
	    g.gridwidth = 1;
	    JLabel labelLocal = new JLabel("Local:");
	    labelLocal.setFont(new Font("Arial", Font.BOLD, 16));
	    labelLocal.setForeground(Color.WHITE);
	    locais.add(labelLocal, g);

	    //Campo para preencher novo local
	    g.gridx = 1;
	    campoLocal = new JTextField(20);
	    locais.add(campoLocal, g);
	    
	    //Equipe
	    g.gridx = 0;
	    g.gridy++;
	    JLabel labelEquipe = new JLabel("Equipe:");
	    labelEquipe.setFont(new Font("Arial", Font.BOLD, 16));
	    labelEquipe.setForeground(Color.WHITE);
	    locais.add(labelEquipe, g);

	    //Campo para preencher a equipe responsável
	    g.gridx = 1;
	    campoEquipe = new JTextField(20);
	    locais.add(campoEquipe, g);

	    //Botão para cadastrar
	    g.gridx = 2;
	    g.gridy--;
	    g.gridheight = 2;
	    buttonCadastrarLocal = new JButton("Cadastrar");
	    buttonCadastrarLocal.setFocusPainted(false);
	    buttonCadastrarLocal.setBackground(Color.decode("#A52A2A"));
	    buttonCadastrarLocal.setForeground(Color.WHITE);
	    buttonCadastrarLocal.setPreferredSize(new Dimension(40, 27));
	    buttonCadastrarLocal.setFont(new Font("Arial", Font.BOLD, 13));
	    buttonCadastrarLocal.setBorder(BorderFactory.createLineBorder(Color.decode("#A52A2A")));
	    buttonCadastrarLocal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	    locais.add(buttonCadastrarLocal, g);
	    g.gridheight = 1;

	    //Locais já cadastrados
	    g.gridx = 0;
	    g.gridy += 2;
	    g.gridwidth = 3;
	    JLabel tituloLista = new JLabel("<html><u>Locais já Cadastrados:</u></html>");
	    tituloLista.setFont(new Font("Arial", Font.BOLD, 20));
	    tituloLista.setForeground(Color.WHITE);
	    locais.add(tituloLista, g);

	    //Painel para listar os locais cadastrados
	    g.gridy++;
	    listagemLocais = new JPanel();
	    listagemLocais.setLayout(new BoxLayout(listagemLocais, BoxLayout.Y_AXIS)); //componentes na coluna vertical
	    JScrollPane scrollPane = new JScrollPane(listagemLocais);
	    scrollPane.setPreferredSize(new Dimension(400, 200));
	    locais.add(scrollPane, g);

	    return locais;
	}

	//Personalizando cada LOCAL CADASTRADO
	public JPanel configLocalCadastrado(String local, String equipe) {
	    localCadastrado = new JPanel();
	    localCadastrado.setLayout(new BorderLayout());
	    localCadastrado.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	    localCadastrado.setBackground(Color.WHITE);

	    //Exibindo o nome do local e equipe responsável
	    JLabel labelLocal = new JLabel(local + " - " + equipe);
	    localCadastrado.add(labelLocal, BorderLayout.CENTER);

	    //Botão para deleter o local cadastrado
	    buttonExcluirLocal = new JButton("Excluir");
	    buttonExcluirLocal.setFocusPainted(false);
	    buttonExcluirLocal.setBackground(Color.decode("#A52A2A"));
	    buttonExcluirLocal.setForeground(Color.WHITE);
	    buttonExcluirLocal.setPreferredSize(new Dimension(80, 27));
	    buttonExcluirLocal.setFont(new Font("Arial", Font.BOLD, 13));
	    buttonExcluirLocal.setBorder(BorderFactory.createLineBorder(Color.decode("#A52A2A")));
	    buttonExcluirLocal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	    localCadastrado.add(buttonExcluirLocal, BorderLayout.EAST);

	    return localCadastrado;
	}

	//Painel de VEÍCULOS
	private JPanel criarPainelVeiculos() {
	    JPanel veiculos = new JPanel(new GridBagLayout());
	    veiculos.setBackground(Color.decode("#4F4F4F"));
	    GridBagConstraints g = new GridBagConstraints();
	    g.fill = GridBagConstraints.HORIZONTAL;
	    g.insets = new Insets(10, 10, 10, 10);
	    g.gridx = 0;
	    g.gridy = 0;
	    g.gridwidth = 2;

	    //Título de cadastro
	    JLabel tituloCadastro = new JLabel("<html><u>Cadastro de Veículos</u></html>");
	    tituloCadastro.setFont(new Font("Arial", Font.BOLD, 20));
	    tituloCadastro.setForeground(Color.WHITE);
	    veiculos.add(tituloCadastro, g);

	    //Placa do veículo
	    g.gridy++;
	    g.gridwidth = 1;
	    JLabel labelPlaca = new JLabel("Placa:");
	    labelPlaca.setFont(new Font("Arial", Font.BOLD, 16));
	    labelPlaca.setForeground(Color.WHITE);
	    veiculos.add(labelPlaca, g);

	    //Campo para preencher a placa do veículo
	    g.gridx = 1;
	    campoPlaca = new JTextField(20);
	    veiculos.add(campoPlaca, g);

	    //Tipo do veículo
	    g.gridx = 0;
	    g.gridy++;
	    JLabel labelTipo = new JLabel("Tipo:");
	    labelTipo.setFont(new Font("Arial", Font.BOLD, 16));
	    labelTipo.setForeground(Color.WHITE);
	    veiculos.add(labelTipo, g);

	    //Campo para preencher o tipo do veículo
	    g.gridx = 1;
	    campoTipo = new JTextField(20);
	    veiculos.add(campoTipo, g);

	    //Botão de cadastrar
	    g.gridx = 2;
	    g.gridy--;
	    g.gridheight = 2;
	    buttonCadastrarVeiculo = new JButton("Cadastrar");
	    buttonCadastrarVeiculo.setFocusPainted(false);
	    buttonCadastrarVeiculo.setBackground(Color.decode("#A52A2A"));
	    buttonCadastrarVeiculo.setForeground(Color.WHITE);
	    buttonCadastrarVeiculo.setPreferredSize(new Dimension(40, 27));
	    buttonCadastrarVeiculo.setFont(new Font("Arial", Font.BOLD, 13));
	    buttonCadastrarVeiculo.setBorder(BorderFactory.createLineBorder(Color.decode("#A52A2A")));
	    buttonCadastrarVeiculo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	    veiculos.add(buttonCadastrarVeiculo, g);
	    g.gridheight = 1;

	    //Veículos já cadastrados
	    g.gridx = 0;
	    g.gridy += 2;
	    g.gridwidth = 3;
	    JLabel tituloLista = new JLabel("<html><u>Veículos já Cadastrados:</u></html>");
	    tituloLista.setFont(new Font("Arial", Font.BOLD, 20));
	    tituloLista.setForeground(Color.WHITE);
	    veiculos.add(tituloLista, g);

	    //Painel para a listagem de veículos
	    g.gridy++;
	    listagemVeiculos = new JPanel();
	    listagemVeiculos.setLayout(new BoxLayout(listagemVeiculos, BoxLayout.Y_AXIS));
	    JScrollPane scrollPane = new JScrollPane(listagemVeiculos);
	    scrollPane.setPreferredSize(new Dimension(400, 200));
	    veiculos.add(scrollPane, g);

	    return veiculos;
	}

	//Personalizando cada VEÍCULO CADASTRADO
	public JPanel configVeiculoCadastrado(String placa, String tipo) {
		veiculoCadastrado = new JPanel();
		veiculoCadastrado.setLayout(new BorderLayout());
	    veiculoCadastrado.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	    veiculoCadastrado.setBackground(Color.WHITE);
	    
	    //Exibindo a placa e o tipo do veículo
	    JLabel labelVeiculo = new JLabel(placa + " - " + tipo);
	    veiculoCadastrado.add(labelVeiculo, BorderLayout.CENTER);
	    
	    //Botão para deletar o veículo cadastrado
	    buttonExcluirVeiculo = new JButton("Excluir");; 
	    buttonExcluirVeiculo.setFocusPainted(false);
	    buttonExcluirVeiculo.setBackground(Color.decode("#A52A2A"));
	    buttonExcluirVeiculo.setForeground(Color.WHITE);
	    buttonExcluirVeiculo.setPreferredSize(new Dimension(80, 27));
	    buttonExcluirVeiculo.setFont(new Font("Arial", Font.BOLD, 13));
	    buttonExcluirVeiculo.setBorder(BorderFactory.createLineBorder(Color.decode("#A52A2A")));
	    buttonExcluirVeiculo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	    veiculoCadastrado.add(buttonExcluirVeiculo, BorderLayout.EAST);
		
		return veiculoCadastrado;
	}
	
	//Painel de MANUTENÇÃO
	private JPanel criarPainelManutencao() {
	    JPanel manutencao = new JPanel(new GridBagLayout());
	    manutencao.setBackground(Color.decode("#4F4F4F"));
	    GridBagConstraints g = new GridBagConstraints();
	    g.fill = GridBagConstraints.HORIZONTAL;
	    g.insets = new Insets(10, 10, 10, 10);
	    g.gridx = 0;
	    g.gridy = 0;
	    g.gridwidth = 2;

	    //Título de cadastro
	    JLabel tituloCadastro = new JLabel("<html><u>Cadastro de Manutenção</u></html>");
	    tituloCadastro.setFont(new Font("Arial", Font.BOLD, 20));
	    tituloCadastro.setForeground(Color.WHITE);
	    manutencao.add(tituloCadastro, g);

	    //Veículo
	    g.gridy++;
	    g.gridwidth = 1;
	    JLabel labelVeiculo = new JLabel("Veículo:");
	    labelVeiculo.setFont(new Font("Arial", Font.BOLD, 16));
	    labelVeiculo.setForeground(Color.WHITE);
	    manutencao.add(labelVeiculo, g);
	    
	    //ComboBox para selecionar veículos já cadastrados
	    g.gridx = 1;
	    comboBoxVeiculoManutencao = new JComboBox<>();
	    manutencao.add(comboBoxVeiculoManutencao, g);

	    //Local
	    g.gridx = 0;
	    g.gridy++;
	    JLabel labelLocal = new JLabel("Local:");
	    labelLocal.setFont(new Font("Arial", Font.BOLD, 16));
	    labelLocal.setForeground(Color.WHITE);
	    manutencao.add(labelLocal, g);

	    //ComboBox para selecionar locais já cadastrados
	    g.gridx = 1;
	    comboBoxLocalManutencao = new JComboBox<>();
	    manutencao.add(comboBoxLocalManutencao, g);

	    //Descrição
	    g.gridx = 0;
	    g.gridy++;
	    g.gridwidth = 3;
	    JLabel labelDescricao = new JLabel("Descrição:");
	    labelDescricao.setFont(new Font("Arial", Font.BOLD, 16));
	    labelDescricao.setForeground(Color.WHITE);
	    manutencao.add(labelDescricao, g);

	    //Campo para descrição
	    g.gridy++;
	    campoDescricao = new JTextArea(10, 20);
	    campoDescricao.setLineWrap(true);
	    campoDescricao.setWrapStyleWord(true);
	    JScrollPane scrollDescricao = new JScrollPane(campoDescricao);
	    scrollDescricao.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollDescricao.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    manutencao.add(scrollDescricao, g);

	    //Botão para salvar manutenção
	    g.gridy++;
	    g.gridx = 0;
	    g.gridwidth = 2;
	    g.anchor = GridBagConstraints.CENTER;
	    buttonSalvarManutencao = new JButton("Salvar");
	    buttonSalvarManutencao.setFocusPainted(false);
	    buttonSalvarManutencao.setBackground(Color.decode("#A52A2A"));
	    buttonSalvarManutencao.setForeground(Color.WHITE);
	    buttonSalvarManutencao.setPreferredSize(new Dimension(60, 27));
	    buttonSalvarManutencao.setFont(new Font("Arial", Font.BOLD, 13));
	    buttonSalvarManutencao.setBorder(BorderFactory.createLineBorder(Color.decode("#A52A2A")));
	    buttonSalvarManutencao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	    manutencao.add(buttonSalvarManutencao, g);

	    return manutencao;
	}

	//Painel de RELATÓRIO
	private JPanel criarPainelRelatorio() {
        JPanel relatorio = new JPanel(new GridBagLayout());
        relatorio.setBackground(Color.decode("#4F4F4F"));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(10, 10, 10, 10);
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;

        //Título do relatório
        JLabel tituloRelatorio = new JLabel("<html><u>Listagem de Manutenções</u></html>");
        tituloRelatorio.setFont(new Font("Arial", Font.BOLD, 20));
        tituloRelatorio.setForeground(Color.WHITE);
        relatorio.add(tituloRelatorio, g);

        //Data
        g.gridy++;
        g.gridwidth = 1;
        JLabel labelData = new JLabel("Data:");
        labelData.setFont(new Font("Arial", Font.BOLD, 16));
        labelData.setForeground(Color.WHITE);
        relatorio.add(labelData, g);

        //Campo da data
        g.gridx = 1;
        campoData = new JTextField(20);
        relatorio.add(campoData, g);

        //Locais
        g.gridx = 0;
        g.gridy++;
        JLabel labelLocais = new JLabel("Local:");
        labelLocais.setFont(new Font("Arial", Font.BOLD, 16));
        labelLocais.setForeground(Color.WHITE);
        relatorio.add(labelLocais, g);

        //ComboBox de locais cadastrados
        g.gridx = 1;
        comboBoxLocalRelatorio = new JComboBox<>();
        relatorio.add(comboBoxLocalRelatorio, g);

        //Veículos
        g.gridx = 0;
        g.gridy++;
        JLabel labelVeiculos = new JLabel("Veículo:");
        labelVeiculos.setFont(new Font("Arial", Font.BOLD, 16));
        labelVeiculos.setForeground(Color.WHITE);
        relatorio.add(labelVeiculos, g);

        //ComboBox de veículos cadastrados
        g.gridx = 1;
        comboBoxVeiculoRelatorio = new JComboBox<>();
        relatorio.add(comboBoxVeiculoRelatorio, g);
        
        //Status
        g.gridx = 0;
        g.gridy++;
        JLabel labelStatus = new JLabel("Status:");
        labelStatus.setFont(new Font("Arial", Font.BOLD, 16));
        labelStatus.setForeground(Color.WHITE);
        relatorio.add(labelStatus, g);
        
        //ComboBox de status da manutenção
        g.gridx = 1;
        comboBoxStatus = new JComboBox<>();
        relatorio.add(comboBoxStatus, g);

        //Botão de consultar
        g.gridx = 0;
        g.gridy++;
        g.gridwidth = 2;
        buttonConsultar = new JButton("Consultar");
        buttonConsultar.setFocusPainted(false);
        buttonConsultar.setBackground(Color.decode("#A52A2A"));
        buttonConsultar.setForeground(Color.WHITE);
        buttonConsultar.setPreferredSize(new Dimension(100, 30));
        buttonConsultar.setFont(new Font("Arial", Font.BOLD, 16));
        buttonConsultar.setBorder(BorderFactory.createLineBorder(Color.decode("#A52A2A")));
        buttonConsultar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        relatorio.add(buttonConsultar, g);

        return relatorio;
    }
	
	//Janela com o RESULTADO DAS CONSULTAS
	public void abrirJanelaResultado(List<JPanel> paineisManutencao) {
	    JFrame janelaResultado = new JFrame("Resultados da Consulta"); 
	    janelaResultado.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    janelaResultado.setLayout(new BorderLayout());
	    janelaResultado.setSize(600, 500);
	    janelaResultado.setLocationRelativeTo(null);

	    JPanel painelResultado = new JPanel();
	    painelResultado.setBackground(Color.decode("#4F4F4F"));
	    painelResultado.setLayout(new BoxLayout(painelResultado, BoxLayout.Y_AXIS));
	    JScrollPane scrollPane = new JScrollPane(painelResultado);
	    janelaResultado.add(scrollPane, BorderLayout.CENTER);

	    //Título da tela
	    JLabel titulo = new JLabel("Manutenções encontradas");
	    JLabel titulo1 = new JLabel(" ");
	    JLabel titulo2 = new JLabel(" ");
	    titulo.setForeground(Color.WHITE);
	    titulo.setFont(new Font("Arial", Font.BOLD, 18));
	    titulo1.setFont(new Font("Arial", Font.BOLD, 18));
	    titulo2.setFont(new Font("Arial", Font.BOLD, 18));
	    painelResultado.add(titulo1);
	    painelResultado.add(titulo);
	    painelResultado.add(titulo2);
	    
	    for (JPanel painelManutencao : paineisManutencao) {
	        painelResultado.add(painelManutencao);
	        painelResultado.add(Box.createRigidArea(new Dimension(0, 10))); //espaço entre os paineis
	    }

	    janelaResultado.setVisible(true);
	}

	//Adiciona cada manutenção em uma caixa personalizada
	public JPanel adicionarManutencao(JPanel painel, String data, String local, String veiculo,
			boolean status, String usuario, String hora, String descricao, int idManutencao) {
	    JPanel caixaManutencao = new JPanel();
	    caixaManutencao.setLayout(new BorderLayout());
	    caixaManutencao.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    caixaManutencao.setBackground(Color.LIGHT_GRAY);
	    caixaManutencao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
	    caixaManutencao.setAlignmentX(Component.CENTER_ALIGNMENT);
	    caixaManutencao.setPreferredSize(new Dimension(580, 150));
	    
	    JLabel labelData = new JLabel("Data: " + data);
	    labelData.setFont(new Font("Arial", Font.BOLD, 14));
	    
	    JLabel labelHora = new JLabel("Hora: " + hora);
	    labelHora.setFont(new Font("Arial", Font.BOLD, 14));
	    
	    JLabel labelLocal = new JLabel("Local: " + local);
	    labelLocal.setFont(new Font("Arial", Font.BOLD, 14));
	    
	    JLabel labelVeiculo = new JLabel("Veículo: " + veiculo);
	    labelVeiculo.setFont(new Font("Arial", Font.BOLD, 14));
	    
	    JLabel labelUsuario = new JLabel("Usuário: " + usuario);
	    labelUsuario.setFont(new Font("Arial", Font.BOLD, 14));
	    
	    JCheckBox checkBoxStatus = new JCheckBox("Concluído");
	    checkBoxStatus.setEnabled(!status);
	    checkBoxStatus.setSelected(status);
	    
	    JLabel labelDesc = new JLabel("<html>Descrição: " + descricao + "</html>");
	    labelDesc.setFont(new Font("Arial", Font.BOLD, 14));
	    
	    //Atribuindo ID de cada manutenção do painel
	    caixaManutencao.putClientProperty("idManutencao", idManutencao);
	    
	    JPanel painelPersonalizado = new JPanel(new GridLayout(3, 2, 10, 10));
	    painelPersonalizado.setOpaque(false);
	    painelPersonalizado.add(labelData);
	    painelPersonalizado.add(labelHora);
	    painelPersonalizado.add(labelLocal);
	    painelPersonalizado.add(labelVeiculo);
	    painelPersonalizado.add(labelUsuario);
	    painelPersonalizado.add(checkBoxStatus);
	    
	    caixaManutencao.add(painelPersonalizado, BorderLayout.NORTH);
	    caixaManutencao.add(labelDesc, BorderLayout.CENTER);
	    
	    return caixaManutencao;
	}

	//Alterando as cores dos BOTÕES SELECIONADOS
	private void buttonSelecionado(JButton button) {
		if(buttonSelecionado != null) { //verifica se há um botão selecionado
			buttonSelecionado.setBackground(Color.decode("#363636")); //mudando a cor
		}
		button.setBackground(Color.decode("#4F4F4F")); //define a cor para o botão clicado
		buttonSelecionado = button; //atualiza o botão selecionado
	}

	//Configurando a aparência e ações dos BOTÕES DO MENU
	public JButton buttonsMenu(String texto) { 
		
		//Aparência do botão
		JButton button = new JButton(texto); //o texto passado será exibido no botão
		button.setFocusPainted(false); //desativa a borda ao redor do botão
		button.setBackground(Color.decode("#363636")); //cor de fundo
		button.setForeground(Color.WHITE); //cor do texto
		button.setFont(new Font("Arial", Font.BOLD, 15)); //fonte, estilo e tamanho
		button.setHorizontalAlignment(JButton.CENTER); //alinhamento
		button.setBorder(BorderFactory.createLineBorder(Color.decode("#363636"))); //definindo borda ao redor do botão com cor
		button.setPreferredSize(new Dimension(200, 60)); //tamanho e altura
		button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		//Ação do mouse
		button.addMouseListener(new MouseAdapter() {
			//Ao passar o mouse na área do botão
			public void mouseEntered(MouseEvent e) {
				if(buttonSelecionado != button) { //se o botão atual não for o selecionado
					button.setBackground(Color.decode("#4F4F4F")); //mudando a cor
				}
			}
			//Ao sair da área do botão
			public void mouseExited(MouseEvent e) {
				if(buttonSelecionado != button) { //se o botão atual não for o selecionado
					button.setBackground(Color.decode("#363636")); //mudando a cor
				}
			}
			
		});
		
		return button;
	}

	//Configurando a aparência e ações do BOTÃO DE SAIR
	private JButton configButtonSair() {
		
		JButton buttonSair = new JButton("Sair");
		buttonSair.setFocusPainted(false);
		buttonSair.setBackground(Color.decode("#A52A2A"));
		buttonSair.setForeground(Color.WHITE);
		buttonSair.setPreferredSize(new Dimension(200, 40));
		buttonSair.setFont(new Font("Arial", Font.BOLD, 15));
		buttonSair.setBorder(BorderFactory.createLineBorder(Color.decode("#A52A2A")));
		buttonSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		//Ação com o mouse para o botão de sair
		buttonSair.addMouseListener(new MouseAdapter() {
			//Ao mouse entrar na caixa no botão
			public void mouseEntered(MouseEvent evt) {
				buttonSair.setBackground(Color.decode("#A52A2A").darker()); //mudando a cor
			}
			//Ao mouse sair da caixa do botão
			public void mouseExited(MouseEvent evt) {
				buttonSair.setBackground(Color.decode("#A52A2A")); //mudando a cor
			}
		});
		
		//Ação para o botão de sair
		buttonSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsuarioLogado usuarioLogado = UsuarioLogado.getInstance();
				usuarioLogado.setUsuario(null); //limpa user logado
				System.exit(0);
			}
		});
		
		return buttonSair;
	}
	
	//Getters
	//Veículo
	public JButton getButtonCadastrarVeiculo() {
		return buttonCadastrarVeiculo;
	}
	public JButton getButtonExcluirVeiculo() {
		return buttonExcluirVeiculo;
	}
	public JTextField getCampoPlaca() {
		return campoPlaca;
	}
	public JTextField getCampoTipo() {
		return campoTipo;
	}
	public JPanel getVeiculoCadastrado() {
		return veiculoCadastrado;
	}
	public JPanel getListagemVeiculos() {
		return listagemVeiculos;
	}
	
	//Local
	public JButton getButtonCadastrarLocal() {
		return buttonCadastrarLocal;
	}
	public JTextField getCampoLocal() {
		return campoLocal;
	}
	public JTextField getCampoEquipe() {
		return campoEquipe;
	}
	public JButton getButtonExcluirLocal() {
		return buttonExcluirLocal;
	}
	public JPanel getLocalCadastrado() {
		return localCadastrado;
	}
	public JPanel getListagemLocais() {
		return listagemLocais;
	}
	
	//Manutenção/Relatório
	public JButton getButtonSalvarManutencao() {
		return buttonSalvarManutencao;
	}
	public JTextArea getCampoDescricao() {
		return campoDescricao;
	}
	public JComboBox<Local> getComboBoxLocalManutencao() {
		return comboBoxLocalManutencao;
	}
	public JComboBox<Veiculo> getComboBoxVeiculoManutencao(){
		return comboBoxVeiculoManutencao;
	}
	public JComboBox<Local> getComboBoxLocalRelatorio() {
		return comboBoxLocalRelatorio;
	}
	public JComboBox<Veiculo> getComboBoxVeiculoRelatorio(){
		return comboBoxVeiculoRelatorio;
	}
	public JComboBox<String> getComboBoxStatus(){
		return comboBoxStatus;
	}
	public JTextField getCampoData() {
		return campoData;
	}
	public JButton getButtonConsultar() {
		return buttonConsultar;
	}
	public JButton getButtonManutencao() {
		return buttonManutencao;
	}
	public JButton getButtonRelatorio() {
		return buttonRelatorio;
	}
	
}
