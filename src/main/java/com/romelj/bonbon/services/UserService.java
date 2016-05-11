package com.romelj.bonbon.services;

import com.romelj.bonbon.entities.Session;
import com.romelj.bonbon.entities.User;
import com.romelj.bonbon.entities.json.UserLoginRequestJson;
import com.romelj.bonbon.entities.json.UserRegisterJson;
import com.romelj.bonbon.exceptions.BonBonException;

import java.util.List;

public interface UserService {

	User registerUser(UserRegisterJson userRegisterJson) throws BonBonException;

	User userLogin(UserLoginRequestJson userLoginRequestJson);

	User getUserBy(Session session);

	User getUserBy(String email);

	List<User> getAllUsers();

	void removeUserBy(String userEmail) throws BonBonException;


}
