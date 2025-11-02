package org.emp.gl.timer.service;

import java.beans.PropertyChangeListener;

/**
 * Interface pour les observateurs du TimerService.
 * Elle hérite de PropertyChangeListener afin de fonctionner
 * avec PropertyChangeSupport sans erreurs de concurrence.
 */
public interface TimerChangeListener extends PropertyChangeListener {

    String DIXEME_DE_SECONDE_PROP = "dixieme";
    String SECONDE_PROP = "seconde";
    String MINUTE_PROP = "minute";
    String HEURE_PROP = "heure";
}
