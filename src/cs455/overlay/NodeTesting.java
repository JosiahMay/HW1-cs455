package cs455.overlay;

import cs455.overlay.node.MessagingNode;
import cs455.overlay.node.Registry;

public class NodeTesting {

  public static void main(String[] args){

    Registry.main(new String[]{"8541"});
    MessagingNode.main(new String[]{"129.82.44.169", "8541"});

  }

}
