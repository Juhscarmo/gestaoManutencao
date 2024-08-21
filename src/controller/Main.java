package controller;

import model.Usuario;
import view.LoginView;

public class Main {

	public static void main(String[] args) {
		UsuarioController c = new UsuarioController(new Usuario(), new LoginView());
		c.initController();
		Usuario p = c.getUsuario();
	}
}
