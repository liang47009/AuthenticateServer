package com.curlymaple.server.module.user;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.curlymaple.server.core.DataSourceSelector;
import com.curlymaple.server.core.IDao;

@Repository
public class UserDao implements IDao<User> {

	@Resource
	private DataSourceSelector dataSourceSelector;

	@Override
	public User get(int id) {
		User user = (User) this.dataSourceSelector.getSessionFactory()
				.getCurrentSession().load(User.class, id);
		return user;
	}

	@Override
	public int save(User entity) {
		int id = (Integer) this.dataSourceSelector.getSessionFactory()
				.getCurrentSession().save(entity);
		return id;
	}

	@Override
	public void update(User entity) {
		this.dataSourceSelector.getSessionFactory().getCurrentSession()
				.update(entity);
	}

	@Override
	public void delete(User entity) {
		this.dataSourceSelector.getSessionFactory().getCurrentSession()
				.delete(entity);
	}

	public List<User> getAllUser() {
		Query q = this.dataSourceSelector.getSessionFactory()
				.getCurrentSession().createQuery("from User");
		return q.list();
	}

	public DataSourceSelector getDataSourceSelector() {
		return this.dataSourceSelector;
	}

	public void setDataSourceSelector(DataSourceSelector dataSourceSelector) {
		this.dataSourceSelector = dataSourceSelector;
	}

}