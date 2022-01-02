package code;

import java.awt.event.MouseAdapter;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import code.Game.STATE;

public class Menu extends MouseAdapter
{

    private Game game;
    private Handler handler;
    private Random r = new Random();

    public Menu(Game game, Handler handler)
    {
        this.game=game;
        this.handler=handler;
    }

    public void mousePressed(MouseEvent e)
    {
        int mx = e.getX();
        int my = e.getY();

        if(game.gameState == STATE.Menu)
        {

            //play button
            if(mouseOver(mx, my, 210, 150, 200, 64))
            {
                game.gameState = STATE.Game;
                handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32,ID.Player, handler));
                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH),r.nextInt(Game.HEIGHT),ID.BasicEnemy,handler));
            }

            //Help button
            if(mouseOver(mx, my, 210, 250, 200, 64))
            {
                game.gameState = STATE.Help;
            }

            //Back button for help
            if(game.gameState == STATE.Help)
            {
                if(mouseOver(mx, my, 210, 350, 200, 64))
                {
                    game.gameState = STATE.Menu;
                    return;
                }
            }

            //quit button
            if(mouseOver(mx, my, 210, 350, 200, 64))
            {
                System.exit(0);
            }
        }

        //Back button for help
        if(game.gameState == STATE.Help)
        {
            if(mouseOver(mx, my, 210, 350, 200, 64))
            {
                game.gameState = STATE.Menu;
                return;
            }
        }

    }
    public void mouseReleased(MouseEvent e)
    {

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height)
    {
        if(mx > x && mx < x+width)
        {
            if(my > y && my < y+height)
                return true;
            else 
                return false;
        }
        else 
            return false;
    }

    public void tick()
    {

    }

    public void render(Graphics g)
    {

        Font fnt = new Font("arial", 1, 50);
        Font fnt2 = new Font("arial", 1, 30);

        if(game.gameState == STATE.Menu)
        {

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Menu", 240, 70);

            g.setFont(fnt2);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Play", 280, 190);

            g.drawRect(210, 250, 200, 64);
            g.drawString("Help", 280, 290);

            g.drawRect(210, 350, 200, 64);
            g.drawString("Quit", 280, 390);

        }
        else if(game.gameState == STATE.Help)
        {
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Help", 240, 70);

            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 280, 390);
        }
    }
}