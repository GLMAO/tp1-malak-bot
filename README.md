# IASD 28

# 🧭 Compte rendu – TP1 : Design Pattern Observer (Java – Génie Logiciel)

## 🎯 Objectif du TP

L’objectif de ce TP est de comprendre et de mettre en œuvre le **Design Pattern Observer** à travers un projet multi-modules Maven en Java.  
Le but est de disposer d’un **service de temps** qui notifie plusieurs **observateurs** (comme des horloges ou des comptes à rebours) à chaque changement de seconde.

---

## 🧱 Structure du projet

Le projet fourni est déjà organisé en plusieurs modules Maven :

```
tp1-observer/
│
├── timer-service/         → contient les interfaces abstraites (TimerService, TimerChangeListener)
├── time-service-impl/     → contient l’implémentation concrète du service de temps (DummyTimeServiceImpl)
├── timer-service-client/  → contient les classes clientes observatrices (Horloge, CompteARebours)
└── launcher/              → contient la classe App (point d’entrée du programme)
```

Cette structure permet une bonne séparation entre l’abstraction, l’implémentation et les clients.

---

## 🧩 Étapes d’implémentation

### 1️⃣ Étude du code existant

Le projet contenait déjà :
- L’interface **`TimerService`**, qui définit les méthodes pour obtenir l’heure (heures, minutes, secondes, dixièmes de seconde).  
- L’interface **`TimerChangeListener`**, chargée de réagir à un changement de temps.  
- L’implémentation **`DummyTimeServiceImpl`**, qui simule le passage du temps grâce à un `Timer` Java.

Ce service exécute périodiquement une tâche toutes les 100 millisecondes pour mettre à jour l’heure et notifier les observateurs.

---

### 2️⃣ Ajout et adaptation de la classe **Horloge**

Nous avons complété la classe **Horloge** afin qu’elle affiche l’heure à chaque seconde.  
Pour cela :
- Elle implémente `TimerChangeListener`,
- Elle s’inscrit auprès du `TimerService` grâce à `addTimeChangeListener(this)`,
- Elle réagit uniquement lorsque la propriété `"seconde"` change,
- Elle affiche ensuite l’heure actuelle sur la console.

Cela permet d’avoir plusieurs horloges indépendantes recevant les notifications d’un même service de temps.

---

### 3️⃣ Ajout de la classe **CompteARebours**

Nous avons ensuite ajouté une nouvelle classe `CompteARebours`, qui représente un minuteur.  
Cette classe :
- reçoit une valeur initiale (nombre de secondes),
- se décrémente à chaque changement de seconde,
- et se **désinscrit automatiquement** du service lorsque la valeur atteint 0.

Ce comportement illustre parfaitement le rôle d’un observateur qui se retire du système quand il n’a plus besoin d’être notifié.

---

### 4️⃣ Tests avec plusieurs observateurs

Dans la classe `App`, nous avons instancié plusieurs horloges et plusieurs comptes à rebours 

L’exécution montre bien que tous les objets reçoivent les notifications du service en parallèle.  
Cependant, lorsqu’un grand nombre d’observateurs étaient créés, des erreurs de concurrence apparaissaient (`ConcurrentModificationException`).

---

### 5️⃣ Résolution des erreurs de concurrence

Pour corriger ces problèmes, nous avons modifié la classe `DummyTimeServiceImpl` :  
la gestion manuelle de la liste des observateurs a été remplacée par un objet **`PropertyChangeSupport`**.  

Ce dernier est fourni par Java et gère automatiquement :
- l’ajout et la suppression d’observateurs,
- l’envoi des notifications,
- la synchronisation entre les threads.

Les appels à la méthode :
```java
listener.propertyChange(...);
```
ont été remplacés par :
```java
support.firePropertyChange(...);
```

Et dans les observateurs (`Horloge` et `CompteARebours`), la méthode :
```java
public void propertyChange(String prop, Object oldValue, Object newValue)
```
a été remplacée par la version standard :
```java
public void propertyChange(PropertyChangeEvent evt)
```

---

### 6️⃣ Résultat final

Après ces modifications :
- plusieurs **Horloges** et **Comptes à rebours** peuvent s’exécuter en parallèle,
- aucun conflit ou bogue de concurrence n’apparaît,
- le code est plus **robuste**, **modulaire** et **proche des bonnes pratiques** Java.


## ✅ Conclusion

À travers ce TP, nous avons :
- Compris le **principe du Design Pattern Observer**,  
- Observé la différence entre **abstraction** et **implémentation**,  
- Ajouté et testé plusieurs observateurs,  
- Résolu les problèmes de **concurrence** grâce à `PropertyChangeSupport`.

Le système final est stable, extensible et respecte les principes de la **programmation orientée objet** et du **faible couplage** entre composants.
