package ua.com.crosp.solutions.hearthealthmonitor.common.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.HashMap;

import ua.com.crosp.solutions.hearthealthmonitor.R;

public final class StickySwitch extends View {
    private final String TAG;
    @Nullable
    private Drawable leftIcon;
    @Nullable
    private Drawable rightIcon;
    private int iconSize;
    private int iconPadding;
    private String leftText;
    private String rightText;
    private int sliderBackgroundColor;
    private int switchColor;
    private int textColor;
    @Nullable
    private Typeface typeFace;
    private final Paint sliderBackgroundPaint;
    private final RectF sliderBackgroundRect;
    private final Paint switchBackgroundPaint;
    private final Paint leftTextPaint;
    private final Rect leftTextRect;
    private final Paint rightTextPaint;
    private final Rect rightTextRect;
    private float leftTextSize;
    private float rightTextSize;
    private final int textAlphaMax;
    private final int textAlphaMin;
    private int leftTextAlpha;
    private int rightTextAlpha;
    private int textSize;
    private int selectedTextSize;
    private boolean isSwitchOn;
    private double animatePercent;
    private double animateBounceRate;

    private AnimationType animationType;
    @Nullable
    private OnSelectedChangeListener onSelectedChangeListener;
    @Nullable
    private AnimatorSet animatorSet;
    private long animationDuration;

    private TextVisibility textVisibility;
    private final Path connectionPath;
    private final float xParam;
    private final float yParam;
    private HashMap _$_findViewCache;

    @Nullable
    public final Drawable getLeftIcon() {
        return this.leftIcon;
    }

    public final void setLeftIcon(@Nullable Drawable drawable) {
        this.leftIcon = drawable;
        this.invalidate();
    }

    @Nullable
    public final Drawable getRightIcon() {
        return this.rightIcon;
    }

    public final void setRightIcon(@Nullable Drawable drawable) {
        this.rightIcon = drawable;
        this.invalidate();
    }


    public final String getLeftText() {
        return this.leftText;
    }

    public final void setLeftText(String value) {
        this.leftText = value;
        this.invalidate();
    }


    public final String getRightText() {
        return this.rightText;
    }

    public final void setRightText(String value) {
        this.rightText = value;
        this.invalidate();
    }

    public final int getSliderBackgroundColor() {
        return this.sliderBackgroundColor;
    }

    public final void setSliderBackgroundColor(@ColorInt int colorInt) {
        this.sliderBackgroundColor = colorInt;
        this.invalidate();
    }

    public final int getSwitchColor() {
        return this.switchColor;
    }

    public final void setSwitchColor(@ColorInt int colorInt) {
        this.switchColor = colorInt;
        this.invalidate();
    }

    public final int getTextColor() {
        return this.textColor;
    }

    public final void setTextColor(@ColorInt int colorInt) {
        this.textColor = colorInt;
        this.invalidate();
    }

    @Nullable
    public final Typeface getTypeFace() {
        return this.typeFace;
    }

    public final void setTypeFace(@Nullable Typeface typeFace) {
        this.typeFace = typeFace;
        this.leftTextPaint.setTypeface(typeFace);
        this.rightTextPaint.setTypeface(typeFace);
        this.invalidate();
    }

    private final void setLeftTextSize(float value) {
        this.leftTextSize = value;
        this.invalidate();
    }

    private final void setRightTextSize(float value) {
        this.rightTextSize = value;
        this.invalidate();
    }

    private final void setLeftTextAlpha(int value) {
        this.leftTextAlpha = value;
        this.invalidate();
    }

    private final void setRightTextAlpha(int value) {
        this.rightTextAlpha = value;
        this.invalidate();
    }

    private final void setTextSize(int value) {
        this.textSize = value;
        this.invalidate();
    }

    private final void setSelectedTextSize(int value) {
        this.selectedTextSize = value;
        this.invalidate();
    }

    private final void setSwitchOn(boolean value) {
        this.isSwitchOn = value;
        this.invalidate();
    }

    private final void setAnimatePercent(double value) {
        this.animatePercent = value;
        this.invalidate();
    }

    private final void setAnimateBounceRate(double value) {
        this.animateBounceRate = value;
        this.invalidate();
    }


    public final AnimationType getAnimationType() {
        return this.animationType;
    }

