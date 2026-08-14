package com.almoxarifado.database.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class  DBConnection {

    private static final String DEFAULT_FILE =
            System.getProperty("user.home") + File.separator + ".almoxarifado"
                    + File.separator + "almoxarifado.db";


    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        String path = System.getProperty("almoxarifado.db.path", DEFAULT_FILE);
        File dbFile = new File(path);
        File folder = dbFile.getParentFile();
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
        return DriverManager.getConnection("jdbc:sqlite:" + path);
    }
}
