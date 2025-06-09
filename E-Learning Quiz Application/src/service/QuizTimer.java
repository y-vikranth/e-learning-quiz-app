package service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * QuizTimer class handles countdown functionality for the quiz.
 * It uses javax.swing.Timer to tick every 1 second.
 */
public class QuizTimer {

    private int remainingSeconds; // Total time left in seconds
    private Timer timer; // Swing Timer that ticks every 1 second
    private TimerListener listener; // Callback to notify QuizScreen when time changes or finishes

    /**
     * Constructor for QuizTimer.
     * @param totalSeconds Total time allowed for the quiz in seconds.
     * @param listener A listener interface to notify UI about timer updates and completion.
     */
    public QuizTimer(int totalSeconds, TimerListener listener) {
        this.remainingSeconds = totalSeconds;
        this.listener = listener;

        // Initialize the Swing Timer to tick every 1000 ms (1 second)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick(); // Every tick reduces time by 1 second
            }
        });
    }

    /**
     * Starts the countdown timer.
     */
    public void start() {
        timer.start();
    }

    /**
     * Stops the countdown timer.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Main countdown logic called every second by Swing Timer.
     * It updates remaining time and notifies listener.
     * When time reaches 0, it stops the timer and notifies the listener that time is up.
     */
    private void tick() {
        if (remainingSeconds > 0) {
            remainingSeconds--; // Decrement time
            listener.onTimerTick(remainingSeconds); // Inform UI to update displayed time
        } else {
            timer.stop(); // Stop when time runs out
            listener.onTimerFinish(); // Notify UI that time is over
        }
    }

    /**
     * Returns how many seconds are left.
     * @return Remaining time in seconds.
     */
    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    /**
     * Interface to be implemented by classes (like QuizScreen)
     * that want to receive timer updates and completion notifications.
     */
    public interface TimerListener {
        void onTimerTick(int remainingSeconds); // Called every second
        void onTimerFinish(); // Called when time finishes
    }
}
