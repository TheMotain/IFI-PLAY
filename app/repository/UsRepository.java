package repository;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import io.ebean.Ebean;
import io.ebean.EbeanServer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import models.Company;
import models.Us;
import play.db.ebean.EbeanConfig;

public class UsRepository {

	private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public UsRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }
    
    public CompletionStage<List<Us>> getAll() {
    	return supplyAsync(() -> {
    		return ebeanServer.find(Us.class).orderBy("name").findList();
    	});
    }
    
    public CompletionStage<Us> getById(long id) {
    	return supplyAsync(() -> {
    		return ebeanServer.find(Us.class).where().eq("id", id).findOne();
    	});
    }
    
    public CompletionStage<Map<String, String>> options() {
        return supplyAsync(() -> ebeanServer.find(Us.class).orderBy("name").findList(), executionContext)
                .thenApply(list -> {
                    HashMap<String, String> options = new LinkedHashMap<String, String>();
                    for (Us c : list) {
                        options.put(c.id.toString(), c.name);
                    }
                    return options;
                });
    }
    
    public CompletionStage<Long> insert(Us us) {
    	return supplyAsync(() -> {
    		us.init();
    		ebeanServer.insert(us);
    		return us.id;
    	});
    }
}
