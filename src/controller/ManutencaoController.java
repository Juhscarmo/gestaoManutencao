package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import dao.LocalDAO;
import dao.ManutencaoDAO;
import dao.VeiculoDAO;
import model.Local;
import model.Manutencao;
import model.Usuario;
import model.UsuarioLogado;
import model.Veiculo;
import view.PrincipalView;

public class ManutencaoController {
	
	//Atributos
	private Manutencao manutencao;
	private Local local;
	private Veiculo veiculo;
	private Usuario usuario;
	private PrincipalView principalView;
	
	//Construtores
	public ManutencaoController(Local local, Veiculo veiculo, Usuario usuario, 
			PrincipalView tela) {
		this.local = local;
		this.veiculo = veiculo;
		this.manutencao = new Manutencao();
		this.manutencao.setLocal(local);
		this.manutencao.setVeiculo(veiculo);
		this.manutencao.setUsuario(usuario);
		this.principalView = tela;
		initView();
	}
	
	private void initView() {
		principalView.setVisible(true);
	}
	
	public void initController() {
		
		principalView.getButtonSalvarManutencao().addActionListener(e-> cadastrar());
		principalView.getButtonSalvarManutencao().addMouseListener(new MouseAdapter() {
			//Ao mouse entrar na caixa do botão salvar
			public void mouseEntered(MouseEvent evt) {
	            principalView.getButtonSalvarManutencao().setBackground(Color.decode("#A52A2A").darker());
	        }
			//Ao mouse sair da caixa do botão salvar
	        public void mouseExited(MouseEvent evt) {
	            principalView.getButtonSalvarManutencao().setBackground(Color.decode("#A52A2A"));
	        }
		});
		
		principalView.getButtonConsultar().addActionListener(e-> consultar());
		principalView.getButtonConsultar().addMouseListener(new MouseAdapter() {
			//Ao mouse entrar na caixa do botão consultar
			public void mouseEntered(MouseEvent evt) {
                principalView.getButtonConsultar().setBackground(Color.decode("#A52A2A").darker());
            }
			//Ao mouse sair da caixa do botão consultar
            public void mouseExited(MouseEvent evt) {
                principalView.getButtonConsultar().setBackground(Color.decode("#A52A2A"));
            }
		});
		
		principalView.getButtonManutencao().addActionListener(e-> listarComboBox());
		principalView.getButtonRelatorio().addActionListener(e-> listarComboBox());
		
	}
	
	private void cadastrar(){
		
		String descricao = principalView.getCampoDescricao().getText(); //descrição da manutenção
		
		//Verificando se há campos vazios
		if (descricao.isEmpty()) {
			JDialog dialog = new JDialog();
	        dialog.setAlwaysOnTop(true);
	        dialog.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
	        JOptionPane.showMessageDialog(dialog,
	                "Você deve preencher a descrição da manutenção!",
	                "Erro de Cadastro",
	                JOptionPane.ERROR_MESSAGE);
	        dialog.dispose();
	        return; //não realiza o cadastro
	    }
		
		//Realizando o cadastro
		//Recebendo data atual do sistema
		LocalDate dataAtual = LocalDate.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //formato para SQL
		String dataFormatada = dataAtual.format(formato);
		manutencao.setDataManutencao(dataFormatada); //salvando a data
		
		//Recebendo horário atual do sistema
		LocalTime hrAtual = LocalTime.now();
		DateTimeFormatter formatoHr = DateTimeFormatter.ofPattern("HH:mm");
		String hrFormatada = hrAtual.format(formatoHr);
		manutencao.setHoraManutencao(hrFormatada); //salvando o horário
		
		//Recebendo nome do local selecionado
		Local localSelecionado = (Local) principalView.getComboBoxLocalManutencao().getSelectedItem();
		manutencao.setLocal(localSelecionado);
		
		//Recebendo placa do veiculo selecionado
		Veiculo veiculoSelecionado = (Veiculo) principalView.getComboBoxVeiculoManutencao().getSelectedItem();
		manutencao.setVeiculo(veiculoSelecionado);
		
		//Recebendo login do usuário logado
		UsuarioLogado usuarioLogado = UsuarioLogado.getInstance();
		Usuario usuario = usuarioLogado.getUsuario();
		manutencao.setUsuario(usuario);
		
		//Setando o status da manutenção que acabou de ser feita
		manutencao.setStatus(false);
		
		manutencao.setDescricao(descricao);
		
		ManutencaoDAO dao = new ManutencaoDAO();
		try {
			dao.save(manutencao);
		} catch (Exception ex) {
			Logger.getLogger(ManutencaoController.class.getName()).log(Level.SEVERE, null, ex);
		} 
		
		limpar();
	}
	
