package mechanisms;

import java.util.Random;

/**
 * 2D6
 * @author Nicolas Brax
 */
public class FSMechanisms implements GameMechanisms {
  
  /**
   * 
   */
  private int positiveDice;
  
  /**
   * 
   */
  private int negativeDice;
  
  /**
   * 
   * @param exp
   * @return 
   */
  @Override
  public String launchDices(String exp){
    Random rand = new Random();
    this.positiveDice = 0;
    while(this.positiveDice == 0 || (this.positiveDice % 6) == 0){
      this.positiveDice += (rand.nextInt(6) + 1);
    }
    this.negativeDice = 0;
    while(this.negativeDice == 0 || (this.negativeDice % 6) == 0){
      this.negativeDice += (rand.nextInt(6) + 1);
    }
    String[] parts = exp.split("--add ");
    int attributeValue = 0;
    if(parts.length != 0){
      for(int i = 0 ; i < parts.length ; ++i){
        attributeValue += Integer.parseInt(parts[i]);
      }
    }
    return String.valueOf(this.positiveDice - this.negativeDice + attributeValue);
  }
  
  /**
   * 
   * @return 
   */
  @Override
  public String mechanismQuickDescription(){
    return "In Feng Shui, it launches two six faces dices, one positive and one "
            + "negative and add their values. The value of related attribute or "
            + "skill can be added using the --addX option where X is the value.";
  }
  
}
