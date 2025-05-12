package org.example.project3.patterns.factory;


import com.example.bodybuild.dao.*;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Supplier;

public class FactoryDAO {
    private FactoryDAO(){}
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();
    private static final String PERSISTENCE_TYPE = "persistence.type";
    private static LoginAndRegistrationDAO loginAndRegistrationDAO;
    private static ExAndScheduleDAO exAndScheduleDAO;
    private static UpdateDAO updateDAO;
    private static RequestDAO requestDAO;
    private static RetrieveDAO retrieveDAO;

    // Caricamento delle proprietà una sola volta
    private static void loadProperties() {
        try (InputStream input = FactoryDAO.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IOException("Properties file not found: " + CONFIG_FILE);
            }
            properties.load(input);
        } catch (IOException e) {
            Printer.errorPrint("Error loading properties file: " + e.getMessage());
        }
    }

    // Verifica che il tipo di persistenza sia valido
    private static String getPersistenceType() {
        if(properties.isEmpty()){
            loadProperties();
        }
        String type = properties.getProperty(PERSISTENCE_TYPE);
        if (type == null) {
            throw new IllegalArgumentException("Persistence type not found in properties file.");
        }
        return type;
    }

    // Metodo generico per creare DAO
    private static <T> T createDAO(String type, Supplier<T> mysqlSupplier, Supplier<T> demoSupplier, Supplier<T> jsonSupplier) {
        return switch (type) {
            case "mysql" -> mysqlSupplier.get();
            case "demo" -> demoSupplier.get();
            case "json" -> {
                if (jsonSupplier == null) {
                    throw new IllegalArgumentException("DAO JSON non supportato.");
                }
                yield jsonSupplier.get();
            }
            default -> throw new IllegalArgumentException("Tipo di DAO non valido: " + type);
        };
    }
}
