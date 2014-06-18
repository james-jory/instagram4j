# instagram4j

Java driver for the [Instagram API](http://instagram.com/developer/). Supports the entire API, including photo/video entities,
posting likes/comments, relationship management, [real-time](http://instagram.com/developer/realtime/) subscription management, 
access to rate limit header information, support for [signed header](http://instagram.com/developer/restrict-api-requests/), and much more.

To use the driver download a release JAR or clone and build locally using Maven. The only dependencies are the Apache 
Commons HTTP client and the Jackson JSON parser.

## Examples
Most Instagram API endpoints require an OAuth 2.0 access token while others support userless access. 
For details on how to obtain an OAuth 2.0 access token, see the [Authentication](http://instagram.com/developer/authentication/) page.

Below are just a few examples of how to use the driver to access the API.

### Single Media Item

```
try {
	InstagramClient client = new DefaultInstagramClient(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET, AN_ACCESS_TOKEN);

	Result<Media> result = client.getMedia(A_MEDIA_ID);
	Media media = result.getData();
	
	// Do something with the media item
}
catch (InstagramException e) {
	// Deal with it.
}
``` 

### Pagination

```
try {
	InstagramClient client = new DefaultInstagramClient(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET, AN_ACCESS_TOKEN);

	// The only required argument for this endpoint is the user ID. However, additional parameters 
	// can specified using the Parameter class as shown here for "count".
	Result<Media[]> result = client = client.getRecentMediaForUser(A_USER_ID, Parameter.as("count", 10));
	
	while (result.getMeta().isSuccess() && result.getData().length > 0) {
		for (Media media : result.getData()) {
			// Do something with the media item
		}
		
		result = client.getMediaNext(result.getPagination());
	}
}
catch (InstagramException e) {
	// Deal with it.
}
``` 

### Signed Requests

Enabling the [signed header](http://instagram.com/developer/restrict-api-requests/) in your API calls enhances the 
security of your calls as well as [increases the rate limits](http://developers.instagram.com/post/87743402071/new-tools-and-rate-limits-for-post-endpoints) 
of specific endpoints.

To use signed requests you must enable them for your client ID in the [Instagram Developer portal](http://instagram.com/developer/clients/manage/) 
and tell the driver to use the signed header. 

```
InstagramClient client = new DefaultInstagramClient(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET, AN_ACCESS_TOKEN);
client.setSignedHeaderEnabled(true);
// Set to your actual client IP(s)... using loopback for testing.
client.setClientIps("127.0.0.1");
``` 

## License

Copyright 2014 James Jory

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
limitations under the License.