    public final void setAnimationType(AnimationType value) {
        this.animationType = value;
        this.invalidate();
    }

    @Nullable
    public final OnSelectedChangeListener getOnSelectedChangeListener() {
        return this.onSelectedChangeListener;
    }

    public final void setOnSelectedChangeListener(@Nullable OnSelectedChangeListener var1) {
        this.onSelectedChangeListener = var1;
    }

    @Nullable
    public final AnimatorSet getAnimatorSet() {
        return this.animatorSet;
    }

    public final void setAnimatorSet(@Nullable AnimatorSet var1) {
        this.animatorSet = var1;
    }

    public final long getAnimationDuration() {
        return this.animationDuration;
    }

    public final void setAnimationDuration(long var1) {
        this.animationDuration = var1;
    }


    public final TextVisibility getTextVisibility() {
        return this.textVisibility;
    }

    public final void setTextVisibility(TextVisibility value) {
        this.textVisibility = value;
        this.invalidate();
    }

    private final void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.StickySwitch, defStyleAttr, defStyleRes);
        this.setLeftIcon(typedArray.getDrawable(R.styleable.StickySwitch_ss_leftIcon));
        String var10001 = typedArray.getString(R.styleable.StickySwitch_ss_leftText);
        if (var10001 == null) {
            var10001 = this.leftText;
        }

        this.setLeftText(var10001);
        this.setRightIcon(typedArray.getDrawable(R.styleable.StickySwitch_ss_rightIcon));
        var10001 = typedArray.getString(R.styleable.StickySwitch_ss_rightText);
        if (var10001 == null) {
            var10001 = this.rightText;
        }

        this.setRightText(var10001);
        this.iconSize = typedArray.getDimensionPixelSize(R.styleable.StickySwitch_ss_iconSize, this.iconSize);
        this.iconPadding = typedArray.getDimensionPixelSize(R.styleable.StickySwitch_ss_iconPadding, this.iconPadding);
        this.setTextSize(typedArray.getDimensionPixelSize(R.styleable.StickySwitch_ss_textSize, this.textSize));
        this.setSelectedTextSize(typedArray.getDimensionPixelSize(R.styleable.StickySwitch_ss_selectedTextSize, this.selectedTextSize));
        this.setLeftTextSize((float) this.selectedTextSize);
        this.setRightTextSize((float) this.textSize);
        this.setSliderBackgroundColor(typedArray.getColor(R.styleable.StickySwitch_ss_sliderBackgroundColor, this.sliderBackgroundColor));
        this.setSwitchColor(typedArray.getColor(R.styleable.StickySwitch_ss_switchColor, this.switchColor));
        this.setTextColor(typedArray.getColor(R.styleable.StickySwitch_ss_textColor, this.textColor));
        this.animationDuration = (long) typedArray.getInt(R.styleable.StickySwitch_ss_animationDuration, (int) this.animationDuration);
        this.setAnimationType(AnimationType.values()[typedArray.getInt(R.styleable.StickySwitch_ss_animationType, AnimationType.LINE.ordinal())]);
        this.setTextVisibility(TextVisibility.values()[typedArray.getInt(R.styleable.StickySwitch_ss_textVisibility, TextVisibility.VISIBLE.ordinal())]);
        typedArray.recycle();
    }

    // $FF: synthetic method
    // $FF: bridge method
    static void init$default(StickySwitch var0, AttributeSet var1, int var2, int var3, int var4, Object var5) {
        if ((var4 & 2) != 0) {
            var2 = 0;
        }

        if ((var4 & 4) != 0) {
            var3 = 0;
        }

        var0.init(var1, var2, var3);
    }

    public final float getXParam() {
        return this.xParam;
    }

    public final float getYParam() {
        return this.yParam;
    }

    @Override
    protected void onDraw(@Nullable Canvas canvas) {
        super.onDraw(canvas);
        int iconMarginLeft = this.iconPadding;
        int iconMarginBottom = this.iconPadding;
        int iconMarginRight = this.iconPadding;
        int iconMarginTop = this.iconPadding;
        int iconWidth = this.iconSize;
        int iconHeight = this.iconSize;
        float sliderRadius = (float) iconMarginTop + (float) iconHeight / 2.0F;
        float circleRadius = (float) iconMarginTop + (float) iconHeight / 2.0F;
        this.sliderBackgroundPaint.setColor(this.sliderBackgroundColor);
        this.sliderBackgroundRect.set(0.0F, 0.0F, (float) this.getMeasuredWidth(), (float) (iconMarginTop + iconHeight + iconMarginBottom));
        if (canvas != null) {
            canvas.drawRoundRect(this.sliderBackgroundRect, sliderRadius, sliderRadius, this.sliderBackgroundPaint);
        }

        this.switchBackgroundPaint.setColor(this.switchColor);
        if (canvas != null) {
            canvas.save();
        }

        boolean var10000;
        label145:
        {
            double var10001 = this.animatePercent;
            if (0.0D <= this.animatePercent) {
                if (0.5D >= var10001) {
                    var10000 = true;
                    break label145;
                }
            }

            var10000 = false;
        }

        boolean isBeforeHalf = var10000;
        float widthSpace = (float) this.getMeasuredWidth() - circleRadius * (float) 2;
        double ocX = (double) circleRadius + (double) widthSpace * Math.min(1.0D, this.animatePercent * (double) 2);
        double ocRadius = (double) circleRadius * (isBeforeHalf ? 1.0D - this.animatePercent : this.animatePercent);
        double ccX = (double) circleRadius + (double) widthSpace * (isBeforeHalf ? 0.0D : Math.abs(0.5D - this.animatePercent) * (double) 2);
        double ccRadius = (double) circleRadius * (isBeforeHalf ? 1.0D - this.animatePercent : this.animatePercent);
        if (canvas != null) {
            canvas.drawCircle((float) ocX, circleRadius, (float) this.evaluateBounceRate(ocRadius), this.switchBackgroundPaint);
        }

        if (canvas != null) {
            canvas.drawCircle((float) ccX, circleRadius, (float) this.evaluateBounceRate(ccRadius), this.switchBackgroundPaint);
        }

        float bottomSpaceHeight;
        float leftTextX;
        if (this.animationType == AnimationType.LINE) {
            bottomSpaceHeight = circleRadius - circleRadius / (float) 2;
            leftTextX = circleRadius + circleRadius / (float) 2;
            if (canvas != null) {
                canvas.drawCircle((float) ccX, circleRadius, (float) this.evaluateBounceRate(ccRadius), this.switchBackgroundPaint);
            }

            if (canvas != null) {
                canvas.drawRect((float) ccX, bottomSpaceHeight, (float) ocX, leftTextX, this.switchBackgroundPaint);
            }
        } else if (this.animationType == AnimationType.CURVED && this.animatePercent > (double) 0 && this.animatePercent < (double) 1) {
            this.connectionPath.rewind();
            bottomSpaceHeight = (float) ccX + (float) ccRadius * this.xParam;
            leftTextX = (float) ocX - (float) ccRadius * this.xParam;
            float $i$a$2$run = circleRadius - (float) ccRadius * this.yParam;
            float leftTextY = circleRadius + (float) ccRadius * this.yParam;
            float middlePointX = (leftTextX + bottomSpaceHeight) / (float) 2;
            float rightTextX = ($i$a$2$run + leftTextY) / (float) 2;
            this.connectionPath.moveTo(bottomSpaceHeight, $i$a$2$run);
            this.connectionPath.cubicTo(bottomSpaceHeight, $i$a$2$run, middlePointX, rightTextX, leftTextX, $i$a$2$run);
            this.connectionPath.lineTo(leftTextX, leftTextY);
            this.connectionPath.cubicTo(leftTextX, leftTextY, middlePointX, rightTextX, bottomSpaceHeight, leftTextY);
            this.connectionPath.close();
            if (canvas != null) {
                canvas.drawPath(this.connectionPath, this.switchBackgroundPaint);
            }
        }

        if (canvas != null) {
            canvas.restore();
        }

        Drawable var35 = this.leftIcon;
        Drawable bottomSpaceHeight1;
        if (this.leftIcon != null) {
            bottomSpaceHeight1 = var35;
            if (canvas != null) {
                canvas.save();
            }

            bottomSpaceHeight1.setBounds(iconMarginLeft, iconMarginTop, iconMarginLeft + iconWidth, iconMarginTop + iconHeight);
            bottomSpaceHeight1.setAlpha(this.isSwitchOn ? 153 : 255);
            bottomSpaceHeight1.draw(canvas);
            if (canvas != null) {
                canvas.restore();
            }
        }

        var35 = this.rightIcon;
        if (this.rightIcon != null) {
            bottomSpaceHeight1 = var35;
            if (canvas != null) {
                canvas.save();
            }

            bottomSpaceHeight1.setBounds(this.getMeasuredWidth() - iconWidth - iconMarginRight, iconMarginTop, this.getMeasuredWidth() - iconMarginRight, iconMarginTop + iconHeight);
            bottomSpaceHeight1.setAlpha(!this.isSwitchOn ? 153 : 255);
            bottomSpaceHeight1.draw(canvas);
            if (canvas != null) {
                canvas.restore();
            }
        }

        bottomSpaceHeight = (float) this.getMeasuredHeight() - circleRadius * (float) 2;
        this.leftTextPaint.setColor(this.textColor);
        this.leftTextPaint.setAlpha(this.leftTextAlpha);
        this.rightTextPaint.setColor(this.textColor);
        this.rightTextPaint.setAlpha(this.rightTextAlpha);
        this.leftTextPaint.setTextSize(this.leftTextSize);
        this.rightTextPaint.setTextSize(this.rightTextSize);
        if (this.textVisibility == TextVisibility.VISIBLE) {
            this.measureText();
            double leftTextX1 = (double) (circleRadius * (float) 2 - (float) this.leftTextRect.width()) * 0.5D;
            double leftTextY1 = (double) (circleRadius * (float) 2) + (double) bottomSpaceHeight * 0.5D + (double) this.leftTextRect.height() * 0.25D;
            if (canvas != null) {
                canvas.save();
            }

            if (canvas != null) {
                canvas.drawText(this.leftText, (float) leftTextX1, (float) leftTextY1, this.leftTextPaint);
            }

            if (canvas != null) {
                canvas.restore();
            }

            double rightTextX1 = (double) (circleRadius * (float) 2 - (float) this.rightTextRect.width()) * 0.5D + (double) ((float) this.getMeasuredWidth() - circleRadius * (float) 2);
            double rightTextY = (double) (circleRadius * (float) 2) + (double) bottomSpaceHeight * 0.5D + (double) this.rightTextRect.height() * 0.25D;
            if (canvas != null) {
                canvas.save();
            }

            if (canvas != null) {
                canvas.drawText(this.rightText, (float) rightTextX1, (float) rightTextY, this.rightTextPaint);
            }

            if (canvas != null) {
                canvas.restore();
            }
        }

    }

    private final double evaluateBounceRate(double value) {
        return value * this.animateBounceRate;
    }

    public boolean onTouchEvent(@Nullable MotionEvent event) {
        this.setSwitchOn(!this.isSwitchOn);
        this.animateCheckState(this.isSwitchOn);
        this.notifySelectedChange();
        return super.onTouchEvent(event);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.measureText();
        int diameter = (this.iconPadding + this.iconSize / 2) * 2;
        int textWidth = this.leftTextRect.width() + this.rightTextRect.width();
        int measuredTextHeight = this.textVisibility == TextVisibility.GONE ? 0 : this.selectedTextSize * 2;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch (heightMode) {
            case Integer.MIN_VALUE:
                heightSize = diameter + measuredTextHeight;
                break;
            case 0:
                heightSize = heightMeasureSpec;
                break;
            case 1073741824:
                heightSize = MeasureSpec.getSize(heightMeasureSpec);
        }

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch (widthMode) {
            case Integer.MIN_VALUE:
                widthSize = diameter * 2 + textWidth;
                break;
            case 0:
                widthSize = widthMeasureSpec;
                break;
            case 1073741824:
                widthSize = MeasureSpec.getSize(widthMeasureSpec);
        }

        this.setMeasuredDimension(widthSize, heightSize);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    public final void setDirection(Direction direction, boolean isAnimate) {
        boolean newSwitchState = this.isSwitchOn;
        switch (StickySwitch$WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
            case 1:
                newSwitchState = false;
                break;
            case 2:
                newSwitchState = true;
        }

        if (newSwitchState != this.isSwitchOn) {
            this.setSwitchOn(newSwitchState);
            AnimatorSet var10000 = this.animatorSet;
            if (this.animatorSet != null) {
                var10000.cancel();
            }

            if (isAnimate) {
                this.animateCheckState(this.isSwitchOn);
            } else {
                this.changeCheckState(this.isSwitchOn);
            }

            this.notifySelectedChange();
        }

    }

    // $FF: synthetic method
    // $FF: bridge method

    public static void setDirection$default(StickySwitch var0, Direction var1, boolean var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = true;
        }

        var0.setDirection(var1, var2);
    }


    public final void setDirection(Direction direction) {
        setDirection$default(this, direction, false, 2, (Object) null);
    }


    public final Direction getDirection() {
        boolean var1 = this.isSwitchOn;
        if (var1) {
            return Direction.RIGHT;
        } else if (!var1) {
            return Direction.LEFT;
        } else {
            return Direction.LEFT;
        }
    }


    public final String getText(Direction direction) {
        switch (StickySwitch$WhenMappings.$EnumSwitchMapping$1[direction.ordinal()]) {
            case 1:
                return this.leftText;
            case 2:
                return this.rightText;
            default:
                return this.leftText;
        }
    }

    // $FF: synthetic method
    // $FF: bridge method


    public static String getText$default(StickySwitch var0, Direction var1, int var2, Object var3) {
        if ((var2 & 1) != 0) {
            var1 = var0.getDirection();
        }

        return var0.getText(var1);
    }


    public final String getText() {
        return getText$default(this, (Direction) null, 1, (Object) null);
    }

    public final void setLeftIcon(@DrawableRes int resourceId) {
        this.setLeftIcon(this.getDrawable(resourceId));
    }

    public final void setRightIcon(@DrawableRes int resourceId) {
        this.setRightIcon(this.getDrawable(resourceId));
    }

    private final Drawable getDrawable(@DrawableRes int resourceId) {
        return AppCompatResources.getDrawable(this.getContext(), resourceId);
    }

    private final void notifySelectedChange() {
        OnSelectedChangeListener var10000 = this.onSelectedChangeListener;
        if (this.onSelectedChangeListener != null) {
            var10000.onSelectedChange(this.isSwitchOn ? Direction.RIGHT : Direction.LEFT, getText$default(this, (Direction) null, 1, (Object) null));
        }

    }

    private final void measureText() {
        this.leftTextPaint.getTextBounds(this.leftText, 0, this.leftText.length(), this.leftTextRect);
        this.rightTextPaint.getTextBounds(this.rightText, 0, this.rightText.length(), this.rightTextRect);
    }

    private final void animateCheckState(boolean newCheckedState) {
        this.animatorSet = new AnimatorSet();
        if (this.animatorSet != null) {
            AnimatorSet var10000 = this.animatorSet;
            if (this.animatorSet != null) {
                var10000.playTogether(new Animator[]{this.getLiquidAnimator(newCheckedState), this.leftTextSizeAnimator(newCheckedState), this.rightTextSizeAnimator(newCheckedState), this.leftTextAlphaAnimator(newCheckedState), this.rightTextAlphaAnimator(newCheckedState), this.getBounceAnimator()});
            }

            var10000 = this.animatorSet;
            if (this.animatorSet != null) {
                var10000.start();
            }
        }

    }

    private final void changeCheckState(boolean newCheckedState) {
        this.setLeftTextAlpha(newCheckedState ? this.textAlphaMin : this.textAlphaMax);
        this.setRightTextAlpha(newCheckedState ? this.textAlphaMax : this.textAlphaMin);
        this.setLeftTextSize(newCheckedState ? (float) this.textSize : (float) this.selectedTextSize);
        this.setRightTextSize(newCheckedState ? (float) this.selectedTextSize : (float) this.textSize);
        this.setAnimatePercent(newCheckedState ? 1.0D : 0.0D);
        this.setAnimateBounceRate(1.0D);
    }

    private final Animator leftTextAlphaAnimator(boolean newCheckedState) {
        int toAlpha = newCheckedState ? this.textAlphaMin : this.textAlphaMax;
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{this.leftTextAlpha, toAlpha});
        animator.setInterpolator((TimeInterpolator) (new AccelerateDecelerateInterpolator()));
        animator.setStartDelay(this.animationDuration / (long) 3);
        animator.setDuration(this.animationDuration - this.animationDuration / (long) 3);
        animator.addUpdateListener((ValueAnimator.AnimatorUpdateListener) (new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator it) {
                StickySwitch var10000 = StickySwitch.this;
                Object var10001 = it.getAnimatedValue();
                if (var10001 == null) {
                } else {
                    var10000.setLeftTextAlpha(((Integer) var10001).intValue());
                }
            }
        }));
        return (Animator) animator;
    }

    private final Animator rightTextAlphaAnimator(boolean newCheckedState) {
        int toAlpha = newCheckedState ? this.textAlphaMax : this.textAlphaMin;
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{this.rightTextAlpha, toAlpha});
        animator.setInterpolator((TimeInterpolator) (new AccelerateDecelerateInterpolator()));
        animator.setStartDelay(this.animationDuration / (long) 3);
        animator.setDuration(this.animationDuration - this.animationDuration / (long) 3);
        animator.addUpdateListener((ValueAnimator.AnimatorUpdateListener) (new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator it) {
                StickySwitch var10000 = StickySwitch.this;
                Object var10001 = it.getAnimatedValue();
                if (var10001 == null) {

                } else {
                    var10000.setRightTextAlpha(((Integer) var10001).intValue());
                }
            }
        }));
        return (Animator) animator;
    }

    private final Animator leftTextSizeAnimator(boolean newCheckedState) {
        int toTextSize = newCheckedState ? this.textSize : this.selectedTextSize;
        ValueAnimator textSizeAnimator = ValueAnimator.ofFloat(new float[]{this.leftTextSize, (float) toTextSize});
        textSizeAnimator.setInterpolator((TimeInterpolator) (new AccelerateDecelerateInterpolator()));
        textSizeAnimator.setStartDelay(this.animationDuration / (long) 3);
        textSizeAnimator.setDuration(this.animationDuration - this.animationDuration / (long) 3);
        textSizeAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener) (new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator it) {
                StickySwitch var10000 = StickySwitch.this;
                Object var10001 = it.getAnimatedValue();
                if (var10001 == null) {
                } else {
                    var10000.setLeftTextSize(((Float) var10001).floatValue());
                }
            }
        }));
        return (Animator) textSizeAnimator;
    }

    private final Animator rightTextSizeAnimator(boolean newCheckedState) {
        int toTextSize = newCheckedState ? this.selectedTextSize : this.textSize;
        ValueAnimator textSizeAnimator = ValueAnimator.ofFloat(new float[]{this.rightTextSize, (float) toTextSize});
        textSizeAnimator.setInterpolator((TimeInterpolator) (new AccelerateDecelerateInterpolator()));
        textSizeAnimator.setStartDelay(this.animationDuration / (long) 3);
        textSizeAnimator.setDuration(this.animationDuration - this.animationDuration / (long) 3);
        textSizeAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener) (new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator it) {
                StickySwitch var10000 = StickySwitch.this;
                Object var10001 = it.getAnimatedValue();
                if (var10001 == null) {
                } else {
                    var10000.setRightTextSize(((Float) var10001).floatValue());
                }
            }
        }));
        return (Animator) textSizeAnimator;
    }

    private final Animator getLiquidAnimator(boolean newCheckedState) {
        ValueAnimator liquidAnimator = ValueAnimator.ofFloat(new float[]{(float) this.animatePercent, newCheckedState ? 1.0F : 0.0F});
        liquidAnimator.setDuration(this.animationDuration);
        liquidAnimator.setInterpolator((TimeInterpolator) (new AccelerateInterpolator()));
        liquidAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener) (new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator it) {
                StickySwitch var10000 = StickySwitch.this;
                Object var10001 = it.getAnimatedValue();
                if (var10001 == null) {
                } else {
                    var10000.setAnimatePercent((double) ((Float) var10001).floatValue());
                }
            }
        }));
        return (Animator) liquidAnimator;
    }

    private final Animator getBounceAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{1.0F, 0.9F, 1.0F});
        animator.setDuration((long) ((double) this.animationDuration * 0.41D));
        animator.setStartDelay(this.animationDuration);
        animator.setInterpolator((TimeInterpolator) (new DecelerateInterpolator()));
        animator.addUpdateListener((ValueAnimator.AnimatorUpdateListener) (new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator it) {
                StickySwitch var10000 = StickySwitch.this;
                Object var10001 = it.getAnimatedValue();
                if (var10001 == null) {
                } else {
                    var10000.setAnimateBounceRate((double) ((Float) var10001).floatValue());
                }
            }
        }));
        return (Animator) animator;
    }

    public StickySwitch(Context context) {
        this(context, (AttributeSet) null);
    }

    public StickySwitch(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickySwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.TAG = "LIQUID_SWITCH";
        this.iconSize = 100;
        this.iconPadding = 70;
        this.leftText = "";
        this.rightText = "";
        this.sliderBackgroundColor = (int) 4279769121L;
        this.switchColor = (int) 4280513018L;
        this.textColor = (int) 4294967295L;
        this.sliderBackgroundPaint = new Paint();
        this.sliderBackgroundRect = new RectF();
        this.switchBackgroundPaint = new Paint();
        this.leftTextPaint = new Paint();
        this.leftTextRect = new Rect();
        this.rightTextPaint = new Paint();
        this.rightTextRect = new Rect();
        this.leftTextSize = 50.0F;
        this.rightTextSize = 50.0F;
        this.textAlphaMax = 255;
        this.textAlphaMin = 163;
        this.leftTextAlpha = this.textAlphaMax;
        this.rightTextAlpha = this.textAlphaMin;
        this.textSize = 50;
        this.selectedTextSize = 50;
        this.animateBounceRate = 1.0D;
        this.animationType = AnimationType.LINE;
        this.animationDuration = 600L;
        this.textVisibility = TextVisibility.VISIBLE;
        this.connectionPath = new Path();
        this.xParam = 0.5F;
        this.yParam = 0.8660254F;
        init$default(this, attrs, defStyleAttr, 0, 4, (Object) null);
    }

    @TargetApi(21)
    public StickySwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.TAG = "LIQUID_SWITCH";
        this.iconSize = 100;
        this.iconPadding = 70;
        this.leftText = "";
        this.rightText = "";
        this.sliderBackgroundColor = (int) 4279769121L;
        this.switchColor = (int) 4280513018L;
        this.textColor = (int) 4294967295L;
        this.sliderBackgroundPaint = new Paint();
        this.sliderBackgroundRect = new RectF();
        this.switchBackgroundPaint = new Paint();
        this.leftTextPaint = new Paint();
        this.leftTextRect = new Rect();
        this.rightTextPaint = new Paint();
        this.rightTextRect = new Rect();
        this.leftTextSize = 50.0F;
        this.rightTextSize = 50.0F;
        this.textAlphaMax = 255;
        this.textAlphaMin = 163;
        this.leftTextAlpha = this.textAlphaMax;
        this.rightTextAlpha = this.textAlphaMin;
        this.textSize = 50;
        this.selectedTextSize = 50;
        this.animateBounceRate = 1.0D;
        this.animationType = AnimationType.LINE;
        this.animationDuration = 600L;
        this.textVisibility = TextVisibility.VISIBLE;
        this.connectionPath = new Path();
        this.xParam = 0.5F;
        this.yParam = 0.8660254F;
        this.init(attrs, defStyleAttr, defStyleRes);
    }

    // $FF: synthetic method
    public static final int access$getLeftTextAlpha$p(StickySwitch $this) {
        return $this.leftTextAlpha;
    }

    // $FF: synthetic method
    public static final int access$getRightTextAlpha$p(StickySwitch $this) {
        return $this.rightTextAlpha;
    }

    // $FF: synthetic method
    public static final float access$getLeftTextSize$p(StickySwitch $this) {
        return $this.leftTextSize;
    }

    // $FF: synthetic method
    public static final float access$getRightTextSize$p(StickySwitch $this) {
        return $this.rightTextSize;
    }

    // $FF: synthetic method
    public static final double access$getAnimatePercent$p(StickySwitch $this) {
        return $this.animatePercent;
    }

    // $FF: synthetic method
    public static final double access$getAnimateBounceRate$p(StickySwitch $this) {
        return $this.animateBounceRate;
    }

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View) this._$_findViewCache.get(Integer.valueOf(var1));
        if (var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(Integer.valueOf(var1), var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }

    public static enum AnimationType {
        LINE,
        CURVED;
    }

    public static enum TextVisibility {
        VISIBLE,
        INVISIBLE,
        GONE;
    }


    public static enum Direction {
        LEFT,
        RIGHT;
    }


    public interface OnSelectedChangeListener {
        void onSelectedChange(Direction var1, String var2);
    }
}
