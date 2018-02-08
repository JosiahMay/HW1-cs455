package cs455.overlay.util;

import cs455.overlay.node.MessagingNode;
import cs455.overlay.node.Node;


import cs455.overlay.node.Registry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InteractiveCommandParser extends Thread implements CommandProtocols {

  private Node parentNode;
  private String nodeType;

  public InteractiveCommandParser(Node baseNode) {
    super("CommandParser");
    this.parentNode = baseNode;

    if(parentNode instanceof Registry){
      nodeType = "Registry";
    }
    else if(parentNode instanceof MessagingNode){
      nodeType = "MessageNode";
    }
    else {
      throw new IllegalArgumentException("Unknown type of Node used "
          + "to construct InteractiveCommandParser");
    }

  }

  public void run() {
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    String fromUser;

    try {
      while (!Thread.currentThread().isInterrupted()) {
          if ((fromUser = stdIn.readLine()) != null){
            String[] userCommand = fromUser.split("\\s");
            sendCommand(userCommand);
            System.out.println(fromUser);
          }
      }


    } catch (IOException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e){
      System.out.println(e.getMessage());
    }
    System.out.println("Stopping thread Parser");

    }

  private void sendCommand(String[] userCommand) {
    InputCommands command = parseCommands(userCommand);
    if(command.command.equals(commandTypes.HELP)){
      printValidCommands();
    } else {
      parentNode.onCommand(command);
    }
  }

  private void printValidCommands() {
    System.out.println(commandsByNodeType());
  }

  private String commandsByNodeType() {
    if(nodeType.equals("Registry")){
      return validRegistryCommands();
    }
    else {
      return validMessageNodeCommands();
    }
  }

  private String validMessageNodeCommands() {
    return "The valid commands for a message node are:\n"
        + PRINT_COUNTERS_AND_DIAGNOSTICS + "\n"
        + EXIT_OVERLAY + "\n";
  }

  private String validRegistryCommands() {

    return "The valid commands for the registry are:\n" + LIST_MESSAGING_NODES
        + "\n" + SETUP_OVERLAY + " <number of routing table entries>\n"
        + LIST_ROUTING_TABLES + "\n" + START + " <number of messages to send>\n";
  }

  private InputCommands parseCommands(String[] userCommand) {

    checkForValidCommandSize(userCommand.length, 1);
    if(userCommand[0].equals("help")){
        return new InputCommands(commandTypes.HELP);
    }

    if(nodeType.equals("Registry")){
      return createRegistryMessage(userCommand);
    }
    else {
      return createNodeMessage(userCommand);
    }
  }

  private InputCommands createNodeMessage(String[] userCommand) {
    switch (userCommand[0]){
      case PRINT_COUNTERS_AND_DIAGNOSTICS:
        return new InputCommands(commandTypes.PRINT_COUNTERS_AND_DIAGNOSTICS);
      case EXIT_OVERLAY:
        return new InputCommands(commandTypes.EXIT_OVERLAY);
      default:
        throwInvaildCommand();
        return null;
    }
  }

  private InputCommands createRegistryMessage(String[] userCommand) {
    switch (userCommand[0]){
      case LIST_MESSAGING_NODES:
        return new InputCommands(commandTypes.LIST_MESSAGING_NODES);
      case START:
        checkForValidCommandSize(userCommand.length,2);
        return new InputCommands(commandTypes.START_MESSAGING, checkIfNumberIsValid(userCommand[1]));
      case SETUP_OVERLAY:
        checkForValidCommandSize(userCommand.length,2);
        return new InputCommands(commandTypes.SETUP_OVERLAY, checkIfNumberIsValid(userCommand[1]));
      case LIST_ROUTING_TABLES:
        return new InputCommands(commandTypes.LIST_ROUTING_TABLES);
      default:
        throwInvaildCommand();
        return null;


    }
  }


  private int checkIfNumberIsValid(String userCommand) {

    try {
      int number = Integer.parseInt(userCommand);
      if(number < 1){
        throwInvaildCommand();
      }
      return number;
    } catch (NumberFormatException e){
      throwInvaildCommand();
    }
    return -1;
  }

  private void checkForValidCommandSize(int length, int expectedSize) {
    if(length != expectedSize){
      throwInvaildCommand();
  }

}

  private void throwInvaildCommand() {
    throw new IllegalArgumentException("Invalid command type \"help\" for valid commands");
  }

}

