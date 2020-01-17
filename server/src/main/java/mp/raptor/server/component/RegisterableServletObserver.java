package mp.raptor.server.component;

import mp.raptor.common.RegisterableComponent;
import mp.raptor.common.observer.Observer;
import mp.raptor.common.observer.ObserverType;
import mp.raptor.server.UndertowServletContainer;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class RegisterableServletObserver implements Observer {

  private final UndertowServletContainer servletContainer;

  public RegisterableServletObserver (final UndertowServletContainer container) {
    this.servletContainer = container;
  }

  @Override
  public ObserverType getType () {
    return ObserverType.SERVLET;
  }

  @Override
  public void update (final RegisterableComponent component) {
    servletContainer.registerComponent(component);
  }
}
