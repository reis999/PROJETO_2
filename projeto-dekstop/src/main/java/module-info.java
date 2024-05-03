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
    exports estg.ipvc.projetodekstop.Controllers.Admin;
    opens estg.ipvc.projetodekstop.Controllers.Admin to javafx.fxml;
    exports estg.ipvc.projetodekstop.Controllers.GestorVenda;
    opens estg.ipvc.projetodekstop.Controllers.GestorVenda to javafx.fxml;
    exports estg.ipvc.projetodekstop.Controllers.GestorProd;
    opens estg.ipvc.projetodekstop.Controllers.GestorProd to javafx.fxml;
}