open module estg.ipvc.projeto.data {
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires com.fasterxml.jackson.annotation;

    exports estg.ipvc.projeto.data.BLL;
    exports estg.ipvc.projeto.data.Entity;
    exports estg.ipvc.projeto.data;
}