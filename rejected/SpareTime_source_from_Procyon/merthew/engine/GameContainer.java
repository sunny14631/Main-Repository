// 
// Decompiled by Procyon v0.5.36
// 

package merthew.engine;

public class GameContainer implements Runnable
{
    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;
    private int fps;
    private boolean running;
    private final double UPDATE_CAP = 0.016666666666666666;
    private int width;
    private int height;
    private float scale;
    private String title;
    
    public GameContainer(final AbstractGame game) {
        this.fps = 0;
        this.running = false;
        this.width = 640;
        this.height = 480;
        this.scale = 2.0f;
        this.title = "";
        this.game = game;
    }
    
    public void start() {
        this.window = new Window(this);
        this.renderer = new Renderer(this);
        this.input = new Input(this);
        (this.thread = new Thread(this)).run();
    }
    
    public void stop() {
    }
    
    @Override
    public void run() {
        this.running = true;
        boolean render = false;
        double firstTime = 0.0;
        double lastTime = System.nanoTime() / 1.0E9;
        double passedTime = 0.0;
        double unprocessedTime = 0.0;
        double frameTime = 0.0;
        int frames = 0;
        while (this.running) {
            render = false;
            firstTime = System.nanoTime() / 1.0E9;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;
            unprocessedTime += passedTime;
            frameTime += passedTime;
            while (unprocessedTime >= 0.016666666666666666) {
                unprocessedTime -= 0.016666666666666666;
                render = true;
                this.game.update(this, 0.016666668f);
                this.input.update();
                if (frameTime >= 1.0) {
                    frameTime = 0.0;
                    this.fps = frames;
                    frames = 0;
                }
            }
            if (render) {
                this.game.render(this, this.renderer);
                this.window.update();
                ++frames;
            }
            else {
                try {
                    Thread.sleep(1L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.dispose();
    }
    
    private void dispose() {
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public float getScale() {
        return this.scale;
    }
    
    public void setScale(final float scale) {
        this.scale = scale;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public Window getWindow() {
        return this.window;
    }
    
    public Renderer getRenderer() {
        return this.renderer;
    }
    
    public void setRenderer(final Renderer renderer) {
        this.renderer = renderer;
    }
    
    public Input getInput() {
        return this.input;
    }
    
    public int getFps() {
        return this.fps;
    }
}
