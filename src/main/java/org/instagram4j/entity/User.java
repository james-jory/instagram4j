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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String id;
	private String username;
	@JsonProperty("profile_picture")
	private String profilePicture;
	@JsonProperty("full_name")
	private String fullName;
	private String website;
	private String bio;
	private UserCounts counts;
	
	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public String getFullName() {
		return fullName;
	}

	public String getWebsite() {
		return website;
	}

	public String getBio() {
		return bio;
	}

	public UserCounts getCounts() {
		return counts;
	}
}
