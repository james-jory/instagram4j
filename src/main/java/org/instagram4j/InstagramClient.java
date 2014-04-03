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
package org.instagram4j;

import org.instagram4j.entity.Comment;
import org.instagram4j.entity.Location;
import org.instagram4j.entity.Media;
import org.instagram4j.entity.Relationship;
import org.instagram4j.entity.RelationshipAction;
import org.instagram4j.entity.Subscription;
import org.instagram4j.entity.User;

public interface InstagramClient {
	String getClientId();
	String getClientSecret();
	
	String getAccessToken();
	void setAccessToken(String token);
	
	long getAutoThrottle();
	void setAutoThrottle(long minDelay);
	
	Result<User> getCurrentUser() throws InstagramException;
	Result<User> getUser(String userId) throws InstagramException;
	Result<User[]> getFollowers(String userId, int count) throws InstagramException;
	Result<User[]> getUsersNext(Pagination pagination) throws InstagramException;
	Result<User[]> searchUsers(String query, int count) throws InstagramException;
	
	Result<Relationship> getRelationship(String userId) throws InstagramException;
	
	Result<Media> getMedia(String mediaId) throws InstagramException;
	Result<Media[]> getRecentMediaForUser(String userId, Parameter...params) throws InstagramException;
	Result<Media[]> getUserFeed(Parameter...params) throws InstagramException;
	Result<Media[]> getRecentMediaForTag(String tag, Parameter...params) throws InstagramException;
	Result<Media[]> getRecentMediaAtLocation(String locationId, Parameter...params) throws InstagramException;
	Result<Media[]> getRecentMediaNearby(double latitude, double longitude, Integer radiusMeters, Parameter...params) throws InstagramException;
	Result<Media[]> getMediaNext(Pagination pagination) throws InstagramException;
	
	Result<Media[]> getRecentMediaForGeoSubscription(String geoSubscriptionId) throws InstagramException;

	Result<Location> getLocation(String locationId) throws InstagramException;
	Result<Location[]> getLocationsNearby(double latitude, double longitude, Integer radiusKm, Parameter...params) throws InstagramException;
	Result<Location[]> getLocationsForFoursquareVenue(String venueId, Parameter...params) throws InstagramException;
	Result<Location[]> getLocationsNext(Pagination pagination) throws InstagramException;
	
	Result<User[]> getMediaLikes(String mediaId, int count) throws InstagramException;
	Result<Comment[]> getMediaComments(String mediaId, int count) throws InstagramException;
	Result<Comment[]> getCommentsNext(Pagination pagination) throws InstagramException;
	
	void addComment(String mediaId, String comment) throws InstagramException;
	void deleteComment(String mediaId, String commentId) throws InstagramException;
	
	void likeMedia(String mediaId) throws InstagramException;
	void unlikeMedia(String mediaId) throws InstagramException;
	Result<Relationship> relationshipAction(String userId, RelationshipAction action) throws InstagramException;
	
	Result<Subscription> createUserSubscription(String verifyToken, String callbackUrl) throws InstagramException;
	Result<Subscription> createTagSubscription(String tag, String verifyToken, String callbackUrl) throws InstagramException;
	Result<Subscription> createLocationSubscription(String locationId, String verifyToken, String callbackUrl) throws InstagramException;
	Result<Subscription> createGeoSubscription(double latitude, double longitude, int radius, String verifyToken, String callbackUrl) throws InstagramException;
	
	Result<Subscription[]> getSubscriptions() throws InstagramException;

	void deleteSubscription(String subscriptionId) throws InstagramException;
	void deleteUserSubscription() throws InstagramException;
	void deleteTagSubscriptions() throws InstagramException;
	void deleteLocationSubscriptions() throws InstagramException;
	void deleteGeoSubscriptions() throws InstagramException;
	void deleteAllSubscriptions() throws InstagramException;
}