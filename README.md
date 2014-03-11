## instagram4j

Java driver for the [Instagram API](http://instagram.com/developer/).

## Example
Most Instagram API endpoints require an OAuth 2.0 access token while others support userless access. 
For details on how to obtain an OAuth 2.0 access token, see the [Authentication](http://instagram.com/developer/authentication/) page.

```
try {
	InstagramClient client = new DefaultInstagramClient(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET, AN_ACCESS_TOKEN);

	Result<Media> result = client.getMedia(MEDIA_ID);
	Media media = result.getData();
	
	// Do something with the media item
}
catch (InstagramException e) {
	// Deal with it.
}
``` 

## License

Copyright 2014 James Jory

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
limitations under the License.