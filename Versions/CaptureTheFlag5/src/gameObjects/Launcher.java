package gameObjects;
//TODO: Teach others how to use the collaboration software!!
//TODO: Have only one base gun, changes will be made in the Store.

//Collaborator ID: 3e12fb13-b425-4ff8-9cbf-f109595cbb68

public class Launcher
{
    //instance variables
    double reloadTime; //time between fireing clips
    double damage; //damage done from a direct hit to an enemy
    double projectileSpeed; //the rate a projectile will fire pixels/frame
    double effectRadius; //The radius of damage caused by a succsesful hit
    double range; //The time a projectile remains active


    //Constructor
    public Launcher()
        {
            reloadTime = 3.0; //3 seconds
            damage = 10.0; //10 health
            projectileSpeed = 20.0; //
            effectRadius= 5.0;
            range= 200.0;

        }

    //accessor and modifier (get and set) methods
    /*
    This enables people to "upgrade" the reload time of the Launcher by subtracting awa
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
    /*
    * Authors:Mohsin Nadir and Sasanka Kondapi
    */
    public double changeProjectileSpeed(double num)
    {
      projectileSpeed *= num;
      return projectileSpeed;
    }


    /*
    This enables people to "upgrade" the effect radius of the Launcher
    */
    public double changeEffectRadius(double statPodoubles)
    {
      statPodoubles *=.1;
      effectRadius = effectRadius*statPodoubles;
    	return effectRadius;
    }


    /*
    This enables people to "upgrade" the damage of the Launcher
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
    /*
    Authors:Sasanka K and Mohsin N
    Now that the game is finished we added it back as an option if we wanted to implement it
    */
    public void sniper()
    {
      damage = 50.0;
      reloadTime = 10.0;
      projectileSpeed = 2.0;
      range = 500.0;
      effectRadius = 5.0;

    }
           /**
        * T: Reload Time
        * D: Damage
        * S: ProjSpeed
        * E: Effect Radius
        * R: Range
        * @return
        */
       public String pack() {
        String p = "LCH" + "T" + reloadTime + "D" + damage + "S" + projectileSpeed + "E" + effectRadius+"R"+range;
        return p;
    }

    public void unpack(String s) {
        String temp;

        temp = s.substring(s.indexOf("T") + 1, s.indexOf("D") - 1);
        reloadTime = Double.parseDouble(temp);

        temp = s.substring(s.indexOf("D") + 1, s.indexOf("S") - 1);
        damage = Double.parseDouble(temp);

        temp = s.substring(s.indexOf("S") + 1, s.indexOf("E") - 1);
        projectileSpeed = Double.parseDouble(temp);

         temp = s.substring(s.indexOf("E") + 1, s.indexOf("R") - 1);
        effectRadius = Double.parseDouble(temp);

        temp = s.substring(s.indexOf("R") + 1, s.length());
        range = Double.parseDouble(temp);


    }


}
