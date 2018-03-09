package gameObjects;
import java.lang.Math;
//Collaborator ID: 3e12fb13-b425-4ff8-9cbf-f109595cbb68
// Map: bb2adf41-4cc6-4688-b8cf-a285b2bbf725
public class Launcher
{
    //instance variables
    double reloadTime;
    double damage;
    double projectileSpeed;
    double effectRadius;
    double range;


    //Constructor
    public Launcher()
        {
            reloadTime = 3.0;
            damage = 10.0;
            projectileSpeed = 20.0;
            effectRadius= 5.0;
            range= 200.0;

        }

    //accessor and modifier (get and set) methods
    /*
    This enables people to "upgrade" the reloadTime of the Launcher by subtracting awa
    */
    public double changeReloadTime(double statPodoubles)//stat podoubles has to be an doubleerger because you cannot put in half a podouble //statPodoubles needs to be a double because you can't multiply a double by an double and have it return an double. You can use Math.round and convert it back to an double in the end.
    {
           statPodoubles *= .1;
           reloadTime -= statPodoubles;
           return reloadTime;
     }
    public double convertTodouble(double var) { //rounds and converts doubles such as statPodoubles doubleo doubleegers
    	Math.round(var); 	//rounds doubleo double
    	return (double)var;	//typecasted

    }
    public double changeEffectRadius(double statPodoubles)
    {
      statPodoubles *=.1;
      effectRadius = effectRadius*statPodoubles;
    	return effectRadius;
    }
    /*
    This enables people to "upgrade" the range of the Launcher
    */
    public double changeDamage(double statPodoubles)
       {
          statPodoubles *=.1;
          damage = damage*statPodoubles;
          return damage;
       }


/*
This enables people to "upgrade" the range of the Launcher
*/
    public double changeRange(double statPodoubles)
       {
          statPodoubles *=.1;
          range = range*statPodoubles;
          return range;
       }

       public double getDamage() {return damage;} //returns damage that gets subtracted from health
       public double getRange() {return range;} // returns amount of pixels that the projectile should travel
       public double getPSpeed() {return projectileSpeed;} // returns the speed at which the projectile should travel
    //Other methods

}
