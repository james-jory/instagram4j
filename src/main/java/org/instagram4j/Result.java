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