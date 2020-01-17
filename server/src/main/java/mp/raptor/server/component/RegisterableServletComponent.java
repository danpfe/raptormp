package mp.raptor.server.component;

import mp.raptor.common.RegisterableComponent;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class RegisterableServletComponent implements RegisterableComponent {
  @Override
  public String hello () {
    return "hello";
  }
}
