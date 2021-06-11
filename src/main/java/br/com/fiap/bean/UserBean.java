package br.com.fiap.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.fiap.dao.DAO;
import br.com.fiap.model.User;

@Named
@RequestScoped
public class UserBean {
	
	private User user = new User();
	
	public String login() {
		boolean exist = new DAO<User>(User.class).exist(this.user);
		
		FacesContext context = FacesContext.getCurrentInstance();
		if (exist) {
			context.getExternalContext().getSessionMap().put("user", this.user);
			return "index?faces-redirect=true";
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login inválido", "erro"));
		return "login?faces-redirect=true";
					
	}
	
	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("user");
		return "login?faces-redirect=true";
	}

	public void save() {
		new DAO<User>(User.class).save(this.user);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário cadastrado com sucesso"));
	}
	
	public List<User> getUsers(){
		return new DAO<User>(User.class).getAll();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
