// 
// Decompiled by Procyon v0.5.36
// 

package merthew.game.objects;

import merthew.engine.physics.DrawnObject;
import merthew.engine.physics.BBox;
import merthew.engine.physics.HasPhysics;

public class Platform extends HasPhysics
{
    public Platform(final float x, final float y, final float width, final float height) {
        super(new BBox(x, y, (int)width, (int)height), false, true, x, y, width, height, Type.RECTANGLE, -1);
    }
}
