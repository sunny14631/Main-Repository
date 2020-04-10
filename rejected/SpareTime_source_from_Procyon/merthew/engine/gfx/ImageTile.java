// 
// Decompiled by Procyon v0.5.36
// 

package merthew.engine.gfx;

public class ImageTile extends Image
{
    private int tileW;
    private int tileH;
    
    public ImageTile(final String path, final int tileW, final int tileH) {
        super(path);
        this.tileW = tileW;
        this.tileH = tileH;
    }
    
    public int getTileW() {
        return this.tileW;
    }
    
    public void setTileW(final int tileW) {
        this.tileW = tileW;
    }
    
    public int getTileH() {
        return this.tileH;
    }
    
    public void setTileH(final int tileH) {
        this.tileH = tileH;
    }
}
