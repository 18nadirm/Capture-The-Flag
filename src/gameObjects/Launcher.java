package gameObjects;
import java.lang.Math;
public class Launcher
{
    //instance variables
    double reloadTime;
    int damage;
    int projectileSpeed;
    int effectRadius;
    int range;


    //Constructor
    public Launcher()
        {
            reloadTime = 3;
            damage = 10;
            projectileSpeed = 20;
            effectRadius= 5;
            range= 200;

        }

    //accessor and modifier (get and set) methods
    /*
    This enables people to "upgrade" the reloadTime of the Launcher by subtracting awa
    */
    public double changeReloadTime(double statPoints)//stat points has to be an interger because you cannot put in half a point //statPoints needs to be a double because you can't multiply a double by an int and have it return an int. You can use Math.round and convert it back to an int in the end.
    {
           statPoints *= .1;
           reloadTime -= statPoints;
           return reloadTime;
     }
    public int convertToInt(double var) { //rounds and converts doubles such as statPoints into integers
    	Math.round(var); 	//rounds into int
    	return (int)var;	//typecasted

    }
    public int changeEffectRadius(int statPoints)
    {
    	return statPoints; //to prevent error
    }
    /*
    This enables people to "upgrade" the range of the Launcher
    */
    public int changeDamage(int hurt)
       {
          hurt *=.1;
          damage *=hurt;
          return damage;
       }
/*
This enables people to "upgrade" the range of the Launcher
*/
    public int changeRange(int distance)
       {
          distance *=.1;
          range = range*distance;
          return range;
       }

       public int getDamage() {return damage;} //returns damage that gets subtracted from health
       public int getRange() {return range;} // returns amount of pixels that the projectile should travel
       public int getPSpeed() {return projectileSpeed;} // returns the speed at which the projectile should travel
    //Other methods

}
