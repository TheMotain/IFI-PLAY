package controllers;

import java.util.List;
import java.util.concurrent.CompletionStage;

import com.google.inject.Inject;

import models.Task;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.TaskService;

public class BoardController extends Controller {

	@Inject
	TaskService taskService;
	
	@Inject
	FormFactory formFactory;
	
	public CompletionStage<Result> index() {
		CompletionStage<List<Task>> taskList = taskService.getAll();
		return taskList.thenApply(pi -> ok(views.html.board.render(pi)));
	}
	
	public Result createForm() {
		Form<Task> form = formFactory.form(Task.class);
		return ok(views.html.create.render(form));
	}
	
	public Result create() {
		Form<Task> form = formFactory.form(Task.class).bindFromRequest();
		if(form.hasErrors()) {
			return badRequest(views.html.create.render(form));
		}
		Task task = form.bindFromRequest().get();
		taskService.save(task);
		return redirect(controllers.routes.BoardController.index());
	}
}