	private void consultar() {
		
		//Recebendo filtros passados pelo usuário
		String dataManutencao = principalView.getCampoData().getText();
	    Local localSelecionado = (Local) principalView.getComboBoxLocalRelatorio().getSelectedItem();
	    Veiculo veiculoSelecionado = (Veiculo) principalView.getComboBoxVeiculoRelatorio().getSelectedItem();
	    String statusSelecionado = (String) principalView.getComboBoxStatus().getSelectedItem();
		
	    if (dataManutencao.trim().isEmpty()) {
	        dataManutencao = null;
	    }
	    if (localSelecionado != null && localSelecionado.getLocal().equals("TODOS")) {
	        localSelecionado = null;
	    }
	    if (veiculoSelecionado != null && veiculoSelecionado.getPlaca().equals("TODOS")) {
	        veiculoSelecionado = null;
	    }
	    if (statusSelecionado != null && statusSelecionado.equals("TODOS")) {
	        statusSelecionado = null;
	    }
	    
	    ManutencaoDAO dao = new ManutencaoDAO();
	    List<Manutencao> manutencoesFiltradas = dao.getManutencoesFiltradas(
	    		dataManutencao, localSelecionado, veiculoSelecionado, statusSelecionado);
	    //Verificando se há manutenções com os parâmetros passados
	    if (manutencoesFiltradas.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Não há manutenções com esses parâmetros!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }
	    
	    List<JPanel> paineisManutencao = new ArrayList<>();
	    for(Manutencao manutencao : manutencoesFiltradas) {
	    	String data = manutencao.getDataManutencao();
	    	String hora = manutencao.getHoraManutencao();
	    	String local = manutencao.getLocal().getLocal();
	    	String veiculo = manutencao.getVeiculo().getPlaca();
	    	String usuario = manutencao.getUsuario().getLogin();
	    	String descricao = manutencao.getDescricao();
	    	boolean status = manutencao.isStatus();
	    	int idManutencao = manutencao.getIdManutencao();
	    	
	    	JPanel painelManutencao = principalView.adicionarManutencao(new JPanel(), 
	    			data, local, veiculo, status, usuario, hora, descricao, idManutencao);
	    	JPanel painelPersonalizado = (JPanel) painelManutencao.getComponent(0); 
	    	JCheckBox checkBoxStatus = (JCheckBox) painelPersonalizado.getComponent(5); 
	    	//Ação da CheckBox
	    	checkBoxStatus.addActionListener(e -> {
	    		int id = (int) painelManutencao.getClientProperty("idManutencao"); //recuperando o ID do painel
                if (checkBoxStatus.isSelected()) {
                    int resposta = JOptionPane.showConfirmDialog(null, "Deseja marcar a manutenção como concluída?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) { //se o usuário confirmar
                        atualizarStatusManutencao(id, true);
                    } else {
                        checkBoxStatus.setSelected(false); //se o usuário cancelar
                    }
                }
            });
	    	paineisManutencao.add(painelManutencao);
	    }
	    
	    principalView.abrirJanelaResultado(paineisManutencao);
	  
	}
	
	
	private void atualizarStatusManutencao(int id, boolean novoStatus) {
		ManutencaoDAO dao = new ManutencaoDAO();
	    dao.update(id, novoStatus);
	    
	}
	
	public void listarComboBox() {
		
		//Limpando antes de adicionar os itens
		principalView.getComboBoxLocalManutencao().removeAllItems();
		principalView.getComboBoxVeiculoManutencao().removeAllItems();
		principalView.getComboBoxLocalRelatorio().removeAllItems();
		principalView.getComboBoxVeiculoRelatorio().removeAllItems();
		principalView.getComboBoxStatus().removeAllItems();
		
		//Adicionando "Todos - (Sem Filtro)" nos JComboBox do Relatório
		//Os JComboBox recebem objetos do tipo Local e Veiculo, então adicionar diretamente não funciona
		Local todosLocal = new Local();
		todosLocal.setLocal("TODOS");
		todosLocal.setEquipe("(SEM FILTRO)");
		Veiculo todosVeiculo = new Veiculo();
		todosVeiculo.setPlaca("TODOS");
		todosVeiculo.setTipoVeiculo("(SEM FILTRO)");
		principalView.getComboBoxLocalRelatorio().addItem(todosLocal); //adicionando
		principalView.getComboBoxVeiculoRelatorio().addItem(todosVeiculo); //adicionando
		//Adicionando no JComboBox de Status
		principalView.getComboBoxStatus().addItem("TODOS");
		principalView.getComboBoxStatus().addItem("CONCLUIDO");
		principalView.getComboBoxStatus().addItem("PENDENTE");
		
		//Adicionando locais
		LocalDAO daoLocal = new LocalDAO();
	    try {
	        List<Local> locais = daoLocal.getLocais();
	        for (Local local : locais) {
	            principalView.getComboBoxLocalManutencao().addItem(local);
	            principalView.getComboBoxLocalRelatorio().addItem(local);
	        }
	    } catch (Exception ex) {
	        Logger.getLogger(ManutencaoController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    
	    //Adicionando veiculos
	    VeiculoDAO daoVeiculo = new VeiculoDAO();
	    try {
	        List<Veiculo> veiculos = daoVeiculo.getVeiculos();
	        for (Veiculo veiculo : veiculos) {
	            principalView.getComboBoxVeiculoManutencao().addItem(veiculo);
	            principalView.getComboBoxVeiculoRelatorio().addItem(veiculo);
	        }
	    } catch (Exception ex) {
	        Logger.getLogger(ManutencaoController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
	private void limpar() {
		principalView.getCampoDescricao().setText("");
	}
	
	//Getters e Setters
	public Manutencao getManutencao() {
		return manutencao;
	}
	public void setManutencao(Manutencao manutencao) {
		this.manutencao = manutencao;
	}
	public Local getLocal() {
		return local;
	}
	public void setLocal(Local local) {
		this.local = local;
	}
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
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
