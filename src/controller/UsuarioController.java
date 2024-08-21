package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;
import dao.UsuarioDAO;
import model.Local;
import model.Usuario;
import model.UsuarioLogado;
import model.Veiculo;
import view.CadastroView;
import view.LoginView;
import view.PrincipalView;

public class UsuarioController {
	
	//Atributos
	private Usuario usuario;
	private LoginView loginView;
	private CadastroView cadastroView;
	
	//Construtores
	public UsuarioController(Usuario usuario, LoginView tela) {
		this.usuario = usuario;
		this.usuario = new Usuario();
		this.loginView = tela;
		initView();
	}
	
	//Métodos
	private void initView() {
		loginView.setVisible(true);
	}

	public void initController() {
		
		loginView.getButtonLogin().addActionListener(e-> login());
		loginView.getButtonLogin().addMouseListener(new MouseAdapter(){
			//Ao mouse entrar na caixa no botão
			public void mouseEntered(MouseEvent evt) {
				loginView.getButtonLogin().setBackground(Color.decode("#A52A2A").darker()); //mudando a cor
			}
			//Ao mouse sair da caixa do botão
			public void mouseExited(MouseEvent evt) {
				loginView.getButtonLogin().setBackground(Color.decode("#A52A2A")); //mudando a cor
			}
		});
		
		loginView.getLabelCadastrar().addMouseListener(new MouseAdapter() {
			//Ao clicar na label
			public void mouseClicked(MouseEvent e) {
				telaCadastroView();
			}
		});
		
	}
	
	private boolean login() {
		
		String login = loginView.getTxtUser().getText();
		String senha = new String(loginView.getTxtSenha().getPassword());
		
		UsuarioDAO dao = new UsuarioDAO();
		try {
            if (dao.validarLogin(login, senha)) {
                loginView.getMsg().setText("Login realizado com sucesso!");
                loginView.getMsg().setForeground(Color.GREEN);
                //Criando novo user com os dados obtidos
                Usuario usuario = new Usuario();
                usuario.setLogin(login);
                usuario.setSenha(senha);
                UsuarioLogado.getInstance().setUsuario(usuario); //armazena o user logado
                //Direcionando para tela principal após delay
                Timer timer = new Timer(2000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        telaPrincipalView(); //muda para a tela principal
                    }
                });
                timer.setRepeats(false);
                timer.start();
                return true; //login feito
            } else {
                loginView.getMsg().setText("Usuário e/ou senha incorretos!");
                loginView.getMsg().setForeground(Color.RED);
                return false; //erro no login
            }
        } catch (Exception e) {
            loginView.getMsg().setText("Erro ao realizar login: " + e.getMessage());
            loginView.getMsg().setForeground(Color.RED);
            
            return false; //falha no login devido a erro
        }
		
    }
	
	private void telaPrincipalView() {
		
		PrincipalView principalView = new PrincipalView();
		principalView.setVisible(true);
		
		Local local = new Local();
		Veiculo veiculo = new Veiculo();
		Usuario usuarioLogado = UsuarioLogado.getInstance().getUsuario();
		
		//Inicializando controladores relacionados a tela principal
		LocalController localController = new LocalController(local, principalView);
	    VeiculoController veiculoController = new VeiculoController(veiculo, principalView);
	    ManutencaoController manutencaoController = new ManutencaoController(local, veiculo, usuarioLogado, principalView);
		
	    //Inicializando as funcionalidades dos controladores
	    localController.initController();
	    veiculoController.initController();
	    manutencaoController.initController();
	    
	    //Fechando a tela de login
	    loginView.dispose();
	    
	}
	
	private void telaCadastroView() {
		
		if(cadastroView == null) {
			cadastroView = new CadastroView(); //inicializando
			cadastroView.getButtonCadastrar().addActionListener(e-> cadastrarUsuario());
			cadastroView.getButtonCadastrar().addMouseListener(new MouseAdapter() {
				// Ao mouse entrar na caixa no botão
	            public void mouseEntered(MouseEvent e) {
	                cadastroView.getButtonCadastrar().setBackground(Color.decode("#A52A2A").darker()); //mudando a cor
	            }
	            // Ao mouse sair da caixa do botão
	            public void mouseExited(MouseEvent e) {
	                cadastroView.getButtonCadastrar().setBackground(Color.decode("#A52A2A")); //mudando a cor
	            }
			});
		}
		cadastroView.setVisible(true); //tornando a tela de cadastro visível
        loginView.dispose(); //fechando tela de login
       
	}
	
	private boolean cadastrarUsuario() {
		
		String nome = cadastroView.getTxtNome().getText();
		String user = cadastroView.getTxtUser().getText();
		String senha = new String(cadastroView.getTxtSenha().getPassword());
		String confirmaSenha = new String(cadastroView.getTxtConfirmaSenha().getPassword());
		
		//Se houver campos vazios
		if (nome.isEmpty() || user.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()) {
            cadastroView.getMsg().setText("Todos os campos devem ser preenchidos!");
            cadastroView.getMsg().setForeground(Color.RED);
            return false;
        }
		
		//Realizando cadastro
		if(senha.equals(confirmaSenha)) { //se as senhas coincidirem
			Usuario usuario = new Usuario();
			usuario.setNome(nome);
			usuario.setLogin(user);
			usuario.setSenha(senha);
			
			UsuarioDAO dao = new UsuarioDAO();
			try {
				if(dao.loginIgual(user)) {
					cadastroView.getMsg().setText("Este login já existe! Escolha outro.");
		            cadastroView.getMsg().setForeground(Color.RED);
		            return false;
				} else {
					dao.save(usuario);
	                cadastroView.getMsg().setText("Uuário cadastrado com sucesso!");
	                cadastroView.getMsg().setForeground(Color.GREEN);
	                //Direcionando para a tela de login após delay
	                Timer timer = new Timer(2000, new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                        loginView = new LoginView(); //recria a tela de login
	                        new UsuarioController(usuario, loginView).initController();
	                        cadastroView.dispose(); //fecha a tela de cadastro
	                    }
	                });
	                timer.setRepeats(false);
	                timer.start();
	                return true; //cadastro feito
				}
            } catch (Exception e) {
                cadastroView.getMsg().setText("Erro ao cadastrar usuário: " + e.getMessage());
                cadastroView.getMsg().setForeground(Color.RED);
                return false; //falha no cadastro devido a erro
            }
        } else {
            cadastroView.getMsg().setText("As senhas não coincidem!");
            cadastroView.getMsg().setForeground(Color.RED);
            return false; //erro no cadastro
		}
		
	}
	
	//Getters e Setters
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public LoginView getTela() {
		return loginView;
	}
	public void setTela(LoginView tela) {
		this.loginView = tela;
	}
	
}
