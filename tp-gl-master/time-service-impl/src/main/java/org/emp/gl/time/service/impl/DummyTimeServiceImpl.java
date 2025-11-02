package org.emp.gl.time.service.impl;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.beans.PropertyChangeSupport;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

public class DummyTimeServiceImpl implements TimerService {

    private int dixiemeDeSeconde;
    private int secondes;
    private int minutes;
    private int heures;

    // ✅ Support pour gérer les observateurs
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public DummyTimeServiceImpl() {
        setTimeValues();

        // Timer Java pour exécuter timeChanged() toutes les 100 ms
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeChanged();
            }
        };
        timer.scheduleAtFixedRate(task, 100, 100);
    }

    private void setTimeValues() {
        LocalTime localTime = LocalTime.now();

        setDixiemeDeSeconde(localTime.getNano() / 100_000_000);
        setSecondes(localTime.getSecond());
        setMinutes(localTime.getMinute());
        setHeures(localTime.getHour());
    }

    private void timeChanged() {
        setTimeValues();
    }

    // === 🔹 Gestion des observateurs ===
    @Override
    public void addTimeChangeListener(TimerChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removeTimeChangeListener(TimerChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // === 🔹 Méthodes de notification ===
    public void setDixiemeDeSeconde(int newValue) {
        if (dixiemeDeSeconde != newValue) {
            int oldValue = dixiemeDeSeconde;
            dixiemeDeSeconde = newValue;
            support.firePropertyChange(TimerChangeListener.DIXEME_DE_SECONDE_PROP, oldValue, newValue);
        }
    }

    public void setSecondes(int newValue) {
        if (secondes != newValue) {
            int oldValue = secondes;
            secondes = newValue;
            support.firePropertyChange(TimerChangeListener.SECONDE_PROP, oldValue, newValue);
        }
    }

    public void setMinutes(int newValue) {
        if (minutes != newValue) {
            int oldValue = minutes;
            minutes = newValue;
            support.firePropertyChange(TimerChangeListener.MINUTE_PROP, oldValue, newValue);
        }
    }

    public void setHeures(int newValue) {
        if (heures != newValue) {
            int oldValue = heures;
            heures = newValue;
            support.firePropertyChange(TimerChangeListener.HEURE_PROP, oldValue, newValue);
        }
    }

    // === 🔹 Getters ===
    @Override
    public int getDixiemeDeSeconde() { return dixiemeDeSeconde; }
    @Override
    public int getSecondes() { return secondes; }
    @Override
    public int getMinutes() { return minutes; }
    @Override
    public int getHeures() { return heures; }
}
