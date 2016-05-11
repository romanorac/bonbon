package com.romelj.bonbon.services;


import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;
import com.romelj.bonbon.entities.Session;
import com.romelj.bonbon.entities.User;
import com.romelj.bonbon.entities.json.UserLoginRequestJson;
import com.romelj.bonbon.entities.json.UserRegisterJson;
import com.romelj.bonbon.enums.UserRole;
import com.romelj.bonbon.exceptions.BonBonError;
import com.romelj.bonbon.exceptions.BonBonException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Basic handlers for managing users
 */
public class UserServiceImpl implements UserService {

	private static final Logger logger = Logger.getLogger(UserService.class.getSimpleName());
	private final static List<String> ADMIN_EMAILS = Arrays.asList("roman.orac@bonbon.com"); //admin emails
	private final Provider<Objectify> ofyProvider;

	@Inject
	public UserServiceImpl(Provider<Objectify> ofyProvider) {
		this.ofyProvider = ofyProvider;
	}

	/**
	 * Register new user
	 *
	 * @param userRegisterJson - userRegister info from post request with userEmail, password, name, lastName
	 * @return new user
	 * @throws BonBonException - user already exists
	 */
	@Override
	public User registerUser(UserRegisterJson userRegisterJson) throws BonBonException {
		if (userRegisterJson == null) {
			throw new BonBonException(BonBonError.MissingRegistrationData);
		}

		if (Strings.isNullOrEmpty(userRegisterJson.getEmail())) {
			throw new BonBonException(BonBonError.MissingUserEmail);
		}

		String password = DigestUtils.sha256Hex(userRegisterJson.getPassword());

		User user = getUserBy(userRegisterJson.getEmail()); //check if user does not exists

		if (user == null) {
			Objectify ofy = ofyProvider.get();

			long created = System.currentTimeMillis();
			String email = userRegisterJson.getEmail().toLowerCase().trim();

			//set user roles
			Set<UserRole> userRoleSet = new HashSet<>();
			if (ADMIN_EMAILS.contains(userRegisterJson.getEmail())) {
				userRoleSet.add(UserRole.Admin);
			}
			userRoleSet.add(UserRole.User);

			//create new user
			user = new User(email, password, userRegisterJson.getName(), userRegisterJson.getLastname(), created, userRoleSet);

			ofy.save().entities(user).now();
			logger.info(user + " user created");

		} else {
			logger.info(user + "{} user already exists");
			throw new BonBonException(BonBonError.UserExists);
		}

		return user;

	}


	/**
	 * User login
	 *
	 * @param userLoginRequestJson - userLogin info from post request with userEmail and password
	 * @return user
	 * @throws BonBonException - User does not exists, incorrect password, etc.
	 */
	@Override
	public User userLogin(UserLoginRequestJson userLoginRequestJson) throws BonBonException{
		if (userLoginRequestJson == null) {
			throw new BonBonException(BonBonError.IncorrectLoginData);
		}

		if (Strings.isNullOrEmpty(userLoginRequestJson.getEmail())) {
			throw new BonBonException(BonBonError.MissingUserEmail);
		}

		if (Strings.isNullOrEmpty(userLoginRequestJson.getPassword())) {
			throw new BonBonException(BonBonError.MissingPassword);
		}

		User user = getUserBy(userLoginRequestJson.getEmail());

		if (user == null) {
			throw new BonBonException(BonBonError.UserNotExists);
		}

		String password = DigestUtils.sha256Hex(userLoginRequestJson.getPassword()); //sha256Hex of password

		if (!user.getPassword().equals(password)) {
			throw new BonBonException(BonBonError.IncorrectPassword);
		}

		return user;

	}


	/**
	 * Get user by email in session
	 *
	 * @param session - session with email
	 * @return user or null
	 */
	@Override
	public User getUserBy(Session session) {
		User user = null;
		if (session != null && !Strings.isNullOrEmpty(session.getUserEmail())) {
			user = getUserBy(session.getUserEmail());
		}
		return user;
	}

	/**
	 * Get user by email
	 *
	 * @param email - user email
	 * @return user or null
	 */
	@Override
	public User getUserBy(String email) {
		User user = null;
		if (!Strings.isNullOrEmpty(email)) {
			Objectify ofy = ofyProvider.get();
			user = ofy.load().type(User.class).id(email).now();
			if (user == null) {
				logger.info("User with email: " + email + " does not exist.");
			}
		}
		return user;
	}

	/**
	 * Get all users from db
	 *
	 * @return all users in db
	 */
	@Override
	public List<User> getAllUsers() {
		Objectify ofy = ofyProvider.get();
		List<User> userList = ofy.load().type(User.class).list();
		if (userList == null || userList.size() == 0) {
			logger.warning("No users found in db");
		}
		return userList;
	}

	/**
	 * Remove user by email
	 *
	 * @param userEmail - email of a user
	 * @throws BonBonException - user does not exists
	 */
	@Override
	public void removeUserBy(String userEmail) throws BonBonException {
		if (!Strings.isNullOrEmpty(userEmail) && getUserBy(userEmail) != null) {
			Objectify ofy = ofyProvider.get();
			ofy.delete().type(User.class).id(userEmail).now();
			logger.info("User " + userEmail + " was removed");
		} else {
			logger.warning("User " + userEmail + " does not exists");
			throw new BonBonException(BonBonError.UserNotExists);
		}
	}

}
