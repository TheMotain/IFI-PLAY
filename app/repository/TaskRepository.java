package repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.Task;
import play.db.ebean.EbeanConfig;

/**
 * Requête pour la base de donnée
 */
public class TaskRepository {

	private final EbeanServer ebeanServer;
	private final DatabaseExecutionContext executionContext;

	@Inject
	public TaskRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
		this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
		this.executionContext = executionContext;
	}

	public CompletionStage<List<Task>> findAll(){
    	return CompletableFuture.supplyAsync(
    			() -> {return ebeanServer.find(Task.class).findList();}, executionContext);
    }

	public CompletionStage<Long> save(Task task) {
		return CompletableFuture.supplyAsync(
				() -> {ebeanServer.insert(task);return task.id;}
				,executionContext);
	}

	public Task find(Long id) {
		return ebeanServer.find(Task.class).where().eq("id", id).findOne();
	}
}
