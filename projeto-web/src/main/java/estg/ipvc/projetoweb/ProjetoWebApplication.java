package estg.ipvc.projetoweb;

import estg.ipvc.projeto.data.BLL.DBConnect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoWebApplication {

    public static void main(String[] args) {

        if(DBConnect.getEntityManager() == null){
            System.out.println("Erro ao conectar à base de dados");
            return;
        }



        SpringApplication.run(ProjetoWebApplication.class, args);
    }

}
