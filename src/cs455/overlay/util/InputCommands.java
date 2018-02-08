package cs455.overlay.util;



public class InputCommands implements CommandProtocols{

  public final commandTypes command;
  public final int numberOfItems;

  public InputCommands(commandTypes command){
    this(command, -1);
  }
  public InputCommands(commandTypes command, int numberOfItems){
    this.command = command;
    this.numberOfItems = numberOfItems;
  }

}
