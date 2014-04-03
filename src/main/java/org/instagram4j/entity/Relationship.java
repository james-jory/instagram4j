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
public class Relationship {
	@JsonProperty("outgoing_status")
	private String outgoingStatus;
	@JsonProperty("incoming_status")
	private String incomingStatus;
	
	@JsonProperty("target_user_is_private")
	private boolean targetUserIsPrivate;

	public String getOutgoingStatus() {
		return outgoingStatus;
	}

	public String getIncomingStatus() {
		return incomingStatus;
	}

	public boolean isTargetUserIsPrivate() {
		return targetUserIsPrivate;
	}
}
