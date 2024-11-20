package Repository;

import Domain.Entity;

import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>> {
    Optional<E> findOne(ID id);

    Iterable<E> findAll();

    Optional<E> save(E entity);

    Optional<E> update(E entity);

    Optional<E> delete(ID id);

    Integer size();
}
