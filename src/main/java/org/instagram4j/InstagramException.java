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

import java.io.IOException;

import org.apache.http.HttpStatus;

/**
 * Raised when an error occurs accessing the API or when processing 
 * the response from the API. If an actual response was received from 
 * the API, the {@link #getMeta() Meta} details will be available. 
 */
public class InstagramException extends IOException {
	private static final long serialVersionUID = 1L;
	private ResultMeta meta;
	private int statusCode;
	private String statusMessage;
	private String url;

	public InstagramException() {
	}

	public InstagramException(String message) {
		super(message);
	}

	public InstagramException(Throwable cause) {
		super(cause);
	}

	public InstagramException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Returns a descriptive message describing the exception. The meta information, if available, 
	 * is used as well as the HTTP status information.
	 * @return
	 */
	@Override
	public String getMessage() {
		if (meta != null && meta.getCode() > 200) {
			if (meta.getErrorMessage() != null && meta.getErrorType() != null)
				return String.format("%s (%s - %d)", meta.getErrorMessage(), meta.getErrorType(), meta.getCode());
			
			if (meta.getErrorMessage() != null)
				return String.format("%s (%d)", meta.getErrorMessage(), meta.getCode());
			
			if (meta.getErrorType() != null)
				return String.format("%s (%d)", meta.getErrorType(), meta.getCode());
			
			if (statusMessage != null)
				return String.format("%s (%d)", statusMessage, statusCode);
		}
		
		if (statusCode > 200) {
			String msg = statusMessage;
			if (msg == null) {
				switch(statusCode) {
				case HttpStatus.SC_BAD_REQUEST:
					msg = "Invalid request"; break;
				case HttpStatus.SC_UNAUTHORIZED:
					msg = "Authorization is invalid or expired. Verify your permissions on Instagram or try re-authorizing."; break;
				case HttpStatus.SC_FORBIDDEN:
					msg = "Authorization is invalid or expired. Try authorizing again."; break;
				case HttpStatus.SC_NOT_FOUND:
					msg = "Requested resource or enpoint not found."; break;
				case HttpStatus.SC_INTERNAL_SERVER_ERROR:
					msg = "Instagram server encountered an unexpected error processing request."; break;
				case HttpStatus.SC_SERVICE_UNAVAILABLE:
					msg = "Instagram servers are temporarily overloaded. Try again later."; break;
				case HttpStatus.SC_GATEWAY_TIMEOUT:
					msg = "Instagram servers are temporarily overloaded. Try again later."; break;
				case 429:
					msg = "You have exceeded the Instagram API rate limit. Try again later."; break;
				}
			}
			
			if (msg != null)
				return String.format("%s (%d)", msg, statusCode);
		}
		
		return super.getMessage();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(getClass().getName());
		s.append(": ").append(getLocalizedMessage());
		String deets = getDetails();
		if (deets.length() > 0)
			s.append(" [").append(deets).append("]");
		return s.toString();
	}

	/**
	 * If there are error details available either in the meta or HTTP status message, they 
	 * will be returned. Otherwise a blank String will be returned.
	 * @return
	 */
	public String getDetails() {
		if (meta != null) {
			if (meta.getCode() != 200) 
				return String.format("%s (%d): %s", meta.getErrorType(), meta.getCode(), meta.getErrorMessage());
		}
		else if (statusCode != 200)
			return String.format("%s (%d)", statusMessage, statusCode);
		
		return "";
	}
	
	/**
	 * HTTP status code returned by API
	 * @return
	 */
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * HTTP status message returned by API.
	 * @return
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/**
	 * Returns true if response indicates a rate limit has been exceeded.
	 * 
	 * @return true if API rate limit has been exceeded
	 * @see http://developers.instagram.com/post/87743402071/new-tools-and-rate-limits-for-post-endpoints
	 */
	public boolean isRateLimitExceeded() {
		if (getStatusCode() == 429)
			return true;
		
		if (getMeta() != null && getMeta().getCode() == 429)
			return true;
		
		return false;
	}

	/**
	 * Returns the URL used to access the API when the exception occurred. Useful for 
	 * debugging purposes--not intended for end-user display.
	 * 
	 * @return URL used to access the API when exception occurred
	 */
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Meta details returned in API response, if available.
	 * @return Meta details from response if available or null
	 */
	public ResultMeta getMeta() {
		return meta;
	}

	public void setMeta(ResultMeta meta) {
		this.meta = meta;
	}
}
