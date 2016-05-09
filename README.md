NSE Handson Microservices
=========================

Kurze Erklärung, wie man die Services in der Vagrant-Box startet:

1. Vagrant starten:

        vagrant plugin install vagrant-reload
        vagrant up
        
2. Warten bis die Maschinen hochgefahren sind. Sobald handson-server durch ist, kann unter <http://192.168.33.100:8500> der Status der einzelnen Services verfolgt werden.
Irgendwann wenn alles grün ist, sind alle Services auf allen drei Nodes da.

Es laufen drei maschinen:

* handson-server (192.168.33.100)
* handson1-3 (192.168.33.111 - 113)

3. Im o.g. consul UI kann man die jeweiligen Ports der Services sehen und unter <http://192.168.33.112:[port]/swagger-ui.html> die jeweilige Swagger-UI aufrufen.

4. Um einen Device-Event zu simulieren also z.B. auf <http://192.168.33.112:8081/swagger-ui.html>


Server und node als echten Server im Netzwerk laufen lassen
--------------------------------------------------
Standardmäßig werden die Vagrant-Internen IPs verwendet. Damit der Server im "echten" Netzwerk als Server fungieren kann,
muss nur eine Systemvariable angepasst und docker-compose neu gestartet werden.

1. Auf der Host-Maschine muss die Variable HANDSON_SERVER mit der eigenen IP belegt werden. Angenommen wir haben die IP 192.168.1.28:

        export HANDSON_SERVER=192.168.1.28

2. Jetzt einfach den Server starten und Consul exposed sich selbst mit dieser IP:
        
        vagrant up handson-server


Handson-DEV Setup für die Teilnehmer
====================================
IntelliJ
--------
Gradle konfiguriert sich dynamisch, d.h. es wird immer das komplette Projekt aus git ausgecheckt. Abhängig davon ob das root-Verzeichnis oder eines der Verzeichnisse der einzelnen Services
in IntelliJ importiert wird, erstellt gradle dann entweder das komplette Projekt oder nur das jeweilige Service Projekt.

Vagrant Maschine handson-dev
-----------
handson-dev ist die Maschine, die beim Handson verwendet wird. 
Sie ist eine einzelne und bekommt je einen individuellen hostnamen (handson-dev-<username>). Außerdem werden alle relevanten Ports vom Host aus weitergeleitet.
Um der Maschine die IP des Servers mitzugeben, muss auch hier einfach am Host-System die Variable HANDSON_SERVER gesetzt sein.
Maschine startet standardmäßig nicht, muss explizit gestartet werden:

        export HANDSON_SERVER=<server-ip>
        vagrant up handson-dev
        
Sie ist so konfiguriert, dass Dienste sich automatisch bei Consul mit der IP des Vagrant Host-
Systems anmelden, außerdem sind die Ports 8080-8089 weitergeleitet, sodass die Services out-of-the-box darin funktionieren sollten.

Außerdem mountet sie immer "." auf /handson. Damit hat jeder der Handson-Teilnehmer seinen eigenen Service in /handson liegen. Voraussetzung dafür ist, dass vagrant up aus dem jeweiligen Service-Verzeichnis ausgeführt wird. Über die Terminal-View ist dieses Verzeichnis in IntelliJ dann ohnehin ausgewählt.
