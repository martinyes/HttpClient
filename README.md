A lightweight and easy to use HTTP Client.

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
* [x] Params and Headers Factory class
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

## Core classes
   - **_HttpRequest_** – represents the request to be sent via the **_HttpClient_**
   - **_HttpClient_** – represents a container, used to send multiple requests and set up its configuration
   - **_HttpResponse_** – represents the result of an **_HttpRequest_** call

## Usage

## Performance

## Installation
