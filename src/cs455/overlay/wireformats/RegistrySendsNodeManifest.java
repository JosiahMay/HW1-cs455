package cs455.overlay.wireformats;

public class RegistrySendsNodeManifest extends Event implements Protocol {

  public RegistrySendsNodeManifest(){
    super(REGISTRY_SENDS_NODE_MANIFEST);
  }

}
