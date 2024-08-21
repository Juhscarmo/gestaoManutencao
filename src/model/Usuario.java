package model;

public class Usuario {
	
	//Atributos
	private String nomeUser;
	private String loginUser;
	private String senhaUser;
	
	//Getters e Setters
	public String getNome() {
		return nomeUser;
	}
	public void setNome(String nome) {
		this.nomeUser = nome;
	}
	public String getLogin() {
		return loginUser;
	}
	public void setLogin(String login) {
		this.loginUser = login;
	}
	public String getSenha() {
		return senhaUser;
	}
	public void setSenha(String senha) {
		this.senhaUser = senha;
	}
	
}
