package mp.raptor.server.component;

import mp.raptor.common.RegisterableComponent;
import mp.raptor.common.observer.Observer;
import mp.raptor.common.observer.ObserverType;
import mp.raptor.common.observer.Subject;

import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.lang.System.Logger.Level.DEBUG;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class RegisterableComponentNotifier implements Subject {
  private static final Logger LOGGER = System.getLogger(RegisterableComponentNotifier.class.getSimpleName());

  private final Map<ObserverType, ArrayList<Observer>> observers;
  private final RegisterableComponentStore store;

  public RegisterableComponentNotifier () {
    this.observers = new HashMap<>();
    this.store = new RegisterableComponentStore();
  }

  @Override
  public void register (final Observer observer) {
    LOGGER.log(DEBUG, format("Registering observer: %s to notification queue", observer.getType()));
    var observersOfType = observers.get(observer.getType());
    if (observersOfType == null) {
      observersOfType = new ArrayList<>();
    }
    observersOfType.add(observer);
    observers.put(observer.getType(), observersOfType);
  }

  @Override
  public void unregister (final Observer observer) {
    final var observersOfType = observers.get(observer.getType());
    final var observerIndex = observersOfType.indexOf(observer);
    LOGGER.log(DEBUG, format("Unregistering observer: %s from notification queue", observer.getType()));
    observersOfType.remove(observerIndex);
    observers.put(observer.getType(), observersOfType);
  }

  @Override
  public void notifyObservers (final ObserverType type) {
    LOGGER.log(DEBUG, format("Notifying all observers of type: %s", type));
    for (final Observer observer : observers.get(type)) {
      for (final RegisterableComponent component : store.getStoreOfType(type)) {
        observer.update(component);
      }
    }
  }

  public RegisterableComponentStore getStore () {
    return store;
  }
}
