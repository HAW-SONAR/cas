package businessLogicLayer;

import java.util.Random;

/**
 * Created by Daniel Hofmeister on 16.01.2016.
 */
public class Testmain {

  public static void main(String [ ] args) {
    Random random = new Random();
    int i;
    for (i=0; i<10; i++) {
      System.out.println(random.nextInt(1));
    }
  }

}
