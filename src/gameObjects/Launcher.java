package gameObjects;


public class Launcher
{
    //instance variables
    int reloadTime;
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
    public int changeReloadTime(int statPoints)//stat points has to be an interger because you cannot put in half a point
    {
           statPoints = statPoints*.1;
           reloadTime = reloadTime- statPoints;
           return reloadTime;
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
          damage = damage*hurt;
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
