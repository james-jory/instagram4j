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

public class Result<T> {
	private final Pagination pagination;
	private final ResultMeta meta;
	private final T data;

	/**
	 * From X-Ratelimit-Limit response header. 
	 */
	private Integer rateLimit;
	
	/**
	 * From X-Ratelimit-Remaining response header.
	 */
	private Integer rateLimitRemaining;
	
	public Result(Pagination pagination, ResultMeta meta, T data) {
		this.pagination = pagination;
		this.meta = meta;
		this.data = data;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public ResultMeta getMeta() {
		return meta;
	}

	public T getData() {
		return data;
	}

	public Integer getRateLimit() {
		return rateLimit;
	}

	void setRateLimit(Integer rateLimit) {
		this.rateLimit = rateLimit;
	}

	public Integer getRateLimitRemaining() {
		return rateLimitRemaining;
	}

	void setRateLimitRemaining(Integer rateLimitRemaining) {
		this.rateLimitRemaining = rateLimitRemaining;
	}
}