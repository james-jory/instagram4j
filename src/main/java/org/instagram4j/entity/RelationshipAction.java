/**
 * Copyright (C) 2014 James Jory (james.b.jory@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
