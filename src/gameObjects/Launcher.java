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
    public int changeReloadTime(double statPoints)
    {
           statPoints = statPoints*.1;
           reloadTime *= statPoints;
           return reloadTime;
     }

<<<<<<< HEAD
=======
     public int changeRange2(int statPoints) //Duplicate Method. Change to something else
     {
           //statPoints = 
    	 return statPoints; //to prevent error
     }

>>>>>>> 09f98d12236b6169efb723b12eb69951dce1e4f7
    public int changeEffectRadius(int statPoints)
    {
    	return statPoints; //to prevent error
    }

    public int changeDamage(int hurt)
       {
          hurt *=.1;
          damage = damage*hurt;
          return damage;
       }

    public int changeRange(int distance)
       {
          distance *=.1;
          range = range*distance;
          return range;
       }

    //Other methods

}
