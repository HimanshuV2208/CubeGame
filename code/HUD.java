package code;

import java.awt.Graphics;
import java.awt.Color;

public class HUD
{
    public static float HEALTH = 100;   
    private float greenValue = 255;

    private int score=0;
    private int level=1;

    public void tick()
    {

        HEALTH = Game.clamp(HEALTH, 0, 100);
        greenValue = Game.clamp(greenValue, 0, 255);

        greenValue = HEALTH * 2;

        score++;       
    }

    public void render(Graphics g)
    {
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 16);
        g.setColor(new Color(75, (int)greenValue, 0));
        g.fillRect(15, 15, (int)HEALTH*2, 16);
        g.setColor(Color.white);
        g.drawRect(15, 15, 200, 16);

        g.drawString("Score: " + score, 15, 44);
        g.drawString("Level: " + level, 15, 60);
    }

    public void setScore(int score)
    {
        this.score=score;
    }   

    public int getScore()
    {
        return score;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level=level;
    }
}