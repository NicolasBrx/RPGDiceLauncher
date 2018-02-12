package mechanisms;

import java.util.ArrayList;
import java.util.Random;
import tools.RPGDLException;

/**
 * This class handles die mechanism for the RPG called Shadowrun. In this game
 * several six face dices are launched for each test. To succeed, a given amount
 * of dice must exceed a threshold, usually 4.
 * 
 * @author Nicolas Brax
 */
public class SRMechanisms implements GameMechanisms {
  
  /**
   * The scores hit by the dices after a launch.
   */
  private ArrayList<Integer> scores = new ArrayList<>();
  
  /**
   * This method is an indicator to the fact that the mechanism is static or not, 
   * meaning that each test is resolved using the same launch or that each
   * launch can use different dice according to contexts. In Shadowrun, it is 
   * different in the number of dice and the threshold of hits to reach.
   * 
   * @return False since for Shadowrun launches might be different one from another.
   */
  @Override
  public boolean staticMechanism(){
    return false;
  }
  
  /**
   * Analyse the expression to resolve the dice launch. For Shadowrun, the 
   * dices are launched and then the scores are analysed to give the result.
   * 
   * The result is computed as follows: 
   *  - Each dice above 4 is a hit, if the number of hits is over a threshold it
   *    a success;
   *  - otherwise it is a failure.
   *  - Critical success or glitches are also indicated.
   * 
   * @param exp the expression to analyse.
   * @return a String containing the dice scores and the results.
   * @throws RPGDLException This expression is thrown if the expression is not
   *                         understandable by this engine.
   */
  @Override
  public String launchDices(String exp) throws RPGDLException{
    scores.clear();
    String toReturn = "";
    Random rand = new Random();
    try{
      int numberOfLaunch = Integer.parseInt(exp.split("D|d")[0]);
      int threshold = Integer.parseInt(exp.split("--threshold")[1].trim());
      int hits = 0, glitches = 0, relaunch = 0;
      for(int i = 0 ; i < numberOfLaunch ; ++i){
        scores.add(rand.nextInt(6) + 1);
        if(exp.contains("--edge") && scores.get(i) == 6){
          numberOfLaunch++;
          relaunch++;
        }
        if(scores.get(i) > 4){hits++;}
        else if(scores.get(i) == 1){glitches++;}
      }
      toReturn += scores 
               + " --result";
      if(hits >= threshold){
        toReturn += (hits >= (threshold + 4)) ? " critical-success" : " success";
      }
      else{
        toReturn += " failure";
      }
      if(glitches >= (numberOfLaunch / 2)){
        toReturn += (hits > 0) ? " glitch" : " critical-glitch";
      }
      toReturn += " --hits(" + hits + ")"
               + " --relaunch(" + relaunch 
               + ") --threshold(" + threshold +")"
               ;
    }//try
    catch(Exception e){
      throw new RPGDLException("Can't analyse the dice expression for Shadowrun.");
    }
    return toReturn;
  }
  
  /**
   * The expression that MUST be used for the game Shadowrun. Capital letters
   * are meant to be replaced by digits.
   * 
   * @return the expression to use.
   */
  @Override
  public String helpExpression(){
    return "XD6 --edge --thresholdT";
  }
    
  /**
   * This method gives a quick description of how the die mechanism for the 
   * game Shadowrun works.
   * 
   * @return a description of the die mechanism.
   */
  @Override
  public String mechanismQuickDescription(){
    return "In Shadowrun, six faces dices are launched and the results are "
            + "compared to a threshold. Only a score higher than 5 or 6 counts"
            + "as a hit and is taken into account to reach the threshold. Moreover "
            + "effects might happen: \r\n"
            + " - half scores are 1: glitches;\r\n"
            + " - half scores are 1 and no 5 or 6: critical glitches;\r\n"
            + " - 4 hits besides the threshold: critical success;\r\n"
            + " - 6 are rolled back if an edge point has been spent.";
  }
}
