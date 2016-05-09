FROM yanana/nginx-consul-template

ADD app.conf.ctmpl /etc/consul-templates/app.conf.ctmpl
# Swagger funktioniert nicht über den Proxy - irgendwas mit den Ports. Da wir den aber auch nicht
# brauchen und ich gerade keine Lust mehr habe, lasse ich das jetzt so.

CMD ["my_init"]