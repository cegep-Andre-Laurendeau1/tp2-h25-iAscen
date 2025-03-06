package ca.cal.tp2;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        // Votre script qui utilise votre API ici
        TcpServer.createTcpServer();
        Thread.currentThread().join();
    }
}
