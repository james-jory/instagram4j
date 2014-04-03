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

public class ResultMeta {
	private int code;
	@JsonProperty("error_type")
	private String errorType;
	@JsonProperty("error_message")
	private String errorMessage;
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (errorMessage != null || errorType != null) {
			s.append(errorMessage);
			if (errorType != null)
				s.append(" (").append(errorType).append(")");
			
			if (code > 0) 
				s.append(" (").append(code).append(")");
		}
		else if (code > 0)
			s.append("HTTP ").append(code);
		else
			s.append("[no meta info available]");
		return s.toString();
	}

	public int getCode() {
		return code;
	}
	
	public boolean isSuccess() {
		return code == 200;
	}

	public String getErrorType() {
		return errorType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
