package ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract;

import android.graphics.ColorFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Xfermode;

public interface DrawingPaint {
    void reset();

    void set(Paint src);

    int getFlags();

    void setFlags(int flags);

    int getHinting();

    void setHinting(int mode);

    boolean isAntiAlias();

    void setAntiAlias(boolean aa);

    boolean isDither();

    void setDither(boolean dither);

    boolean isLinearText();

    void setLinearText(boolean linearText);

    boolean isSubpixelText();

    void setSubpixelText(boolean subpixelText);

    boolean isUnderlineText();

    void setUnderlineText(boolean underlineText);

    boolean isStrikeThruText();

    void setStrikeThruText(boolean strikeThruText);

    boolean isFakeBoldText();

    void setFakeBoldText(boolean fakeBoldText);

    boolean isFilterBitmap();

    void setFilterBitmap(boolean filter);

    int getColor();

    float getTextSize();

    void setColor(int color);

    void setTextSize(float textSize);

    Paint.Align getTextAlign();

    public void getTextBounds(char[] text, int index, int count, Rect bounds);

    public void getTextBounds(String text, int start, int end, Rect bounds);


    int getAlpha();

    void setAlpha(int a);

    void setARGB(int a, int r, int g, int b);

    float getStrokeWidth();

    void setStrokeWidth(float width);

    float getStrokeMiter();

    void setStrokeMiter(float miter);

    Paint.Cap getStrokeCap();

    void setStrokeCap(Paint.Cap cap);

    Paint.Join getStrokeJoin();

    void setStrokeJoin(Paint.Join join);

    boolean getFillPath(Path src, Path dst);

    Shader getShader();

    Shader setShader(Shader shader);

    ColorFilter getColorFilter();

    ColorFilter setColorFilter(ColorFilter filter);

    Xfermode getXfermode();

    Xfermode setXfermode(Xfermode xfermode);

    PathEffect getPathEffect();

    PathEffect setPathEffect(PathEffect effect);

    MaskFilter getMaskFilter();

    MaskFilter setMaskFilter(MaskFilter maskfilter);

    Typeface getTypeface();

    Typeface setTypeface(Typeface typeface);

    boolean isElegantTextHeight();

    void setElegantTextHeight(boolean elegant);

    float getTextScaleX();

    void setTextScaleX(float scaleX);

    float getTextSkewX();

    void setTextSkewX(float skewX);

    float getLetterSpacing();

    void setLetterSpacing(float letterSpacing);

    String getFontFeatureSettings();

    void setFontFeatureSettings(String settings);


    float ascent();

    float descent();

    float getFontMetrics(Paint.FontMetrics metrics);

    Paint.FontMetrics getFontMetrics();

    int getFontMetricsInt(Paint.FontMetricsInt fmi);

    Paint.FontMetricsInt getFontMetricsInt();

    float getFontSpacing();

    float measureText(char[] text, int index, int count);

    float measureText(String text, int start, int end);

    float measureText(String text);

    float measureText(CharSequence text, int start, int end);

    int breakText(char[] text, int index, int count,
                  float maxWidth, float[] measuredWidth);

    int breakText(CharSequence text, int start, int end,
                  boolean measureForwards,
                  float maxWidth, float[] measuredWidth);

    int breakText(String text, boolean measureForwards,
                  float maxWidth, float[] measuredWidth);

    int getTextWidths(char[] text, int index, int count,
                      float[] widths);

    int getTextWidths(CharSequence text, int start, int end,
                      float[] widths);

    int getTextWidths(String text, int start, int end, float[] widths);

    int getTextWidths(String text, float[] widths);


    void getTextPath(char[] text, int index, int count,
                     float x, float y, Path path);

    void getTextPath(String text, int start, int end,
                     float x, float y, Path path);


    boolean hasGlyph(String string);

    float getRunAdvance(char[] text, int start, int end, int contextStart, int contextEnd,
                        boolean isRtl, int offset);

    float getRunAdvance(CharSequence text, int start, int end, int contextStart,
                        int contextEnd, boolean isRtl, int offset);

    int getOffsetForAdvance(char[] text, int start, int end, int contextStart,
                            int contextEnd, boolean isRtl, float advance);

    int getOffsetForAdvance(CharSequence text, int start, int end, int contextStart,
                            int contextEnd, boolean isRtl, float advance);

    void setTextAlign(Paint.Align align);
}
