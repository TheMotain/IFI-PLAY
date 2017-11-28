package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import models.Us;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.USService;

public class USController extends Controller{

	private USService usService;
    private final HttpExecutionContext httpExecutionContext;
    private final FormFactory formFactory;
	
	@Inject
	public USController(FormFactory formFactory, USService usService, HttpExecutionContext httpExecutionContext) {
		this.usService= usService;
		this.httpExecutionContext = httpExecutionContext;
		this.formFactory = formFactory;
	}
	
	public CompletionStage<Result> getAllStories() {
		return this.usService.search().thenApplyAsync(list -> {
            // This is the HTTP rendering thread context
            return ok(views.html.stories.render(list));
        }, httpExecutionContext.current());
	}
	
	public CompletionStage<Result> getStory(long id) {
		return this.usService.searchById(id).thenApplyAsync(story -> {
            // This is the HTTP rendering thread context
            return ok(views.html.story.render(story));
        }, httpExecutionContext.current());
	}
	
	public Result create() {
		Form<Us> usForm = formFactory.form(Us.class);
		return ok(views.html.createUs.render(usForm));
	}
	
	public CompletionStage<Result> save() {
		Form<Us> usForm = formFactory.form(Us.class).bindFromRequest();
		if (usForm.hasErrors()) {
			return CompletableFuture.supplyAsync(() -> {
				return badRequest(views.html.createUs.render(usForm));
			}, httpExecutionContext.current());
		}
		Us us = usForm.get();
		return this.usService.insert(us).thenApplyAsync(data -> {
			flash("success", "US " + us.name + " a été créée.");
			return redirect(routes.USController.getAllStories());
		}, httpExecutionContext.current());
	}
	
}
