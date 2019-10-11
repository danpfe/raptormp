module mp.raptor.server {
  requires mp.raptor.common;
  requires undertow.core;
  requires jdk.unsupported; // TODO we need to deal with this, but Undertow needs it right now

  exports mp.raptor.server;
}
