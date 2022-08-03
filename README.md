A lightweight and easy to use HTTP Client.

## Supported Protocols

- Socket scheme:
  - HTTP/1.0
  - ~~HTTP/1.1~~
  - ~~HTTP/2~~
- Http(s)URLConnection scheme:
  - HTTP/1.0
  - HTTP/1.1
  - HTTP/2

## Features

* [x] Well-documented
* [x] Support for using multiple connection scheme
    - Socket
    - Http(s)URLConnection
* [x] Both Sync (blocking) and Async (non-blocking) API
* [ ] Support for HTTP/2 Protocol
* [x] Headers Factory class
* [ ] Body manipulation on sending requests
* [x] Numerous response body types
    - String
    - Byte array
    - Json Object
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
* [x] Integrated HTTP echo server for JUnit tests

## Core classes

- **_HttpRequest_** – represents the request to be sent via the **_HttpClient_**
- **_HttpContainer_** – represents a container, used to send multiple requests and set up its configuration
- **_HttpResponse_** – represents the result of an **_HttpRequest_** call

## Usage
TODO

## Performance
TODO

## Installation
TODO

**_This project has created solely for learning._**