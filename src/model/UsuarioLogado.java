package model;

public class UsuarioLogado {

	//Atributos
	private static UsuarioLogado instance; //instância única da classe
	private Usuario usuario; //aramazena user logado
	
	//Construtores
	//Construtor privado para impedir a criação de novas instâncias
	private UsuarioLogado() {
		
	}
	
	//Métodos
	//Obtem a instância única da classe
	public static UsuarioLogado getInstance() {
        if (instance == null) {
            instance = new UsuarioLogado();
        }
        return instance;
    }
	
	//Getters e Setters
	public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
		
}
