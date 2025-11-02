package org.emp.gl.clients;

import java.beans.PropertyChangeEvent;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

public class CompteARebours implements TimerChangeListener {

    private final String name;
    private int valeur;
    private final TimerService timerService;

    public CompteARebours(String name, TimerService timerService, int valeurInitiale) {
        this.name = name;
        this.timerService = timerService;
        this.valeur = valeurInitiale;

        // S’inscrire comme observateur
        timerService.addTimeChangeListener(this);

        System.out.println("Compte à rebours " + name + " démarré à " + valeurInitiale + " secondes");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // On ne réagit qu’à la modification des secondes
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            if (valeur > 0) {
                System.out.println(name + " : " + valeur);
                valeur--;

                // Se désinscrire quand le compte atteint 0
                if (valeur == 0) {
                    System.out.println(name + " terminé !");
                    timerService.removeTimeChangeListener(this);
                }
            }
        }
    }
}
