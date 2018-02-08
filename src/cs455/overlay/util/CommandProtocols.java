package cs455.overlay.util;

public interface CommandProtocols {

  String LIST_MESSAGING_NODES = "list-messaging-nodes";
  String SETUP_OVERLAY = "setup-overlay";
  String LIST_ROUTING_TABLES = "list-routing-tables";
  String START = "start";
  String PRINT_COUNTERS_AND_DIAGNOSTICS = "print-counters-and-diagnostics";
  String EXIT_OVERLAY = "exit-overlay";
  String HELP = "help";

  enum commandTypes {LIST_MESSAGING_NODES, SETUP_OVERLAY, LIST_ROUTING_TABLES,
    START_MESSAGING, PRINT_COUNTERS_AND_DIAGNOSTICS, EXIT_OVERLAY, HELP}

}
