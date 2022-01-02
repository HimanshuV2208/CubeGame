package code;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.awt.Rectangle;

public class Player extends GameObject
{
    Random r = new Random();
    Handler handler;

    public Player(int x, int y, ID id, Handler handler)
    {
        super(x, y, id);
        this.handler=handler;
    }

    public Rectangle getBounds()
    {
        return new Rectangle((int)x, (int)y, 32, 32);
    }

    public void tick()
    {
        x+=velX;
        y+=velY;

        x = (int) Game.clamp(x, 0, Game.WIDTH-38);
        y = (int) Game.clamp(y, 0, Game.HEIGHT-61);
        
        handler.addObject(new Trail((int)x, (int)y, ID.Trail, Color.white, 32, 32, 0.05f, handler));

        collision();
    }

    private void collision()
    {
        for(int i=0; i<handler.object.size(); i++)
        {
            
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getID() == ID.BasicEnemy || tempObject.getID() == ID.FastEnemy || tempObject.getID() == ID.SmartEnemy)
            {
                if(getBounds().intersects(tempObject.getBounds()))
                {
                    HUD.HEALTH -=2;
                }
            }
            
            if(tempObject.getID() == ID.EnemyBoss)
            {
                if(getBounds().intersects(tempObject.getBounds()))
                {
                    HUD.HEALTH -=1000;
                }
            }
            
        }
    }

    public void render(Graphics g)
    {
        g.setColor(Color.white);
        g.fillRect((int)x,(int)y,32,32);
    }
}