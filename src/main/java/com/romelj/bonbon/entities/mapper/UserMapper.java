package com.romelj.bonbon.entities.mapper;

import com.romelj.bonbon.entities.User;
import com.romelj.bonbon.entities.json.UserInfoJson;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

	/**
	 * Map User object to UserInfo object that does not contain sensitive info
	 *
	 * @param user - User object
	 * @return UserInfo object
	 */
	public static UserInfoJson map(User user) {
		if (user != null) {
			return new UserInfoJson(user);
		}
		return null;
	}

	/**
	 * Map list of User Object to list of UserInfo objects that does not contain sensitive info
	 *
	 * @param userList - list of User objects
	 * @return list of User info objects
	 */
	public static List<UserInfoJson> map(List<User> userList) {
		List<UserInfoJson> userInfoJsonList = new ArrayList<>();
		if (userList != null && userList.size() > 0) {
			for (User user : userList) {
				userInfoJsonList.add(map(user));
			}
		}
		return userInfoJsonList;
	}
}
