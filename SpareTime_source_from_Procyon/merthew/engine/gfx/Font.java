// 
// Decompiled by Procyon v0.5.36
// 

package merthew.engine.gfx;

public class Font
{
    public static final Font STANDARD;
    private Image fontImage;
    private int[] offsets;
    private int[] widths;
    
    static {
        STANDARD = new Font("/font_standard.png");
    }
    
    public Font(final String path) {
        this.fontImage = new Image(path);
        this.offsets = new int[59];
        this.widths = new int[59];
        int unicode = 0;
        for (int i = 0; i < this.fontImage.getW(); ++i) {
            if (this.fontImage.getP()[i] == -16776961) {
                this.offsets[unicode] = i;
            }
            if (this.fontImage.getP()[i] == -256) {
                this.widths[unicode] = i - this.offsets[unicode];
                ++unicode;
            }
        }
    }
    
    public Image getFontImage() {
        return this.fontImage;
    }
    
    public void setFontImage(final Image fontImage) {
        this.fontImage = fontImage;
    }
    
    public int[] getOffsets() {
        return this.offsets;
    }
    
    public void setOffsets(final int[] offsets) {
        this.offsets = offsets;
    }
    
    public int[] getWidths() {
        return this.widths;
    }
    
    public void setWidths(final int[] widths) {
        this.widths = widths;
    }
}
