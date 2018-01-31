package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class EventFactory {


  public static Event getEvent(byte[] marshalledBytes) throws IOException {
    int messageType = getMessageTypeFromByteArray(marshalledBytes);

    switch (messageType){
      case Protocol.OVERLAY_NODE_SENDS_REGISTRATION:
        return new OverlayNodeSendsRegistration(marshalledBytes);
      case Protocol.REGISTRY_REPORTS_REGISTRATION_STATUS:
        return new RegistryReportsRegistrationStatus(marshalledBytes);
      case Protocol.OVERLAY_NODE_SENDS_DEREGISTRATION:
        return new OverlayNodeSendsDeregistration(marshalledBytes);
      case Protocol.REGISTRY_REPORTS_DEREGISTRATION_STATUS:
        return new RegistryReportsDeregistrationStatus(marshalledBytes);
      case Protocol.REGISTRY_SENDS_NODE_MANIFEST:
        return new RegistrySendsNodeManifest(marshalledBytes);
      case Protocol.NODE_REPORTS_OVERLAY_SETUP_STATUS:
        return new NodeReportsOverlaySetupStatus(marshalledBytes);
      case Protocol.REGISTRY_REQUESTS_TASK_INITIATE:
        return new RegistryRequestsTaskInitiate(marshalledBytes);
      default:
        throw new IllegalArgumentException("Invalid message type: " + messageType);
    }
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
