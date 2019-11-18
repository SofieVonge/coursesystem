package dk.kea.kurser.legacy.services;

import java.util.List;

public interface IApiService<T> {

    public List<T> listAll();

    public void add(T param);

    public T findById(int id);

    public void update(T param);

    public void delete(T param);

    public boolean exists(int id);
}
