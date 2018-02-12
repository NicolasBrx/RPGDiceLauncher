package mechanisms;

import java.util.Random;
import tools.RPGDLException;

/**
 * This class handles die mechanism for any launch. This only take into account
 * the number of dice to launch as well as the number of faces.
 * 
 * @author Nicolas Brax
 */
public class GenericMechanisms implements GameMechanisms {
  
  /**
   * This method is an indicator to the fact that the mechanism is static or not, 
   * meaning that each test is resolved using the same launch or that each
   * launch can use different dice according to contexts. Here it is fully generic.
   * 
   * @return True, 'nuff said.
   */
  @Override
  public boolean staticMechanism(){
    return false;
  }
  
  /**
   * Launch XDY dice.
   * 
   * @param exp the expression to analyse, MUST BE "XDY".
   * @return a String containing the dice scores.
   * @throws RPGDLException This expression is thrown if the expression is not
   *                         understandable by this engine.
   */
  @Override
  public String launchDices(String exp) throws RPGDLException{
    String toReturn = "[";
    Random rand = new Random();
    try{
      String[] parts = exp.split("D|d");
      for(int i = 0 ; i < Integer.parseInt(parts[0]) ; i++){
        toReturn += rand.nextInt(Integer.parseInt(parts[1])) + 1;
        if(i < Integer.parseInt(parts[0]) - 1){
          toReturn += ", ";
        }
      }
    }//try
    catch(Exception e){
      throw new RPGDLException("Can't analyse the dice expression for Generic Game.");
    }
    return toReturn + "]";
  }
  
  /**
   * The expression that might be used in a generic game.
   * 
   * @return the expression to use.
   */
  @Override
  public String helpExpression(){
    return "XDY";
  }
    
  /**
   * This method gives a quick description of how the die mechanism for a generic
   * game works.
   * 
   * @return a description of the die mechanism.
   */
  @Override
  public String mechanismQuickDescription(){
    return "A generic dice launcher. Expression must follow the format "
            + "\"X\"D\"Y\" which launches \"X\" dices with \"Y\" faces.";
  }
}
