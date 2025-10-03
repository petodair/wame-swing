package br.com.wame.controller;

import br.com.wame.form.JHome;
import br.com.wame.form.work.JWorkRegister;
import br.com.wame.form.work.JWorks;
import br.com.wame.manager.ContentManager;
import br.com.wame.manager.FormManager;
import br.com.wame.manager.SessionManager;
import br.com.wame.view.JContent;
import br.com.wame.view.JLogin;

public class ContentController {

	private final JContent content;

	public ContentController(JContent content) {
		this.content = content;
	}

	public void openHomeScreen() {
		FormManager.getInstance().showForm(new JHome(content.getContentPanel(), content));
	}

	public void openWorksScreen() {
		FormManager.getInstance().showForm(new JWorks(content));
	}

	public void abrirTelaCadastroTrabalho() {
		FormManager.getInstance().showForm(new JWorkRegister(content.getContentPanel(), content));
	}

	public void realizarLogout() {
		SessionManager.setToken(null);
		ContentManager.getInstance().setContentPane(new JLogin(content.getMain()));
	}
}
