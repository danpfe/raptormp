import io.codelair.raptormp.logs.LoggerFinder;

module raptormp.common {
  provides java.lang.System.LoggerFinder
      with LoggerFinder;
  exports io.codelair.raptormp.logs;
}
