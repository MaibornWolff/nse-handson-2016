Badge-Reader
============

Die Leser sind als normale Tastatur angemeldet. Damit Java ohne JNI die Events bekommt,
muss die Applikation also den Fokus haben. Dazu den Badge-Reader nicht mit docker-compose,
sondern mit dem start-script (innerhalb der VM) starten:

        start-badge-reader.sh
        
Das Script kann zum Ausprobieren innerhalb der handson1-3 ausgeführt werden und es wird jeweils deviceId 1-3 verwendet.


Manuell
-------
Die Software kann auch ohne den Badge-Reader verwendet werden. Einfach selbst eine 10-Stellige, Numerische Zahl eingeben und Enter drücken.