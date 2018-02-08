package mechanisms;

import java.util.Random;
import tools.RPGDLException;

/**
 * 3D6
 * @author Nicolas Brax
 */
public class P13Mechanisms implements GameMechanisms {
  
  /**
   * 
   */
  private int diceOne;
  
  /**
   * 
   */
  private int diceTwo;
  
  /**
   * 
   */
  private int diceThree;
  
  /**
   * 
   * @param exp
   * @return
   * @throws RPGDLException 
   */
  @Override
  public String launchDices(String exp) throws RPGDLException{
    String toReturn = "";
    Random rand = new Random();
    this.diceOne = rand.nextInt(6) + 1;
    this.diceTwo = rand.nextInt(6) + 1;
    this.diceThree = rand.nextInt(6) + 1;
    String[] parts = exp.split("--threshold ");
    if(parts.length != 0){
      int success = 0;
      if(this.diceOne > Integer.parseInt(parts[0])){success++;}
      if(this.diceTwo > Integer.parseInt(parts[0])){success++;}
      if(this.diceThree > Integer.parseInt(parts[0])){success++;}
      toReturn += "{" + this.diceOne + "," + this.diceTwo + "," 
              + this.diceThree + "} --result "
               + success + "hit" + (success > 1 ? "s" : "") +
               ((success == 3)                                                       // if three hits
               ? ((this.diceOne == this.diceTwo && this.diceOne == this.diceThree)   //   if three times the same value
                  ? "--critical-success" : "--good-success")                         //     critical success else good-success
               : ((success == 0)                                                     // else if zero hit
                 ? ((this.diceOne == this.diceTwo && this.diceOne == this.diceThree) //   if three times the same value
                    ? "--critical-failure" : "--bad-failure")                        //     critical failure else bad-failure
                 : (success == 2) ? "success" : "failure"                            // else classic success or failure
                 )                                                                   // endif success == 0
              );                                                                     // endif success == 3
    }
    return toReturn;
  }
    
  /**
   * 
   * @return 
   */
  @Override
  public String mechanismQuickDescription(){
    return "In Patient 13, three six faces dices are launched. The results are "
            + "then compared to a threshold at the discretion of the GM, although "
            + "it is usually 4. Two dices must above the threshold for the action "
            + "to succeed. Otherwise the action is a failure. There are some "
            + "specific effects based on the results: \r\n"
            + " - three hits above: +1 to the involved traits;\r\n"
            + " - three same hits above (critical success): +3 to the involved traits;\r\n"
            + " - three hits below: -1 to the involved traits;\r\n"
            + " - three same hits below (critical failure): -3 to the involved traits;\r\n"
            + "";
  }
  
}
