package cs455.overlay.node;

import cs455.overlay.transport.TCPConnectionsCache;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.util.InputCommands;
import cs455.overlay.util.InteractiveCommandParser;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import java.io.IOException;
import java.net.Socket;

public abstract class Node {

  public static final int MAX_NODES = 127;
  protected TCPConnectionsCache connectionsCache;

  protected InteractiveCommandParser commandLineParser;
  protected TCPServerThread serverThread;

  public Node(){
    EventFactory.setCommandNode(this);
  }

  public abstract void onEvent(Event event);
  public abstract void onEvent(Event event, Socket socket);
  public abstract void onCommand(InputCommands command) throws IOException;


  protected void setupCommandLineParser(){
    System.out.println("Starting parser");
    commandLineParser = new InteractiveCommandParser(this);
    commandLineParser.start();
  }

  public void setTcpConnectionsCache(TCPConnectionsCache tcpConnectionsCache){
    this.connectionsCache = tcpConnectionsCache;
  }

  protected void stopCommandLineParser(){
    commandLineParser.interrupt();
  }
  protected void stopServerThread() {
    connectionsCache.interruptAll();

  }



}
