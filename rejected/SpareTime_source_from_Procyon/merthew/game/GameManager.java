// 
// Decompiled by Procyon v0.5.36
// 

package merthew.game;

import java.util.Iterator;
import merthew.engine.physics.HasPhysics;
import merthew.engine.Renderer;
import java.util.Random;
import merthew.engine.GameContainer;
import merthew.engine.audio.SoundClip;
import merthew.game.objects.Coin;
import java.util.ArrayList;
import merthew.game.objects.Player;
import merthew.engine.AbstractGame;

public class GameManager extends AbstractGame
{
    boolean showFPS;
    boolean showBBox;
    Player player;
    ArrayList<Coin> coins;
    SoundClip coinCollect;
    int frame;
    
    public GameManager() {
        this.showFPS = true;
        this.showBBox = false;
        this.coins = new ArrayList<Coin>();
        this.coinCollect = new SoundClip("/../res/audio/pluck.wav");
        this.frame = 0;
        this.player = new Player(300.0f, 300.0f);
    }
    
    @Override
    public void update(final GameContainer gc, final float dt) {
        if (this.frame >= 60) {
            this.frame = 0;
        }
        final Random rand = new Random();
        if (this.frame == 0) {
            final Coin coin = new Coin((float)(rand.nextInt(gc.getWidth() - 20) + 10), 0.0f);
            this.coins.add(coin);
        }
        ++this.frame;
        if (gc.getInput().isKeyUp(49)) {
            this.showFPS = !this.showFPS;
        }
        if (gc.getInput().isKeyUp(50)) {
            this.showBBox = !this.showBBox;
        }
    }
    
    @Override
    public void render(final GameContainer gc, final Renderer r) {
        r.clear();
        if (this.showFPS) {
            r.drawRect(1, 1, 50, 10, -1);
            r.drawText("FPS: " + gc.getFps(), 2, 3, -1);
        }
        r.drawRect(1, 12, 50, 10, -1);
        r.drawText("Coins: " + this.player.getCoinCount(), 2, 14, -1);
        r.setzDepth(0);
        this.player.draw(gc);
        this.player.move(gc);
        this.player.calcPhysics(gc);
        for (final Coin coin : this.coins) {
            coin.draw(gc);
            coin.calcPhysics(gc);
            if (this.showBBox) {
                coin.drawBB(gc);
            }
        }
        for (int i = 0; i < this.coins.size(); ++i) {
            final Coin coin2 = this.coins.get(i);
            if (coin2.isCollide(this.player)) {
                this.coins.remove(coin2);
                this.player.setCoinCount(this.player.getCoinCount() + 1);
                this.coinCollect.play();
            }
        }
        if (this.showBBox) {
            this.player.drawBB(gc);
        }
    }
}
