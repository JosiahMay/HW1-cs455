package cs455.overlay.util;

import static org.junit.Assert.*;

import cs455.overlay.util.CommandProtocols.commandTypes;
import org.junit.Test;

public class InputCommandsTest {

  @Test
  public void constructorTestNoNumber(){
    InputCommands test = new InputCommands(commandTypes.EXIT_OVERLAY);
    assertEquals(test.numberOfItems, -1);
  }

  @Test
  public void constructorTest(){
    InputCommands test = new InputCommands(commandTypes.SETUP_OVERLAY, 4);
    assertEquals(test.numberOfItems, 4);
    assertEquals(test.command, commandTypes.SETUP_OVERLAY);
  }
}