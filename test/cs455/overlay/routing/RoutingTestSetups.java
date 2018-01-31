package cs455.overlay.routing;

public interface RoutingTestSetups {

  int numOfEntries = 5;
  int idStart = 100;
  int portStart = 5000;
  String ipStart = "192.01.01.";


  static RoutingEntry[] populateEntries() {
    int id = idStart;
    int port = portStart;
    RoutingEntry[] rt = new RoutingEntry[numOfEntries];
    for (int i = 0; i < numOfEntries; i++) {
      rt[i] = new RoutingEntry(id++, ipStart + i, port++);
    }
    return rt;
  }

  static RoutingEntry populateEntry() {
    return new RoutingEntry(idStart,ipStart +1, portStart);
  }

  static String entryString() {
    return "Node Id: " + RoutingTestSetups.idStart
        + "\nIP Address: " + RoutingTestSetups.ipStart+1
        + "\nPort Number: " + RoutingTestSetups.portStart;
  }
}
