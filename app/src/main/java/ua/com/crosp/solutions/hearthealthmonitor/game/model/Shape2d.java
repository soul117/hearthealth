package ua.com.crosp.solutions.hearthealthmonitor.game.model;

public abstract class Shape2d extends BaseGameObject {
    protected float mLeft;
    protected float mRight;
    protected float mTop;
    protected float mBottom;
    protected int mColor;
    protected float mTopInitial, mBottomInitial, mLeftInitial, mRightInitial;

    protected VectorGeometry.VelocityVector mVelocityVector;

    public Shape2d() {
    }

    public Shape2d(float left, float top, float right, float bottom) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
        // Set initial values
        mTopInitial = mTop;
        mBottomInitial = mBottom;
        mLeftInitial = mLeft;
        mRightInitial = mRight;
    }

    public float getTopInitial() {
        return mTopInitial;
    }

    public void setTopInitial(float topInitial) {
        mTopInitial = topInitial;
    }

    public float getBottomInitial() {
        return mBottomInitial;
    }

    public void setBottomInitial(float bottomInitial) {
        mBottomInitial = bottomInitial;
    }

    public float getLeftInitial() {
        return mLeftInitial;
    }

    public void setLeftInitial(float leftInitial) {
        mLeftInitial = leftInitial;
    }

    public float getRightInitial() {
        return mRightInitial;
    }

    public void setRightInitial(float rightInitial) {
        mRightInitial = rightInitial;
    }

    public float getLeft() {
        return mLeft;
    }

    public void setLeft(float left) {
        mLeft = left;
    }

    public float getRight() {
        return mRight;
    }

    public void setRight(float right) {
        mRight = right;
    }

    public float getTop() {
        return mTop;
    }

    public void setTop(float top) {
        mTop = top;
    }

    public float getBottom() {
        return mBottom;
    }

    public void setBottom(float bottom) {
        mBottom = bottom;
    }


    /**
     * @param other Another 2d shape
     * @return Whether this shape is intersecting with the other.
     */
    public boolean isIntersecting(Shape2d other) {
        return getLeft() <= other.getRight() && getRight() >= other.getLeft()
                && getTop() <= other.getBottom() && getBottom() >= other.getTop();
    }

    /**
     * @param x An x coordinate
     * @param y A y coordinate
     * @return Whether the point is within this shape
     */
    public boolean isPointWithin(float x, float y) {
        return (x > getLeft() && x < getRight()
                && y > getTop() && y < getBottom());

    }

    public VectorGeometry.VelocityVector getVelocityVector() {
        return mVelocityVector;
    }

    public void setVelocityVector(VectorGeometry.VelocityVector velocityVector) {
        mVelocityVector = velocityVector;
    }

    public void setVelocityVectorYValue(float yValue) {
        mVelocityVector.y = yValue;
    }

    public void setVelocityVectorXValue(float xValue) {
        mVelocityVector.x = xValue;
    }


    public float getArea() {
        return getHeight() * getWidth();
    }

    public float getHeight() {
        return getBottom() - getTop();
    }

    public float getWidth() {
        return getRight() - getLeft();
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public boolean hasSameColor(Shape2d another) {
        return another.getColor() == this.getColor();
    }

}
