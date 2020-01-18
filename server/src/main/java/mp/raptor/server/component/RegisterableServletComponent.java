package mp.raptor.server.component;

import io.undertow.servlet.api.ServletInfo;
import mp.raptor.common.RegisterableComponent;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class RegisterableServletComponent implements RegisterableComponent<ServletInfo> {
  private ServletInfo servletInfo;

  @Override
  public ServletInfo getComponent () {
    return servletInfo;
  }

  @Override
  public void setComponent (final ServletInfo servletInfo) {
    this.servletInfo = servletInfo;
  }
}
