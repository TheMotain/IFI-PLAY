package services;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Result;
import models.Company;
import models.Us;
import repository.UsRepository;

public class USService {

	private final UsRepository usRepository;
	private final HttpExecutionContext httpExecutionContext;

    @Inject
    public USService(UsRepository usRepository, HttpExecutionContext httpExecutionContext) {
        this.usRepository = usRepository;
        this.httpExecutionContext = httpExecutionContext;
    }
    
    public CompletionStage<List<Us>> search() {
    	return this.usRepository.getAll();
    }
    
    public CompletionStage<Us> searchById(long id) {
    	return this.usRepository.getById(id);
    }
    
    public CompletionStage<Long> insert(Us us) {
    	return this.usRepository.insert(us);
    }

	public CompletionStage<Map<String, String>> searchOption() {
		return this.usRepository.options();
	}
}
