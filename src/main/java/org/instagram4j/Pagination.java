package org.instagram4j;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pagination {
	@JsonProperty("next_url")
	private String nextUrl;
	
	@JsonProperty("next_max_id")
	private String nextMaxId;
	
	@JsonProperty("next_min_id")
	private String nextMinId;
	
	@JsonProperty("deprecation_warning")
	private String deprecationWarning;

	// Specific to tag searching.
	@JsonProperty("min_tag_id")
	private String minTagId;
	
	@JsonProperty("next_max_tag_id")
	private String nextMaxTagId;
	
	// Specific to user relationship paging
	@JsonProperty("next_cursor")
	private String nextCursor;

	public boolean isHasMore() {
		return nextUrl != null && nextUrl.length() > 0;
	}
	
	public String getNextUrl() {
		return nextUrl;
	}

	public String getNextMaxId() {
		return nextMaxId;
	}

	public String getNextMinId() {
		return nextMinId;
	}

	public String getDeprecationWarning() {
		return deprecationWarning;
	}

	public String getMinTagId() {
		return minTagId;
	}

	public String getNextMaxTagId() {
		return nextMaxTagId;
	}

	public String getNextCursor() {
		return nextCursor;
	}
}
