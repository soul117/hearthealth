package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class CTBGameScore {

    // Total score points
    public int mTotalMissedBallsCount = 0;
    public int mTotalIncorrectBallsCount = 0;
    public int mTotalCorrectBallCount = 0;

    // Intermediate score points
    public transient int mMissedBallsCount = 0;
    public transient int mIncorrectBallsCount = 0;
    public transient int mCorrectBallCount = 0;


    public int getMissedBallsCount() {
        return mMissedBallsCount;
    }

    public void setMissedBallsCount(int missedBallsCount) {
        this.mMissedBallsCount = missedBallsCount;
    }

    public int getIncorrectBallsCount() {
        return mIncorrectBallsCount;
    }

    public void setIncorrectBallsCount(int incorrectBallsCount) {
        this.mIncorrectBallsCount = incorrectBallsCount;
    }

    public int getCorrectBallCount() {
        return mCorrectBallCount;
    }

    public void setCorrectBallCount(int correctBallCount) {
        this.mCorrectBallCount = correctBallCount;
    }

    public int getTotalMissedBallsCount() {
        return mTotalMissedBallsCount;
    }

    public int getTotalIncorrectBallsCount() {
        return mTotalIncorrectBallsCount;
    }

    public int getTotalCorrectBallCount() {
        return mTotalCorrectBallCount;
    }

    //Increment
    public void incrementCorrectBallsCount() {
        this.mTotalCorrectBallCount++;
        this.mCorrectBallCount++;
    }

    public void incrementMissedBallsCount() {
        this.mTotalMissedBallsCount++;
        this.mMissedBallsCount++;
    }

    public void incrementIncorrectBallsCount() {
        this.mTotalIncorrectBallsCount++;
        this.mIncorrectBallsCount++;
    }

    // Decrement
    public void decrementCorrectBallsCount() {
        this.mTotalCorrectBallCount--;
        this.mCorrectBallCount--;
    }

    public void decrementMissedBallsCount() {
        this.mTotalMissedBallsCount--;
        this.mMissedBallsCount--;
    }

    public void decrementIncorrectBallsCount() {
        this.mTotalIncorrectBallsCount--;
        this.mIncorrectBallsCount--;
    }

    // Clear
    public void clearCorrectBallCount() {
        this.mCorrectBallCount = 0;
    }

    public void clearIncorrectBallCount() {
        this.mIncorrectBallsCount = 0;
    }

    public void clearMissedBallCount() {
        this.mMissedBallsCount = 0;
    }

}
