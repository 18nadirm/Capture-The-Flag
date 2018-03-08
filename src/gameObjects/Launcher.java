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


    //accessor and modifier (get and set) methods
    public int changeCooldown(int cooldown)
       {
           cooldown *=.1;
           reloadTime= reloadTime*cooldown;
           return reloadTime;
       }

    //Other methods

}
