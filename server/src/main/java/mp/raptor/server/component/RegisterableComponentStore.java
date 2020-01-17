package mp.raptor.server.component;

import mp.raptor.common.RegisterableComponent;
import mp.raptor.common.observer.ObserverType;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class RegisterableComponentStore {
  private final List<RegisterableComponent> servletComponents;

  public RegisterableComponentStore () {
    this.servletComponents = new ArrayList<>();
  }

  public List<RegisterableComponent> getStoreOfType (final ObserverType observerType) {
    switch (observerType) {
      case CONFIG:
        return null;
      case SERVLET:
        return this.servletComponents;
    }
    return null;
  }

}
