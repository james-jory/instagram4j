package org.instagram4j.entity;

/**
 * Relationship actions to perform when updating a relationship between users.
 */
public enum RelationshipAction {
	FOLLOW,
	UNFOLLOW,
	BLOCK,
	UNBLOCK,
	APPROVE,
	IGNORE;
	
	/**
	 * Returns the command used when updating the relationship in API call.
	 * @return
	 */
	public String getCommand() {
		return name().toLowerCase();
	}
}
