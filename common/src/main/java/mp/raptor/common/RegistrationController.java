package mp.raptor.common;

import java.util.List;

/**
 * Manages the registration and pool-storage of registerable components of
 * type T.
 *
 * @param <T> Type of component that is registerable.
 */
public interface RegistrationController<T> {
  /**
   * Load components that can be registered to an application container.
   */
  void registerComponents();

  /**
   * Return all registerable Components to the caller.
   *
   * @return components.
   */
  List<RegisterableComponent<T>> getRegisterables();
}
