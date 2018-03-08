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
    public int changeCooldown(int cooldown)
       {
           cooldown *=.1;
           reloadTime= reloadTime*cooldown;
           return reloadTime;
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
