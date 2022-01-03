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

    private Handler handler;
    private HUD hud;
    private Random r = new Random();

    public Menu(Game game, Handler handler, HUD hud)
    {
        this.handler=handler;
        this.hud = hud;
    }

    public void mousePressed(MouseEvent e)
    {
        int mx = e.getX();
        int my = e.getY();

        if(Game.gameState == STATE.Menu)
        {

            //Play button
            if(mouseOver(mx, my, 210, 150, 200, 64))
            {
                Game.gameState = STATE.Game;
                handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32,ID.Player, handler));
                handler.clearEnemys();
                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH),r.nextInt(Game.HEIGHT),ID.BasicEnemy,handler));
            }

            //Help button
            if(mouseOver(mx, my, 210, 250, 200, 64))
            {
                Game.gameState = STATE.Help;
            }

            //Quit button
            if(mouseOver(mx, my, 210, 350, 200, 64))
            {
                System.exit(0);
            }
        }

        //Back button for help
        if(Game.gameState == STATE.Help)
        {
            if(mouseOver(mx, my, 210, 350, 200, 64))
            {
                Game.gameState = STATE.Menu;
                return;
            }
        }
        
        //Play again button for Game Over
        if(Game.gameState == STATE.End)
        {
            if(mouseOver(mx, my, 210, 350, 200, 64))
            {
                Game.gameState = STATE.Menu;
				hud.setLevel(1);
				hud.setScore(0);
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
        Font fnt3 = new Font("arial", 1, 20);

        if(Game.gameState == STATE.Menu)
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
        else if(Game.gameState == STATE.Help)
        {
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Help", 255, 70);
            
            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 280, 390);
            
            g.setFont(fnt3);
            g.drawString("Use WASD to move around and dodge the enemies", 65, 200);
        }
        else if(Game.gameState == STATE.End)
        {
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Game Over", 190, 70);
            
            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Play Again", 238, 390);
            
            g.setFont(fnt3);
            g.drawString("Your Score was : " + hud.getScore(), 210, 200);
        }
    }
}