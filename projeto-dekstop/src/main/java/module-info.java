module estg.ipvc.projetodekstop {
    requires javafx.controls;
    requires javafx.fxml;
    requires estg.ipvc.projeto.data;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens estg.ipvc.projetodekstop to javafx.fxml;
    exports estg.ipvc.projetodekstop;
    exports estg.ipvc.projetodekstop.Controllers;
    opens estg.ipvc.projetodekstop.Controllers to javafx.fxml;
}