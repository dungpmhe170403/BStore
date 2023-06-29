package model.action;

public interface Crud<T> {
    T save(T data);

    void get(int id);

    void update(int id, T data);

    void delete(int id);
}
