package com.curlymaple.server.module.user.message;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.curlymaple.server.core.IMemoryData;
import com.curlymaple.server.core.IService;
import com.curlymaple.server.module.user.User;
import com.curlymaple.server.module.user.UserDao;

@Service
public class UserService implements IService {
	@Resource
	private UserDao userDao;

	@Resource
	private Authenticate_S2C authenticate_S2C;

	private final ConcurrentMap<String, User> users;

	public UserService() {
		users = new ConcurrentHashMap<>(32);
	}

	public Authenticate_S2C getAuthenticate_S2C() {
		return authenticate_S2C;
	}

	public void setAuthenticate_S2C(Authenticate_S2C authenticate_S2C) {
		this.authenticate_S2C = authenticate_S2C;
	}

	public ConcurrentMap<String, User> getUsers() {
		return users;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public void init() {
		List<User> list = this.getUserDao().getAllUser();
		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			users.put(user.getUsername(), user);
		}
	}

	/**
	 * 验证
	 * 
	 * @param memoryData
	 * @param username
	 * @param password
	 */
	public void authenticate(IMemoryData memoryData, String username,
			String password) {

	}
}