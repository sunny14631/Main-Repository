// 
// Decompiled by Procyon v0.5.36
// 

package merthew.engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image
{
    private int w;
    private int h;
    private int[] p;
    
    public Image(final String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Image.class.getResourceAsStream(path));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.w = image.getWidth();
        this.h = image.getHeight();
        this.p = image.getRGB(0, 0, this.w, this.h, null, 0, this.w);
        image.flush();
    }
    
    public int getW() {
        return this.w;
    }
    
    public void setW(final int w) {
        this.w = w;
    }
    
    public int getH() {
        return this.h;
    }
    
    public void setH(final int h) {
        this.h = h;
    }
    
    public int[] getP() {
        return this.p;
    }
    
    public void setP(final int[] p) {
        this.p = p;
    }
}
