package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class EventFactory {

  public static Event getEvent(byte[] marshalledBytes) throws IOException {
    int messageType = getMessageTypeFromByteArray(marshalledBytes);

    Event returnEvent;

    switch (messageType){
      case Protocol.OVERLAY_NODE_SENDS_REGISTRATION:
        returnEvent = new OverlayNodeSendsRegistration(marshalledBytes);
        break;
      case Protocol.REGISTRY_REPORTS_REGISTRATION_STATUS:
        returnEvent = new RegistryReportsRegistrationStatus(marshalledBytes);
        break;
      case Protocol.OVERLAY_NODE_SENDS_DEREGISTRATION:
        returnEvent = new OverlayNodeSendsDeregistration(marshalledBytes);
        break;
      case Protocol.REGISTRY_REPORTS_DEREGISTRATION_STATUS:
        returnEvent = new RegistryReportsDeregistrationStatus(marshalledBytes);
        break;
      case Protocol.REGISTRY_SENDS_NODE_MANIFEST:
        returnEvent = new RegistrySendsNodeManifest(marshalledBytes);
        break;
      default:
        throw new IllegalArgumentException("Invalid message type: " + messageType);
    }
    return returnEvent;
  }

  private static int getMessageTypeFromByteArray(byte[] marshalledBytes) throws IOException {
    ByteArrayInputStream baInputStream =
        new ByteArrayInputStream(marshalledBytes);
    DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));

    int messageType = din.readInt();

    baInputStream.close();
    din.close();
    return messageType;
  }

}
