package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import models.Login;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Gestion du login dans la session
 */
public class LoginController extends Controller{

	/**
	 * formulaire
	 */
	private final FormFactory formFactory;
	/**
	 * contexte d'execution
	 */
	private final HttpExecutionContext executionContext;
	
	/**
	 * Constructeur avec les injections
	 * 
	 * @param formFactory
	 * @param httpExecutionContext
	 */
	@Inject
	public LoginController(FormFactory formFactory, HttpExecutionContext httpExecutionContext) {
		this.formFactory = formFactory;
		this.executionContext = httpExecutionContext;
	}
	
	/**
	 * Affichage de la page de login
	 * 
	 * @return la page de login
	 */
	public CompletionStage<Result> index() {
		return CompletableFuture.supplyAsync(() -> {
			Form<Login> form = formFactory.form(Login.class);
			return ok(views.html.login.render(form));
		}, this.executionContext.current());
	}
	
	/**
	 * Enregistre dans la session le login de l'utilisateur
	 * 
	 * @return la page principale
	 */
	public CompletionStage<Result> connect() {
		return CompletableFuture.supplyAsync(() -> {
			Form<Login> form = formFactory.form(Login.class).bindFromRequest();
			Login login = form.get();
			session("login", login.getName());
			return redirect(controllers.routes.BoardController.index());
		}, this.executionContext.current());
	}
	
	/**
	 * Supprime de la session le login de l'utilisateur
	 * 
	 * @return la page principale
	 */
	public CompletionStage<Result> disconnect() {
		return CompletableFuture.supplyAsync(() -> {
			session().remove("login");
			return redirect(controllers.routes.BoardController.index());
		}, this.executionContext.current());
	}
}
