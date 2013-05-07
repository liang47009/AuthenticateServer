package com.curlymaple.server.core;

public interface IDao<T> {
	T get(int id);

	int save(T entity);
	
	void update(T entity);
	
	void delete(T entity);
}
