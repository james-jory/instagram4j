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
