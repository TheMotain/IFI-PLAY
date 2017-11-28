package controllers;

import javax.inject.Inject;

import models.Login;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

public class LoginController extends Controller{
	
	private final FormFactory formFactory;
	
	@Inject
	public LoginController(FormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public Result index() {
		Form<Login> loginForm = formFactory.form(Login.class);
        return ok(views.html.login.render(loginForm));
    }
	
	public Result login() {
		Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
		Login login = loginForm.get();
		session().put("login", login.getName());
		return redirect(controllers.routes.USController.getAllStories());
    }
	
	public Result logout() {
	    session().clear();
	    return ok("Bye");
	}
}
