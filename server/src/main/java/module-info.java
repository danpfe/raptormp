module mp.raptor.server {
  requires mp.raptor.common;
  requires io.github.classgraph;
  requires undertow.core;
  requires undertow.servlet;
  requires jdk.unsupported; // TODO we need to deal with this, but Undertow needs it right now
  requires jandex;
  requires beta.jboss.servlet.api_4_0;

  exports mp.raptor.server;
  exports mp.raptor.server.parser;
  exports mp.raptor.server.component;
}
