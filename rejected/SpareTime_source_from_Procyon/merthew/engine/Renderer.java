// 
// Decompiled by Procyon v0.5.36
// 

package merthew.engine;

import merthew.engine.gfx.ImageTile;
import merthew.engine.gfx.Image;
import java.awt.image.DataBufferInt;
import merthew.engine.gfx.Font;

public class Renderer
{
    private int pW;
    private int pH;
    private int[] p;
    private int[] zb;
    private int zDepth;
    private Font font;
    
    public Renderer(final GameContainer gc) {
        this.zDepth = 0;
        this.font = Font.STANDARD;
        this.pW = gc.getWidth();
        this.pH = gc.getHeight();
        this.p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
        this.zb = new int[this.p.length];
    }
    
    public void clear() {
        for (int i = 0; i < this.p.length; ++i) {
            this.p[i] = -16777216;
            this.zb[i] = 0;
        }
    }
    
    public void setPixel(final int x, final int y, final int value) {
        final int alpha = value >> 24 & 0xFF;
        if (x < 0 || x >= this.pW || y < 0 || y >= this.pH || alpha == 0) {
            return;
        }
        if (this.zb[x + y * this.pW] > this.zDepth) {
            return;
        }
        if (alpha == 255) {
            this.p[x + y * this.pW] = value;
        }
        else {
            final int pixelColor = this.p[x + y * this.pW];
            final int newR = (pixelColor >> 16 & 0xFF) - (int)(((pixelColor >> 16 & 0xFF) - (value >> 16 & 0xFF)) * (alpha / 255.0f));
            final int newG = (pixelColor >> 8 & 0xFF) - (int)(((pixelColor >> 8 & 0xFF) - (value >> 8 & 0xFF)) * (alpha / 255.0f));
            final int newB = (pixelColor >> 0 & 0xFF) - (int)(((pixelColor >> 0 & 0xFF) - (value >> 0 & 0xFF)) * (alpha / 255.0f));
            this.p[x + y * this.pW] = (0xFF000000 | newR << 16 | newG << 8 | newB);
            this.zb[x + y * this.pW] = this.zDepth;
        }
    }
    
    public void drawText(String text, final int offX, final int offY, final int color) {
        text = text.toUpperCase();
        int offset = 0;
        for (int i = 0; i < text.length(); ++i) {
            final int unicode = text.codePointAt(i) - 32;
            for (int y = 0; y < this.font.getFontImage().getH(); ++y) {
                for (int x = 0; x < this.font.getWidths()[unicode]; ++x) {
                    if (this.font.getFontImage().getP()[x + this.font.getOffsets()[unicode] + y * this.font.getFontImage().getW()] == -1) {
                        this.setPixel(x + offX + offset, y + offY, color);
                    }
                }
            }
            offset += this.font.getWidths()[unicode];
        }
    }
    
    public void drawCenterText(String text, final int offX, final int offY, final int color) {
        text = text.toUpperCase();
        int offset = 0;
        for (int i = 0; i < text.length(); ++i) {
            final int unicode = text.codePointAt(i) - 32;
            offset += this.font.getWidths()[unicode];
        }
        this.drawText(text, offX - offset / 2, offY - 3, color);
    }
    
    public void drawImage(final Image image, final int offX, final int offY) {
        if (offX < -image.getW()) {
            return;
        }
        if (offY < -image.getH()) {
            return;
        }
        if (offX >= this.pW) {
            return;
        }
        if (offY >= this.pH) {
            return;
        }
        int nX = 0;
        int nY = 0;
        int nW = image.getW();
        int nH = image.getH();
        if (offX < 0) {
            nX -= offX;
        }
        if (offY < 0) {
            nY -= offY;
        }
        if (nW + offX > this.pW) {
            nW -= nW + offX - this.pW;
        }
        if (nH + offY > this.pH) {
            nH -= nH + offY - this.pH;
        }
        for (int y = nY; y < nH; ++y) {
            for (int x = nX; x < nW; ++x) {
                this.setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
            }
        }
    }
    
    public void drawImageTile(final ImageTile image, final int offX, final int offY, final int tileX, final int tileY) {
        if (offX < -image.getTileW()) {
            return;
        }
        if (offY < -image.getTileH()) {
            return;
        }
        if (offX >= this.pW) {
            return;
        }
        if (offY >= this.pH) {
            return;
        }
        int nX = 0;
        int nY = 0;
        int nW = image.getTileW();
        int nH = image.getTileH();
        if (offX < 0) {
            nX -= offX;
        }
        if (offY < 0) {
            nY -= offY;
        }
        if (nW + offX > this.pW) {
            nW -= nW + offX - this.pW;
        }
        if (nH + offY > this.pH) {
            nH -= nH + offY - this.pH;
        }
        for (int y = nY; y < nH; ++y) {
            for (int x = nX; x < nW; ++x) {
                this.setPixel(x + offX, y + offY, image.getP()[x + tileX * image.getTileW() + (y + tileY * image.getTileH()) * image.getW()]);
            }
        }
    }
    
    public void drawRect(final int offX, final int offY, final int width, final int height, final int color) {
        if (offX < -width) {
            return;
        }
        if (offY < -height) {
            return;
        }
        if (offX >= this.pW) {
            return;
        }
        if (offY >= this.pH) {
            return;
        }
        int nX = 0;
        int nY = 0;
        int nW = width;
        int nH = height;
        if (offX < 0) {
            nX -= offX;
        }
        if (offY < 0) {
            nY -= offY;
        }
        if (nW + offX > this.pW) {
            nW -= nW + offX - this.pW;
        }
        if (nH + offY > this.pH) {
            nH -= nH + offY - this.pH;
        }
        for (int y = nY; y <= nH; ++y) {
            this.setPixel(offX, y + offY, color);
            this.setPixel(offX + width, y + offY, color);
        }
        for (int x = nX; x <= nW; ++x) {
            this.setPixel(x + offX, offY, color);
            this.setPixel(x + offX, offY + height, color);
        }
    }
    
    public void fillRect(final int offX, final int offY, final int width, final int height, final int color) {
        if (offX < -width) {
            return;
        }
        if (offY < -height) {
            return;
        }
        if (offX >= this.pW) {
            return;
        }
        if (offY >= this.pH) {
            return;
        }
        int nX = 0;
        int nY = 0;
        int nW = width;
        int nH = height;
        if (offX < 0) {
            nX -= offX;
        }
        if (offY < 0) {
            nY -= offY;
        }
        if (nW + offX > this.pW) {
            nW -= nW + offX - this.pW;
        }
        if (nH + offY > this.pH) {
            nH -= nH + offY - this.pH;
        }
        for (int x = nX; x <= nW; ++x) {
            for (int y = nY; y <= nH; ++y) {
                this.setPixel(offX + x, offY + y, color);
            }
        }
    }
    
    public int getzDepth() {
        return this.zDepth;
    }
    
    public void setzDepth(final int zDepth) {
        this.zDepth = zDepth;
    }
}
