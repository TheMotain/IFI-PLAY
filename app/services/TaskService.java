package services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import models.Task;
import repository.TaskRepository;

/**
 *Service qui fait des appels au repository et fait son action sur le même thread (thenApply)
 */
public class TaskService {

	private final List<Task> stories;

	private final TaskRepository taskRepository;
	
    @Inject
    public TaskService(TaskRepository taskRepository) {
        this.stories = new ArrayList<>();
        this.stories.add(new Task(1L, "Faire le TP :)", "Avec de la bonne humeur"));
        this.stories.add(new Task(2L, "Avoir son Master", "Vivement le stage"));
        this.taskRepository = taskRepository;
    }
    
    public CompletionStage<List<Task>> getAll() {
    	return taskRepository.findAll().thenApply(lst -> {return lst;});
    }

	public CompletionStage<Long> save(Task task) {
		return taskRepository.save(task).thenApply(id -> {return id;});
	}
	
	public CompletionStage<Long> update(Task task, Long id) {
		return taskRepository.update(task, id).thenApply(idTask -> {return idTask;});
	}

	public CompletionStage<Task> find(Long id) {
		return taskRepository.find(id).thenApply(task -> {return task;});
	}
    
}
