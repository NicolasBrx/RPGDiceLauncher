package mechanisms;

import java.util.Random;

/**
 * 2D6
 * @author Nicolas Brax
 */
public class FSMechanisms implements GameMechanisms {
  
  /**
   * 
   * @param exp
   * @return 
   */
  @Override
  public String launchDices(String exp){
    Random rand = new Random();
    int positiveDice = 0;
    while(positiveDice == 0 || (positiveDice % 6) == 0){
      positiveDice += (rand.nextInt(6) + 1);
    }
    int negativeDice = 0;
    while(negativeDice == 0 || (negativeDice % 6) == 0){
      negativeDice += (rand.nextInt(6) + 1);
    }
    String[] parts = exp.split("--add");
    int attributeValue = 0;
    if(parts.length != 0){
      for(int i = 0 ; i < parts.length ; ++i){
        attributeValue += Integer.parseInt(parts[i]);
      }
    }
    // TODO: critical success and failures
    return String.valueOf(positiveDice - negativeDice + attributeValue);
  }
  
  /**
   * 
   * @return 
   */
  @Override
  public String mechanismQuickDescription(){
    return "In Feng Shui, it launches two six face dices, one positive and one "
            + "negative and add their values. The value of related attribute or "
            + "skill can be added using the --addX option where X is the value.";
  }
  
}
