package com.romelj.bonbon.security;

import com.romelj.bonbon.context.RequestContext;
import com.romelj.bonbon.entities.User;
import com.romelj.bonbon.enums.UserRole;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Set;

class BonBonSecurityContext implements SecurityContext {

	private final RequestContext requestContext;

	BonBonSecurityContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * Get userId
	 *
	 * @return userId, if user exists
	 */
	@Override
	public Principal getUserPrincipal() {
		return () -> {
			User user = requestContext.getUser();
			if (user == null) {
				return null;
			}
			return user.getEmail();
		};
	}

	/**
	 * Check if user has permission to access required endpoint
	 *
	 * @param methodRole - endpoint's rolesAllowed
	 * @return true, if user has requested role or false otherwise
	 */
	@Override
	public boolean isUserInRole(String methodRole) {
		User user = requestContext.getUser();
		if (user != null) {
			Set<UserRole> userRoleSet = user.getUserRoleSet();
			for (UserRole canAct : userRoleSet) {
				if (canAct != null && canAct.name().equals(methodRole)) {
					return true;
				}

			}
		}

		return false;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public String getAuthenticationScheme() {
		return null;
	}
}
