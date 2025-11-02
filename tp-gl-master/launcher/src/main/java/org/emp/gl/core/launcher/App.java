package org.emp.gl.core.launcher;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;

import java.util.Random;

import org.emp.gl.clients.CompteARebours;
import org.emp.gl.clients.Horloge;

public class App {

    public static void main(String[] args) {
        testDuTimeService();
    }

    private static void testDuTimeService() {
        // 1️⃣ Créer le service de temps (implémentation concrète)
        TimerService timerService = new DummyTimeServiceImpl();

        // 2️⃣ Créer plusieurs horloges et leur injecter le service (abstraction)
        Horloge horloge1 = new Horloge("Horloge 1", timerService);
        Horloge horloge2 = new Horloge("Horloge 2", timerService);
        Horloge horloge3 = new Horloge("Horloge 3", timerService);

        // Les horloges s'affichent automatiquement à chaque seconde
        Random rand = new Random();

        // Créer 10 comptes à rebours avec valeurs entre 10 et 20
        for (int i = 1; i <= 10; i++) {
            int valeur = rand.nextInt(11) + 10; // 10 à 20
            new CompteARebours("C" + i, timerService, valeur);
        }
    }

    // Méthode optionnelle pour nettoyer la console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
