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

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Media {
	private String id;
	private String type;
	@JsonProperty("user_has_liked")
	private boolean userHasLiked;
	private String filter;
	private String[] tags;
	private Attribution attribution;
	private Comments comments;
	private Caption caption;
	private Location location;
	@JsonProperty("created_time")
	private String createdTime;
	private String link;
	private Likes likes;
	private Images images;
	private User user;
	private Videos videos;
	
	@JsonProperty("users_in_photo")
	private UserInPhoto[] usersInPhoto;
	
	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public boolean isUserHasLiked() {
		return userHasLiked;
	}

	public String getFilter() {
		return filter;
	}

	public String[] getTags() {
		return tags;
	}

	public Attribution getAttribution() {
		return attribution;
	}

	public Comments getComments() {
		return comments;
	}

	public Caption getCaption() {
		return caption;
	}

	public Location getLocation() {
		return location;
	}
	
	public String getCreatedTime() {
		return createdTime;
	}

	@JsonIgnore
	public Date getCreatedTimeAsDate() {
		if (createdTime == null)
			return null;
		return new Date(Long.parseLong(createdTime) * 1000);
	}
	
	public String getLink() {
		return link;
	}

	public Likes getLikes() {
		return likes;
	}

	public Images getImages() {
		return images;
	}

	public User getUser() {
		return user;
	}

	public UserInPhoto[] getUsersInPhoto() {
		return usersInPhoto;
	}
	
	public Videos getVideos() {
		return videos;
	}
}
