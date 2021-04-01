A lightweight and easy to use HTTP Client.

**_This project has created solely for learning._**

## Supported Protocols

- HTTP/1.0
- ~~HTTP/1.1~~
- ~~HTTP/2~~

## Features

* [x] Well-documented
* [x] Support for using multiple connection scheme
* [x] Both Sync (blocking) and Async (non-blocking) API
    - Using CompletableFuture for the async API
* [ ] Support for HTTP/2 Protocol
* [x] Headers Factory class
* [x] Numerous response body types
* [ ] Manipulation of cookies
* [ ] Support for HTTP authentication protocols
    - HTTP Basic
    - Digest
* [ ] Compression
* [x] Configuration options
    - Connection, read timeout
    - Authentication settings
    - Redirect policy
    - Custom Executor
* [x] Integrated HTTP server for JUnit tests

## Core classes

- **_HttpRequest_** – represents the request to be sent via the **_HttpClient_**
- **_HttpClient_** – represents a container, used to send multiple requests and set up its configuration
- **_HttpResponse_** – represents the result of an **_HttpRequest_** call

## Usage

## Performance

## Installation

## Dependencies

- Guava 30.1
- Lombok 1.18.16
- Javalin 3.13.4