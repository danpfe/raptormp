# RaptorMP

A fast, light and customizable Eclipse MicroProfile™-middleware.

## Goals

Every project should have some goals, so let's reach for the stars. 😉

* Extension-based - if you only need a REST-API then only the JAX-RS and the bootstrapper should be in your final JAR.
* Implements all MicroProfile specs and passes the TCK.
* Pure Java, no trickery which makes it "similar" to Java, but not quite.
* Nothing custom, only open standards and its implementations (preferably RIs).
* Have a Maven-plugin that helps to AOT/CDS/Jlink an entire project into a fast-to-start executable.
* Attempt to be Jakarta EE-compatible so users can add their own frameworks to be a more complete middleware (JNDI/JPA/JTA/EJBLite?)
* Support the latest Java LTS (i.e. 11) but preferably also the tween-versions (12,13,14...). Java 8, 9 or 10 are no priority, though.
* Be amongst the fastest MicroProfile-implementations (time to first response, tps).

## Suggested libs

* Undertow for HTTP/Servlet 4.x
* SmallRye for MicroProfile
* Weld for CDI 2
* Jersey for JAX-RS and RESTClient

## License

Copyright 2019 RedBridge Technology AB

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
