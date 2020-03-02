package repository.api;

import util.api.RepositoryActionInvoker;
import util.api.RepositoryActionResult;

import java.util.List;

public interface BaseRepository<Entity, Id> {
    void save(Entity entity);

    void merge(Entity entity);

    List<Entity> findAll();

    Entity findById(Id id);

    long size();

    RepositoryActionResult execute(RepositoryActionInvoker invoker);
}
