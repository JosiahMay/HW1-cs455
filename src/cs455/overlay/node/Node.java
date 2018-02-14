package cs455.overlay.node;

import cs455.overlay.util.InputCommands;
import cs455.overlay.wireformats.Event;

public interface Node {

  int MAX_NODES = 127;

  void onEvent(Event event);

  void onCommand(InputCommands command);

}
