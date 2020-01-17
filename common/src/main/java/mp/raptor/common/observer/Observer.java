package mp.raptor.common.observer;

import mp.raptor.common.RegisterableComponent;

public interface Observer {

  ObserverType getType ();

  void update (RegisterableComponent c);

}
