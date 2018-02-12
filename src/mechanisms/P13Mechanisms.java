package mechanisms;

import java.util.Random;
import tools.RPGDLException;

/**
 * This class handles die mechanism for the RPG called Patient 13. In this game
 * 3 six face dices are launched for each test. To succeed, two or more dice 
 * must exceed a threshold, usually 4.
 * 
 * @author Nicolas Brax
 */
public class P13Mechanisms implements GameMechanisms {
  
  /**
   * First launched die.
   */
  private int diceOne;
  
  /**
   * Second launched die.
   */
  private int diceTwo;
  
  /**
   * Third launched die.
   */
  private int diceThree;
  
  /**
   * This method is an indicator to the fact that the mechanism is static or not, 
   * meaning that each test is resolved using the same launch or that each
   * launch can use different dice according to contexts. In Patient 13, it is 
   * always the same, 3D6.
   * 
   * @return True since for Patient 13, 3D6 are always launched.
   */
  @Override
  public boolean staticMechanism(){
    return true;
  }
  
  /**
   * Analyse the expression to resolve the dice launch. For Patient 13, the three
   * dices are launched and then the scores are analysed to give the result.
   * 
   * The result is computed as follows: 
   *  - Two or more dices over the threshold 4 give a success;
   *  - otherwise it is a failure.
   *  - Critical success or glitches are also indicated.
   * 
   * @param exp the expression to analyse, MUST BE "3D6 --threshold2".
   * @return a String containing the dice scores and the results.
   * @throws RPGDLException This expression is thrown if the expression is not
   *                         understandable by this engine.
   */
  @Override
  public String launchDices(String exp) throws RPGDLException{
    String toReturn = "";
    Random rand = new Random();
    this.diceOne = rand.nextInt(6) + 1;
    this.diceTwo = rand.nextInt(6) + 1;
    this.diceThree = rand.nextInt(6) + 1;
    try{
      String[] parts = exp.split("--threshold");
      if(parts.length > 1){
       int success = 0;
        if(this.diceOne >= 4){success++;}
        if(this.diceTwo >= 4){success++;}
        if(this.diceThree >= 4){success++;}
        toReturn += "[" + this.diceOne + ", " + this.diceTwo + ", " 
                + this.diceThree + "]"
                + " --result " +
                ((success == 3)                                                       // if three hits
                ? ((this.diceOne == this.diceTwo && this.diceOne == this.diceThree)   //   if three times the same value
                   ? "critical-success" : "good-success")                             //     critical success else good-success
                : ((success == 0)                                                     // else if zero hit
                  ? ((this.diceOne == this.diceTwo && this.diceOne == this.diceThree) //   if three times the same value
                     ? "critical-failure" : "bad-failure")                            //     critical failure else bad-failure
                  : (success == 2) ? "success" : "failure"                            // else classic success or failure
                  )                                                                   // endif success == 0
                )                                                                    // endif success == 3
                + "--threshold("+ "2" + ")"
                + " --hits(" + success + ")";
      }//if length > 1
      else{
        throw new RPGDLException("Can't analyse the dice expression for Patient 13.");
      }
    }//try
    catch(Exception e){
      throw new RPGDLException("Can't analyse the dice expression for Patient 13.");
    }
    return toReturn;
  }
  
  /**
   * The expression that MUST be used for the game Patient 13.
   * 
   * @return the expression to use.
   */
  @Override
  public String helpExpression(){
    return "3D6 --threshold2 (static)";
  }
    
  /**
   * This method gives a quick description of how the die mechanism for the 
   * game Patient 13 works.
   * 
   * @return a description of the die mechanism.
   */
  @Override
  public String mechanismQuickDescription(){
    return "In Patient 13, three six faces dices are launched. The results are "
            + "then compared to a score at the discretion of the GM, although "
            + "it is usually 4. Two dices must above this score for the action "
            + "to succeed. Otherwise the action is a failure. There are some "
            + "specific effects based on the results: \r\n"
            + " - three hits above: +1 to the involved traits;\r\n"
            + " - three same hits above (critical success): +3 to the involved traits;\r\n"
            + " - three hits below: -1 to the involved traits;\r\n"
            + " - three same hits below (critical failure): -3 to the involved traits;\r\n"
            + "";
  }
  
}
