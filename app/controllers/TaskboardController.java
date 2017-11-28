package controllers;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import models.Login;
import models.Us;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.USService;

public class TaskboardController extends Controller{

	private final USService usService;
	private final HttpExecutionContext httpExecutionContext;
	private final FormFactory formFactory;
	
	@Inject
	public TaskboardController(USService usService, FormFactory formFactory, HttpExecutionContext httpExecutionContext){
		this.formFactory = formFactory;
		this.httpExecutionContext = httpExecutionContext;
		this.usService = usService;
	}
	
	public CompletionStage<Result> index() {
		Form<Us> form = formFactory.form(Us.class);
		return this.usService.search().thenApplyAsync(list -> {
			return ok(views.html.board.render(list, form));
		}, httpExecutionContext.current());
    }
	
}
