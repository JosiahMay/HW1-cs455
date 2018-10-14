package cs455.overlay.node;

import java.util.ArrayList;
import java.util.Collections;


public class RandomNodeID {

  private static ArrayList<Integer> randomNumbers = new ArrayList<>();

  public synchronized static void seedArray(int size){
    randomNumbers = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      randomNumbers.add(i);
    }
    Collections.shuffle(randomNumbers);
    randomNumbers.trimToSize();
  }

  public synchronized static int getIdNumber(){
    return randomNumbers.remove(0);
  }

  public synchronized static void returnIdNumber(int num){
    randomNumbers.add(num);
  }
}
