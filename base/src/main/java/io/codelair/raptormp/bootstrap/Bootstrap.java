package io.codelair.raptormp.bootstrap;

import io.codelair.raptormp.logs.LogConfigurator;

import java.util.logging.Logger;

/**
 * Bootstraps the application server.
 *
 * @author Hassan Nazar, hassenasse @ github (2019)
 */
public class Bootstrap
{

  private static Logger log;

  public static void main( String[] args )
  {
    String logo =
            "\n" +
            "╔═══╗─────╔╗─────╔═╗╔═╦═══╗\n" +
            "║╔═╗║────╔╝╚╗────║║╚╝║║╔═╗║\n" +
            "║╚═╝╠══╦═╩╗╔╬══╦═╣╔╗╔╗║╚═╝║\n" +
            "║╔╗╔╣╔╗║╔╗║║║╔╗║╔╣║║║║║╔══╝\n" +
            "║║║╚╣╔╗║╚╝║╚╣╚╝║║║║║║║║║\n" +
            "╚╝╚═╩╝╚╣╔═╩═╩══╩╝╚╝╚╝╚╩╝\n" +
            "───────║║\n" +
            "───────╚╝\n" +
            "A fast, light and customizable Eclipse MicroProfile™-middleware.";

    // Initialize a base logger
    LogConfigurator.init();

    // Create a main class logger
    log = Logger.getLogger( Bootstrap.class.getSimpleName() );

    // Print bootstrap logo
      log.info( logo );

  }

}
