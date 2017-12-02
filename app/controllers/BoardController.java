package controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.google.inject.Inject;

import models.Status;
import models.Task;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.TaskService;

/**
 * Contrôleur de gestion des tâches
 * Tous les appels sont gérés en asynchrone
 */
public class BoardController extends Controller {

	/**
	 * Service de tâche
	 */
	private final TaskService taskService;
	/**
	 * Formulaire
	 */
	private final FormFactory formFactory;
	/**
	 * contexte d'application
	 */
	private final HttpExecutionContext applicationContext;
	
	/**
	 * Constructeur avec les injections
	 * 
	 * @param applicationContext
	 * @param formFactory
	 * @param taskService
	 */
	@Inject
	public BoardController (HttpExecutionContext applicationContext, FormFactory formFactory, TaskService taskService){
		this.applicationContext = applicationContext;
		this.formFactory = formFactory;
		this.taskService = taskService;
	}
	
	/**
	 * Récupération de la liste des tâches et affichage de la page principale
	 * 
	 * @return la page principale
	 */
	public CompletionStage<Result> index() {
		CompletionStage<List<Task>> taskList = taskService.getAll();
		return taskList.thenApplyAsync(pi -> ok(views.html.board.render(pi)), applicationContext.current());
	}
	
	/**
	 * Création d'un formulaire de tâche
	 * 
	 * @return la page de création d'un formulaire de tâche
	 */
	public CompletionStage<Result> createForm() {
		return CompletableFuture.supplyAsync(
				() -> {
					Form<Task> form = formFactory.form(Task.class);
					return ok(views.html.create.render(form));
				}, applicationContext.current());
	}
	
	/**
	 * Réception du formulaire 
	 * Enregistre dans la mémoire flash un message d'erreur si il y a une erreur dans le formulaire et redirige vers la page de création d'une tâche,
	 * S'il y en a pas, enregistrement de la nouvelle tâche et redirection vers la page principale
	 * 
	 * @return la page appropriée
	 */
	public CompletionStage<Result> create() {
		Form<Task> form = formFactory.form(Task.class).bindFromRequest();
		if(form.hasErrors()) {
			flash("errors", "Nom obligatoire");
			return CompletableFuture.supplyAsync(
					() -> {return badRequest(views.html.create.render(form));}
					,applicationContext.current());
		}
		Task task = form.bindFromRequest().get();
		CompletionStage promesse = taskService.save(task);
		return promesse.thenApply(pi -> redirect(controllers.routes.BoardController.index()));
	}
	
	/**
	 * Ouvre la page du formulaire de la tâche ayant l'id id
	 * 
	 * @param id de la tâche
	 * @return la page du formulaire de la tâche avec les informations pré-remplies
	 */
	public CompletionStage<Result> edit(Long id) {
		Form<Task> form = formFactory.form(Task.class);
		CompletionStage<Task> taskList = taskService.find(id);
		return taskList.thenApplyAsync(pi -> ok(views.html.edit.render(form.fill(pi), Status.getMap(), id)), applicationContext.current());

	}
	
	/**
	 * Enregistre les nouvelles modifications en base de données
	 * 
	 * @param id de la tâche
	 * @return la page d'édition s'il y a eu une erreur sinon la page principale
	 */
	public CompletionStage<Result> update(Long id) {
		Form<Task> form = formFactory.form(Task.class).bindFromRequest();
		if(form.hasErrors()) {
			flash("errors", "Le formulaire contient des erreurs");
			return CompletableFuture.supplyAsync(
					() -> {return badRequest(views.html.edit.render(form, Status.getMap(), id));}
					,applicationContext.current());
		}
		Task task = form.bindFromRequest().get();
		CompletionStage promesse = taskService.update(task, id);
		return promesse.thenApplyAsync(pi -> redirect(controllers.routes.BoardController.index()), applicationContext.current());

	}
}
