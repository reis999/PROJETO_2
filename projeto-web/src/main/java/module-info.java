module projeto.web {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires estg.ipvc.projeto.data;
    requires jakarta.persistence;
    requires spring.beans;
    requires spring.context;
    requires spring.web;
    requires spring.core;
    requires org.apache.tomcat.embed.core;
    requires spring.boot.actuator;
    requires spring.tx;

    exports estg.ipvc.projetoweb.App;
    opens estg.ipvc.projetoweb.App;
}



