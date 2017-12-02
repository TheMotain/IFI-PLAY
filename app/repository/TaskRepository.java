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

	/**
	 * serveur ebean
	 */
	private final EbeanServer ebeanServer;
	/**
	 * contexte d'execution de la base de données
	 */
	private final DatabaseExecutionContext executionContext;

	/**
	 * Constructeur et ses injections
	 * 
	 * @param ebeanConfig
	 * @param executionContext
	 */
	@Inject
	public TaskRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
		this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
		this.executionContext = executionContext;
	}

	/**
	 * Récupère toutes les tâches
	 * 
	 * @return les tâches récupérées
	 */
	public CompletionStage<List<Task>> findAll(){
    	return CompletableFuture.supplyAsync(
    			() -> {return ebeanServer.find(Task.class).findList();}, executionContext);
    }

	/**
	 * Enregistre une tâche
	 * 
	 * @param task
	 * @return l'id de la tâche
	 */
	public CompletionStage<Long> save(Task task) {
		return CompletableFuture.supplyAsync(
				() -> {ebeanServer.insert(task);return task.id;}
				,executionContext);
	}
	
	/**
	 * Modifie une tâche
	 * 
	 * @param task 
	 * @param id
	 * @return l'id de la tâche modifiée
	 */
	public CompletionStage<Long> update(Task task, Long id) {
		return CompletableFuture.supplyAsync(
				() -> {ebeanServer.update(task);
				System.out.println("here" + task.description + " " + task.name + " " + task.status);
				Task savedTask = ebeanServer.find(Task.class).setId(id).findUnique();
                if (savedTask != null) {
                    savedTask.description = task.description;
                    savedTask.name = task.name;
                    savedTask.status = task.status;

                    savedTask.update();
                }
				return id;
			}
			,executionContext);
	}

	/**
	 * Récupère une tâche en particulier
	 * 
	 * @param id
	 * @return la tâche
	 */
	public CompletionStage<Task> find(Long id) {
		return CompletableFuture.supplyAsync(
				() -> {return ebeanServer.find(Task.class).where().eq("id", id).findOne();}
				, executionContext);
	}
}
