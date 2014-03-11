package org.instagram4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.instagram4j.entity.Comment;
import org.instagram4j.entity.Location;
import org.instagram4j.entity.Media;
import org.instagram4j.entity.Relationship;
import org.instagram4j.entity.RelationshipAction;
import org.instagram4j.entity.Subscription;
import org.instagram4j.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;

public class DefaultInstagramClient implements InstagramClient {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultInstagramClient.class);
	
	private static final JsonFactory JSON_FACTORY = new MappingJsonFactory();
	private static final URLCodec ENCODER = new URLCodec("UTF-8");
	
	private final String clientId;
	private final String clientSecret;
	private String accessToken;
	private long autoThrottle;
	private long lastCallTicks;

	public DefaultInstagramClient(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public DefaultInstagramClient(String clientId, String clientSecret, String accessToken) {
		this(clientId, clientSecret);
		this.accessToken = accessToken;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public String getClientSecret() {
		return clientSecret;
	}

	@Override
	public String getAccessToken() {
		return accessToken;
	}

	@Override
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public long getAutoThrottle() {
		return autoThrottle;
	}

	@Override
	public void setAutoThrottle(long minDelay) {
		this.autoThrottle = minDelay;
	}

	@Override
	public Result<User> getCurrentUser() throws InstagramException {
		return getUser("self");
	}

	@Override
	public Result<User> getUser(String userId) throws InstagramException {
		URIBuilder uri = createUriBuilder("https://api.instagram.com/v1/users/" + urlEncode(userId));
		return queryEntity(uri.toString(), User.class);
	}

	@Override
	public Result<User[]> getFollowers(String userId, int count) throws InstagramException {
		URIBuilder uri = createUriBuilder(String.format("https://api.instagram.com/v1/users/%s/followed-by", urlEncode(userId)), Parameter.as("count", count));
		return queryEntities(uri.toString(), User.class);
	}

	@Override
	public Result<User[]> searchUsers(String query, int count) throws InstagramException {
		URIBuilder uri = createUriBuilder("https://api.instagram.com/v1/users/search", Parameter.as("q", query), Parameter.as("count", count));
		return queryEntities(uri.toString(), User.class);
	}

	@Override
	public Result<User[]> getMediaLikes(String mediaId, int count) throws InstagramException {
		URIBuilder uri = createUriBuilder(String.format("https://api.instagram.com/v1/media/%s/likes", urlEncode(mediaId)), Parameter.as("count", count));
		return queryEntities(uri.toString(), User.class);
	}

	@Override
	public Result<Comment[]> getMediaComments(String mediaId, int count) throws InstagramException {
		URIBuilder uri = createUriBuilder(String.format("https://api.instagram.com/v1/media/%s/comments", urlEncode(mediaId)), Parameter.as("count", count));
		return queryEntities(uri.toString(), Comment.class);
	}

	@Override
	public Result<Comment[]> getCommentsNext(Pagination pagination) throws InstagramException {
		return queryEntities(pagination.getNextUrl(), Comment.class);
	}

	@Override
	public Result<User[]> getUsersNext(Pagination pagination) throws InstagramException {
		return queryEntities(pagination.getNextUrl(), User.class);
	}

	@Override
	public Result<Relationship> getRelationship(String userId) throws InstagramException {
		URIBuilder uri = createUriBuilder(String.format("https://api.instagram.com/v1/users/%s/relationship", urlEncode(userId)));
		return queryEntity(uri.toString(), Relationship.class);
	}

	@Override
	public Result<Media> getMedia(String mediaId) throws InstagramException {
		URIBuilder uri = createUriBuilder("https://api.instagram.com/v1/media/" + urlEncode(mediaId));
		return queryEntity(uri.toString(), Media.class);
	}

	@Override
	public Result<Media[]> getRecentMediaForUser(String userId, Parameter...params) throws InstagramException {
		URIBuilder uri = createUriBuilder(String.format("https://api.instagram.com/v1/users/%s/media/recent", urlEncode(userId)), params);
		return queryEntities(uri.toString(), Media.class);
	}

	@Override
	public Result<Media[]> getUserFeed(Parameter... params)	throws InstagramException {
		URIBuilder uri = createUriBuilder("https://api.instagram.com/v1/users/self/feed", params);
		return queryEntities(uri.toString(), Media.class);
	}

	@Override
	public Result<Media[]> getRecentMediaForTag(String tag, Parameter...params) throws InstagramException {
		URIBuilder uri = createUriBuilder(String.format("https://api.instagram.com/v1/tags/%s/media/recent", urlEncode(tag)), params);
		return queryEntities(uri.toString(), Media.class);
	}

	@Override
	public Result<Media[]> getRecentMediaAtLocation(String locationId, Parameter...params) throws InstagramException {
		URIBuilder uri = createUriBuilder(String.format("https://api.instagram.com/v1/locations/%s/media/recent", urlEncode(locationId)), params);
		return queryEntities(uri.toString(), Media.class);
	}

	@Override
	public Result<Media[]> getRecentMediaNearby(double latitude, double longitude, Integer radiusMeters, Parameter...params)
			throws InstagramException {
		URIBuilder uri = createUriBuilder("https://api.instagram.com/v1/media/search", params);
		uri.addParameter("lat", String.format("%.5f", latitude));
		uri.addParameter("lng", String.format("%.5f", longitude));
		if (radiusMeters != null && radiusMeters > 0)
			uri.addParameter("distance", radiusMeters.toString());
		return queryEntities(uri.toString(), Media.class);
	}

	@Override
	public Result<Media[]> getMediaNext(Pagination pagination) throws InstagramException {
		return queryEntities(pagination.getNextUrl(), Media.class);
	}

	@Override
	public Result<Location> getLocation(String locationId) throws InstagramException {
		URIBuilder uri = createUriBuilder("https://api.instagram.com/v1/locations/" + urlEncode(locationId));
		return queryEntity(uri.toString(), Location.class);
	}

	@Override
	public Result<Location[]> getLocationsNearby(double latitude, double longitude, Integer radiusKm, Parameter...params) throws InstagramException {
		URIBuilder uri = createUriBuilder("https://api.instagram.com/v1/locations/search", params);
		uri.addParameter("lat", String.format("%.5f", latitude));
		uri.addParameter("lng", String.format("%.5f", longitude));
		if (radiusKm != null && radiusKm > 0)
			uri.addParameter("distance", radiusKm.toString());
		return queryEntities(uri.toString(), Location.class);
	}

	@Override
	public Result<Location[]> getLocationsForFoursquareVenue(String venueId, Parameter...params) throws InstagramException {
		URIBuilder uri = createUriBuilder("https://api.instagram.com/v1/locations/search", params);
		uri.addParameter("foursquare_v2_id", venueId);	// Assume v2 id.

		return queryEntities(uri.toString(), Location.class);
	}

	@Override
	public Result<Location[]> getLocationsNext(Pagination pagination) throws InstagramException {
		return queryEntities(pagination.getNextUrl(), Location.class);
	}

	@Override
	public void addComment(String mediaId, String comment) throws InstagramException {
		if (mediaId == null)
			throw new IllegalArgumentException("Media ID is required");
		
		HttpPost post = new HttpPost(String.format("https://api.instagram.com/v1/media/%s/comments", urlEncode(mediaId)));
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("text", comment));
		params.add(new BasicNameValuePair("access_token", accessToken));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(params));
		} 
		catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unexpected error building Instagram request", e);
		}
		
		Result<?> result = requestEntity(post, null);
		if (result != null && result.getMeta() != null && !result.getMeta().isSuccess())
        	throw createInstagramException("Error adding comment to photo", post.getURI().toString(), null, result.getMeta(), null);
	}

	@Override
	public void deleteComment(String mediaId, String commentId)	throws InstagramException {
		if (mediaId == null || commentId == null)
			throw new IllegalArgumentException("Media ID and Comment ID are required");

		HttpDelete delete = new HttpDelete(String.format("https://api.instagram.com/v1/media/%s/comments/%s?access_token=%s", 
				mediaId, commentId, accessToken));

		Result<?> result = requestEntity(delete, null);
		if (result != null && result.getMeta() != null && !result.getMeta().isSuccess())
        	throw createInstagramException("Error deleting comment from photo", delete.getURI().toString(), null, result.getMeta(), null);
	}

	@Override
	public void likeMedia(String mediaId) throws InstagramException {
		if (mediaId == null)
			throw new IllegalArgumentException("Media ID is required");
		
		HttpPost post = new HttpPost(String.format("https://api.instagram.com/v1/media/%s/likes", mediaId));
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("access_token", accessToken));

		try {
			post.setEntity(new UrlEncodedFormEntity(params));
		} 
		catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unexpected error building Instagram request", e);
		}
		
		Result<?> result = requestEntity(post, null);
		if (result != null && result.getMeta() != null && !result.getMeta().isSuccess())
        	throw createInstagramException("Error liking photo", post.getURI().toString(), null, result.getMeta(), null);
	}

	@Override
	public void unlikeMedia(String mediaId) throws InstagramException {
		if (mediaId == null)
			throw new IllegalArgumentException("Media ID is required");
		
		HttpDelete delete = new HttpDelete(String.format("https://api.instagram.com/v1/media/%s/likes?access_token=%s", 
				mediaId, accessToken));

		Result<?> result = requestEntity(delete, null);
		if (result != null && result.getMeta() != null && !result.getMeta().isSuccess())
        	throw createInstagramException("Error unliking photo", delete.getURI().toString(), null, result.getMeta(), null);
	}

	@Override
	public Result<Relationship> relationshipAction(String userId, RelationshipAction action) throws InstagramException {
		if (userId == null || action == null)
			throw new IllegalArgumentException("User ID and action are required");
		
		HttpPost post = new HttpPost(String.format("https://api.instagram.com/v1/users/%s/relationship", userId));
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("action", action.getCommand()));
		params.add(new BasicNameValuePair("access_token", accessToken));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(params));
		} 
		catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unexpected error building Instagram request", e);
		}
		
		return requestEntity(post, Relationship.class);
	}
	
	@Override
	public Result<Media[]> getRecentMediaForGeoSubscription(String geoSubscriptionId) throws InstagramException {
		// https://api.instagram.com/v1/geographies/{geography id}/media/recent?client_id=CLIENT-ID
		URIBuilder uri = createUriBuilderNoAuth(String.format("https://api.instagram.com/v1/geographies/%s/media/recent", 
				urlEncode(geoSubscriptionId)), Parameter.as("client_id", clientId));
		return queryEntities(uri.toString(), Media.class);
	}

	@Override
	public Result<Subscription> createUserSubscription(String verifyToken, String callbackUrl) throws InstagramException {
		return createSubscription("user", null, "media", verifyToken, callbackUrl);
	}

	@Override
	public Result<Subscription> createTagSubscription(String tag,
			String verifyToken, String callbackUrl) throws InstagramException {
		return createSubscription("tag", tag, "media", verifyToken, callbackUrl);
	}

	@Override
	public Result<Subscription> createLocationSubscription(String locationId,
			String verifyToken, String callbackUrl) throws InstagramException {
		return createSubscription("location", locationId, "media", verifyToken, callbackUrl);
	}

	@Override
	public Result<Subscription> createGeoSubscription(double latitude, double longitude, int radius, String verifyToken, 
			String callbackUrl)	throws InstagramException {
		HttpPost post = new HttpPost("https://api.instagram.com/v1/subscriptions/");
		List<NameValuePair> params = new ArrayList<NameValuePair>(9);
		params.add(new BasicNameValuePair("client_id", clientId));
		params.add(new BasicNameValuePair("client_secret", clientSecret));
		params.add(new BasicNameValuePair("object", "geography"));
		params.add(new BasicNameValuePair("aspect", "media"));
		params.add(new BasicNameValuePair("lat", String.format("%.5f", latitude)));
		params.add(new BasicNameValuePair("lng", String.format("%.5f", longitude)));
		params.add(new BasicNameValuePair("radius", Integer.toString(radius)));
		params.add(new BasicNameValuePair("verify_token", verifyToken));
		params.add(new BasicNameValuePair("callback_url", callbackUrl));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(params));
		} 
		catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unexpected error building Instagram request", e);
		}
		
		return requestEntity(post, Subscription.class);
	}

	@Override
	public Result<Subscription[]> getSubscriptions() throws InstagramException {
		URIBuilder uri = createUriBuilderNoAuth("https://api.instagram.com/v1/subscriptions", 
				Parameter.as("client_id", clientId), Parameter.as("client_secret", clientSecret));
		return queryEntities(uri.toString(), Subscription.class);
	}

	@Override
	public void deleteSubscription(String subscriptionId) throws InstagramException {
		HttpDelete delete = new HttpDelete(String.format("https://api.instagram.com/v1/subscriptions?id=%s&client_id=%s&client_secret=%s", 
				subscriptionId, clientId, clientSecret));

		Result<?> result = requestEntity(delete, null);
		if (result != null && result.getMeta() != null && !result.getMeta().isSuccess())
        	throw createInstagramException("Error deleting subscription", delete.getURI().toString(), null, result.getMeta(), null);
	}

	@Override
	public void deleteUserSubscription() throws InstagramException {
		HttpDelete delete = new HttpDelete(String.format("https://api.instagram.com/v1/subscriptions?object=user&client_id=%s&client_secret=%s", 
				clientId, clientSecret));
		Result<?> result = requestEntity(delete, null);
		if (result != null && result.getMeta() != null && !result.getMeta().isSuccess())
        	throw createInstagramException("Error deleting user subscription", delete.getURI().toString(), null, result.getMeta(), null);
	}

	@Override
	public void deleteTagSubscriptions() throws InstagramException {
		HttpDelete delete = new HttpDelete(String.format("https://api.instagram.com/v1/subscriptions?object=tag&client_id=%s&client_secret=%s", 
				clientId, clientSecret));
		Result<?> result = requestEntity(delete, null);
		if (result != null && result.getMeta() != null && !result.getMeta().isSuccess())
        	throw createInstagramException("Error deleting tag subscriptions", delete.getURI().toString(), null, result.getMeta(), null);
	}

	@Override
	public void deleteLocationSubscriptions() throws InstagramException {
		HttpDelete delete = new HttpDelete(String.format("https://api.instagram.com/v1/subscriptions?object=location&client_id=%s&client_secret=%s", 
				clientId, clientSecret));
		Result<?> result = requestEntity(delete, null);
		if (result != null && result.getMeta() != null && !result.getMeta().isSuccess())
        	throw createInstagramException("Error deleting location subscription", delete.getURI().toString(), null, result.getMeta(), null);
	}

	@Override
	public void deleteGeoSubscriptions() throws InstagramException {
		HttpDelete delete = new HttpDelete(String.format("https://api.instagram.com/v1/subscriptions?object=geographytag&client_id=%s&client_secret=%s", 
				clientId, clientSecret));
		Result<?> result = requestEntity(delete, null);
		if (result != null && result.getMeta() != null && !result.getMeta().isSuccess())
        	throw createInstagramException("Error deleting geo subscription", delete.getURI().toString(), null, result.getMeta(), null);
	}

	@Override
	public void deleteAllSubscriptions() throws InstagramException {
		HttpDelete delete = new HttpDelete(String.format("https://api.instagram.com/v1/subscriptions?object=all&client_id=%s&client_secret=%s", 
				clientId, clientSecret));
		Result<?> result = requestEntity(delete, null);
		if (result != null && result.getMeta() != null && !result.getMeta().isSuccess())
        	throw createInstagramException("Error deleting subscriptions", delete.getURI().toString(), null, result.getMeta(), null);
	}

	private Result<Subscription> createSubscription(String object, String objectId, String aspect, 
			String verifyToken, String callbackUrl) throws InstagramException {
		HttpPost post = new HttpPost("https://api.instagram.com/v1/subscriptions/");
		List<NameValuePair> params = new ArrayList<NameValuePair>(7);
		params.add(new BasicNameValuePair("client_id", clientId));
		params.add(new BasicNameValuePair("client_secret", clientSecret));
		params.add(new BasicNameValuePair("object", object));
		if (objectId != null)
			params.add(new BasicNameValuePair("object_id", objectId));
		params.add(new BasicNameValuePair("aspect", aspect));
		params.add(new BasicNameValuePair("verify_token", verifyToken));
		params.add(new BasicNameValuePair("callback_url", callbackUrl));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(params));
		} 
		catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unexpected error building Instagram request", e);
		}
		
		return requestEntity(post, Subscription.class);
	}

	private <T> Result<T> queryEntity(String url, Class<T> type) throws InstagramException {
        HttpGet get = new HttpGet(url);
		return requestEntity(get, type);
	}
	
	private <T> Result<T> requestEntity(HttpRequestBase method, Class<T> type) throws InstagramException {
		method.getParams().setParameter("http.useragent", "Instagram4j/1.0");
        
		JsonParser jp = null;
		HttpResponse response = null;
		ResultMeta meta = null;

		try {
			method.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
	        
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(AllClientPNames.CONNECTION_TIMEOUT, 15000);
			client.getParams().setParameter(AllClientPNames.SO_TIMEOUT, 30000);
			
			if (LOG.isDebugEnabled())
				LOG.debug(String.format("Requesting entity entry point %s, method %s", method.getURI().toString(), method.getMethod()));
			
			autoThrottle();
			
            response = client.execute(method);
            
            jp = createParser(response, method);

			JsonToken tok = jp.nextToken();
			if (tok != JsonToken.START_OBJECT) {
		        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) 
		        	throw createInstagramException("Instagram request failed", method.getURI().toString(), response, null, null);
		        
	        	throw createInstagramException("Invalid response format from Instagram API", method.getURI().toString(), response, null, null);
			}

			T data = null;
			
			while (true) {
				tok = jp.nextValue();
				if (tok == JsonToken.START_ARRAY) {
		        	throw createInstagramException("Unexpected array in entity response " + jp.getCurrentName(), method.getURI().toString(), response, meta, null);
				}
				else if (tok == JsonToken.START_OBJECT) {
					// Should be "data" or "meta"
					String name = jp.getCurrentName();
					if ("meta".equals(name))
						meta = jp.readValueAs(ResultMeta.class);
					else if ("data".equals(name)) {
						if (type != null)
							data = jp.readValueAs(type);
						else
							jp.readValueAs(Map.class); // Consume & ignore
					}
					else
			        	throw createInstagramException("Unexpected field name " + name, method.getURI().toString(), response, meta, null);
				}
				else
					break;
			}
			
			if (data == null && meta == null && response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) 
	        	throw createInstagramException("Instagram request failed", method.getURI().toString(), response, null, null);
			
			Result<T> result = new Result<T>(null, meta, data);
			setRateLimits(response, result);
			
			return result;
		}
		catch (JsonParseException e) {
			throw createInstagramException("Error parsing response from Instagram: " + e.getMessage(), method.getURI().toString(), response, meta, e);
		} 
		catch (JsonProcessingException e) {
			throw createInstagramException("Error parsing response from Instagram: " + e.getMessage(), method.getURI().toString(), response, meta, e);
		} 
		catch (IOException e) {
			throw createInstagramException("Error communicating with Instagram: " + e.getMessage(), method.getURI().toString(), response, meta, e);
		}
		finally {
			if (jp != null)
				try { jp.close(); } catch (IOException e) {}
			method.releaseConnection();
		}
	}

	private <T> Result<T[]> queryEntities(String url, Class<T> type) throws InstagramException {
		HttpGet get = new HttpGet(url);
		return requestEntities(get, type);
	}

	@SuppressWarnings("unchecked")
	private <T> Result<T[]> requestEntities(HttpRequestBase method, Class<T> type) throws InstagramException {
		method.getParams().setParameter("http.useragent", "Instagram4j/1.0");
        
		JsonParser jp = null;
		HttpResponse response = null;
		ResultMeta meta = null;
		
		try {
			method.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
	        
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(AllClientPNames.CONNECTION_TIMEOUT, 15000);
			client.getParams().setParameter(AllClientPNames.SO_TIMEOUT, 30000);

			if (LOG.isDebugEnabled())
				LOG.debug(String.format("Requesting entities entry point %s, method %s", method.getURI().toString(), method.getMethod()));

			autoThrottle();
			
            response = client.execute(method);
            
            jp = createParser(response, method);

			JsonToken tok = jp.nextToken();
			if (tok != JsonToken.START_OBJECT) {
		        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) 
		        	throw createInstagramException("Instagram request failed", method.getURI().toString(), response, null, null);
		        
	        	throw createInstagramException("Invalid response format from Instagram API", method.getURI().toString(), response, null, null);
			}

			Pagination pagination = null;
			T[] data = null;
			
			while (true) {
				tok = jp.nextValue();
				if (tok == JsonToken.START_ARRAY) {
					// Should be "data"
					String name = jp.getCurrentName();
					if (!"data".equals(name))
			        	throw createInstagramException("Unexpected field name " + name, method.getURI().toString(), response, meta, null);

					List<T> items = new ArrayList<T>();

					tok = jp.nextToken();
					if (tok == JsonToken.START_OBJECT) {
						if (type != null) {
							T item;
							while ((item = jp.readValueAs(type)) != null) 
								items.add(item);
						}
						else 
							jp.readValueAs(Map.class); // Consume & ignore
					}
					
					data = (T[])Array.newInstance(type, items.size());
					System.arraycopy(items.toArray(), 0, data, 0, items.size());
				}
				else if (tok == JsonToken.START_OBJECT) {
					// Should be "pagination" or "meta"
					String name = jp.getCurrentName();
					if ("pagination".equals(name))
						pagination = jp.readValueAs(Pagination.class);
					else if ("meta".equals(name))
						meta = jp.readValueAs(ResultMeta.class);
					else
			        	throw createInstagramException("Unexpected field name " + name, method.getURI().toString(), response, meta, null);
				}
				else
					break;
			}
			
			if (data == null && meta == null && response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) 
	        	throw createInstagramException("Instagram request failed", method.getURI().toString(), response, null, null);
			
			Result<T[]> result = new Result<T[]>(pagination, meta, data);
			setRateLimits(response, result);
			
			return result;
		}
		catch (JsonParseException e) {
			throw createInstagramException("Error parsing response from Instagram: " + e.getMessage(), method.getURI().toString(), response, meta, e);
		} 
		catch (JsonProcessingException e) {
			throw createInstagramException("Error parsing response from Instagram: " + e.getMessage(), method.getURI().toString(), response, meta, e);
		} 
		catch (IOException e) {
			throw createInstagramException("Error communicating with Instagram: " + e.getMessage(), method.getURI().toString(), response, meta, e);
		}
		finally {
			if (jp != null)
				try { jp.close(); } catch (IOException e) {}
			method.releaseConnection();
		}
	}

	private URIBuilder createUriBuilder(String uri, Parameter...params) {
		URIBuilder bldr = createUriBuilderNoAuth(uri, params);
		addAuth(bldr);
		return bldr;
	}

	private URIBuilder createUriBuilderNoAuth(String uri, Parameter...params) {
		try {
			URIBuilder bldr = new URIBuilder(uri);
			addParams(bldr, params);
			return bldr;
		} 
		catch (URISyntaxException e) {
			throw new IllegalStateException("Unexpected error building URI for " + uri, e);
		}
	}
	
	private void setRateLimits(HttpResponse response, Result<?> result) {
        Header hdr = response.getFirstHeader("X-Ratelimit-Limit");
        if (hdr != null && hdr.getValue() != null)
        	result.setRateLimit(Integer.valueOf(hdr.getValue()));
        
        hdr = response.getFirstHeader("X-Ratelimit-Remaining");
        if (hdr != null && hdr.getValue() != null)
        	result.setRateLimitRemaining(Integer.valueOf(hdr.getValue()));
	}
	
	private String urlEncode(String s) {
		try {
			return ENCODER.encode(s);
		} 
		catch (EncoderException e) {
			throw new IllegalStateException("Unexpected error encoding URL value", e);
		}
	}
	
	private void addAuth(URIBuilder uri) {
		if (accessToken != null)
			uri.addParameter("access_token", accessToken);
		else 
			uri.addParameter("client_id", clientId);
	}
	
	private void addParams(URIBuilder uri, Parameter...params) {
		if (params != null && params.length != 0) {
			for (Parameter param : params)
				uri.addParameter(param.getName(), param.getValue());
		}
	}
	
	private JsonParser createParser(HttpResponse response, HttpRequestBase method) throws JsonParseException, IOException {
		HttpEntity entity = response.getEntity();
		// Always try to get response body since API will often return JSON for non-200 status codes.
		String responseBody = entity != null ? EntityUtils.toString(entity) : null;
        if (responseBody != null)
        	responseBody = responseBody.trim();
        
		if (responseBody == null || responseBody.length() == 0) {
            if (response.getStatusLine().getStatusCode() >= 300)
            	throw createInstagramException("Instagram request failed", method.getURI().toString(), response, null, null);
            
			throw new InstagramException("Received empty response from Instagram");
		}
		
		// Since sometimes the Instagram API responds with non-JSON (e.g. "Oops, an error occurred."), sniff here.
		if (!responseBody.startsWith("{") && !responseBody.endsWith("}")) {
            if (response.getStatusLine().getStatusCode() >= 300)
            	throw createInstagramException("Instagram request failed", method.getURI().toString(), response, null, null);
            
			throw new InstagramException("Received unexpected response from Instagram: " + (responseBody.length() > 100 ? responseBody.substring(0, 100) : responseBody));
		}

		return JSON_FACTORY.createJsonParser(responseBody);
	}
	
	private InstagramException createInstagramException(String msg, String url, HttpResponse response, ResultMeta meta, Throwable t) {
		InstagramException e = t != null ? new InstagramException(msg, t) : new InstagramException(msg);
		e.setUrl(url);
		e.setMeta(meta);
		
		if (response != null && response.getStatusLine() != null) {
			e.setStatusCode(response.getStatusLine().getStatusCode());
			e.setStatusMessage(response.getStatusLine().getReasonPhrase());
		}
		return e;
	}
	
	private boolean autoThrottle() {
		if (autoThrottle > 0) {
			long now = System.currentTimeMillis();
			long sinceLast = now - lastCallTicks; 
			if (sinceLast < autoThrottle) {
				try {
					Thread.sleep(autoThrottle - sinceLast);
					lastCallTicks = System.currentTimeMillis();
				}
				catch (InterruptedException e) {
					return false;
				}
			}
			else
				lastCallTicks = now;
		}
		
		return true;
	}
}
