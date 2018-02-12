package mechanisms;

import java.util.Random;
import tools.RPGDLException;

/**
 * This class handles die mechanism for the RPG called Dungeons and Dragons. In this 
 * game 1 twenty face die is launched for each test. To succeed the score must be 
 * over a given threshold, usually 10.
 * 
 * @author Nicolas Brax
 */
public class ADDMechanisms implements GameMechanisms {
  
  /**
   * The scores hit by the die after a launch.
   */
  private int score;
  
   /**
   * This method is an indicator to the fact that the mechanism is static or not, 
   * meaning that each test is resolved using the same launch or that each
   * launch can use different dice according to contexts. In Dungeons and Dragons,
   * it is always the same, 1D100 with a difficulty and a treshold.
   * 
   * @return True since for Dungeons and Dragons, it is always the same launch.
   */
  @Override
  public boolean staticMechanism(){
    return true;
  }
  
  /**
   * Analyse the expression to resolve the dice launch. For Dungeons and Dragons, the
   * die is launched and then the score is analysed to give the result.
   * 
   * The result is computed as follows: 
   *  - Above the threshold is a success, below is a failure;
   *  - 20 is a critical success;
   *  - 1 is a critical failure.
   * 
   * @param exp the expression to analyse, MUST BE "1D20 --thresholdT".
   * @return a String containing the dice scores and the results.
   * @throws RPGDLException This expression is thrown if the expression is not
   *                         understandable by this engine.
   */
  @Override
  public String launchDices(String exp) throws RPGDLException{
    String toReturn = "";
    Random rand = new Random();
    score = rand.nextInt(20) + 1;
    try{
      if(!exp.substring(0,3).equalsIgnoreCase("1d20")){
        throw new RPGDLException("Can't analyse the dice expression for Dungeons and Dragons.");        
      }
      int threshold;
      if(exp.contains("threshold")){
        threshold = Integer.parseInt(exp.split("--threshold")[1].replace("(static)","").trim());
      }
      else{
        threshold = 10;
      }
      toReturn += "[" + score + "]"
               + " --result "             
               + ((score < threshold)                                           // if result < threshold
               ? ((score == 1) ? "critical-failure" : "failure")                // this is a critical failure or a failure (score == 1)
               : ((score == 20) ? "critical-success" : "success"))              // else it is a success or a critical one if scores 20.
               + " --threshold(" + threshold +")";
    }//try
    catch(Exception e){
      throw new RPGDLException("Can't analyse the dice expression for Dungeons and Dragons.");
    }
    return toReturn;
  }
  
  /**
   * The expression that MUST be used for the game Dungeons and Dragons.
   * 
   * @return the expression to use.
   */
  @Override
  public String helpExpression(){
    return "1D20 --thresholdT (static)";
  }
   
  /**
   * This method gives a quick description of how the die mechanism for the 
   * game Dungeons and Dragons works.
   * 
   * @return a description of the die mechanism.
   */
  @Override
  public String mechanismQuickDescription(){
    return "In Dungeons and Dragons, apart from launch for damage, life points and "
            + "some other computations, every test is based 1 twenty faces die. The "
            + "score is then compared to a threhsold defined by the Game Master. Moreover "
            + "1 is a critical failure while 20 is a critical success.";
  }
  
}
