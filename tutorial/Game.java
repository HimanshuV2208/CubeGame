package tutorial;

import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Canvas;
import java.util.Random;

public class Game extends Canvas implements Runnable
{
    
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 640, HEIGHT = WIDTH /12 *9;

    private boolean running = false;
    private Thread thread;

    private Random r;  
    private Handler handler;
    private HUD hud;
    private Spawn spawn;
    private Menu menu;

    public enum STATE
    {
        Menu,
        Game,
        Help;
    }

    public STATE gameState = STATE.Menu;

    public Game()
    {
        handler = new Handler();
        menu = new Menu(this, handler);
        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(menu);

        new Window(WIDTH, HEIGHT, "Let's Build a Game!", this);

        hud = new HUD();
        spawn = new Spawn(handler,hud);
        menu = new Menu(this, handler);
        r=new Random();

        if(gameState == STATE.Game)
        {
            
            handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32,ID.Player, handler));
            handler.addObject(new BasicEnemy(r.nextInt(WIDTH),r.nextInt(HEIGHT),ID.BasicEnemy,handler));//Enemy 1
            
        }
    }

    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop()
    {
        try
        {
            thread.join();
            running = false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick()
    {
        handler.tick();
        if(gameState == STATE.Game)
        {
            hud.tick();
            spawn.tick();
        }
        else if(gameState == STATE.Menu)
        {
            menu.tick();
        }
    }

    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();     
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);

        if(gameState == STATE.Game)
        {
            hud.render(g);
        }
        else if(gameState == STATE.Menu || gameState == STATE.Help)
        {
            menu.render(g);
        }

        g.dispose();
        bs.show();
    }

    public static float clamp(float var, float min, float max)
    {
        if(var>=max)
            return var = max;
        else if(var<=min)
            return var = min;
        else 
            return var;
    }

    public static void main(String args[])
    {
        new Game();
    }
}