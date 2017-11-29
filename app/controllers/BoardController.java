package controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.google.inject.Inject;

import models.Task;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.TaskService;

public class BoardController extends Controller {

	@Inject
	TaskService taskService;
	
	@Inject
	FormFactory formFactory;
	
	@Inject
	HttpExecutionContext applicationContext;
	
	public CompletionStage<Result> index() {
		CompletionStage<List<Task>> taskList = taskService.getAll();
		return taskList.thenApply(pi -> ok(views.html.board.render(pi)));
	}
	
	public Result createForm() {
		Form<Task> form = formFactory.form(Task.class);
		return ok(views.html.create.render(form));
	}
	
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
	
	public Result edit(Long id) {
		taskService.find(id);
		return null;
	}
}
