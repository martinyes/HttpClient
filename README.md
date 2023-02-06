An easy-to-use HTTP client and wrapper for all your favourite HTTP libraries.

## Implemented schemes and libraries
- Raw sockets (`SocketScheme.class`)
- Java URLConnection (`UrlConnectionScheme.class`)
- OkHttp (`OkHttpScheme.class`)
- Apache HttpClient (`ApacheScheme.class`)
- Jetty HttpClient (`JettyScheme.class`)

## Features

* [x] Well-documented code
* [x] Both Sync (blocking) and Async (non-blocking) API available
* [x] Headers Factory class for easy header creation
* [ ] Body manipulation on sending requests (form-data, multipart, etc)
* [x] Numerous response body types available
    - String (default)
    - Byte array (binary)
    - Json Object (json)
* [ ] Manipulation of cookies (set, get, delete)
* [ ] Support for HTTP authentication protocols
    - HTTP Basic Auth
    - Digest Auth
    - NTLM Auth
* [ ] Compression support
    - Gzip
    - Deflate
* [x] Configuration options for server, timeout, etc
    - Connection, read timeout
    - Redirect policy
    - Custom Executor
* [x] Integrated HTTP echo server for JUnit tests

## Core classes

- **_HttpRequest_** – represents the request to be sent via the **_HttpContainer_** class.
- **_HttpContainer_** – represents a container, used to send multiple requests and set up its configuration options.
- **_HttpResponse_** – represents the result of an **_HttpRequest_** call and contains the response body and headers.

## Usage
TODO

## Performance
TODO

## Installation
TODO

**_This project has created solely for learning._**