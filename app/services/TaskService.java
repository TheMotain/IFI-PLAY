package services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import models.Task;
import repository.TaskRepository;

/**
 *Service 
 */
public class TaskService {

	private final List<Task> stories;

	@Inject
	TaskRepository taskRepository;
	
    @Inject
    public TaskService() {
        this.stories = new ArrayList<>();
        this.stories.add(new Task(1L, "Faire le TP :)", "Avec de la bonne humeur"));
        this.stories.add(new Task(2L, "Avoir son Master", "Vivement le stage"));
    }
    
    public CompletionStage<List<Task>> getAll() {
    	return taskRepository.findAll();
    }

	public void save(Task task) {
		taskRepository.save(task);
	}
    
}
