package io.codelair.raptormp.bootstrap;

import static java.lang.System.Logger.Level;

/**
 * Bootstraps the application server.
 *
 * @author Hassan Nazar, hassenasse @ github (2019)
 */
public class Bootstrap
{

  private static System.Logger LOGGER = System.getLogger( Bootstrap.class.getSimpleName() );

  public static void main( String[] args ) throws IllegalAccessException
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


    // Print bootstrap logo
    LOGGER.log( Level.INFO, logo );
    Throwable thrown = new IllegalAccessException( "CHECK THIS" );

    LOGGER.log( Level.INFO, "Sample log with throwable", thrown );
  }

}
