// 
// Decompiled by Procyon v0.5.36
// 

package merthew.game;

import merthew.engine.AbstractGame;
import merthew.engine.GameContainer;

public class Main
{
    public static void main(final String[] args) {
        final GameContainer gc = new GameContainer(new GameManager());
        gc.setTitle("Spare Time");
        gc.setHeight(480);
        gc.setWidth(640);
        gc.setScale(2.0f);
        gc.start();
    }
}
