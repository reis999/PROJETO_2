module projeto.web {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires estg.ipvc.projeto.data;
    requires jakarta.persistence;
    requires spring.beans;
    requires spring.context;
    requires spring.web;
    requires spring.core;

    exports estg.ipvc.projetoweb;
    exports estg.ipvc.projetoweb.Controllers to spring.beans, spring.context, spring.web;
    exports estg.ipvc.projetoweb.Services.impl to spring.beans;

    opens estg.ipvc.projetoweb to spring.core, spring.beans, spring.context, spring.web;
}


