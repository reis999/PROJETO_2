module projeto.web {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires estg.ipvc.projeto.data;
    requires jakarta.persistence;

    exports estg.ipvc.projetoweb to spring.core;
    
}