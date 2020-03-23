package me.grsan.solidpets.util;

import me.grsan.solidpets.SolidPets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

public class Config {

    private File configFile;
    private JSONObject configJson;
    private JSONParser jsonParser;

    public Config(File configFile) throws FileNotFoundException, UnsupportedEncodingException {
        this.configFile = configFile;
        if (!configFile.getParentFile().exists()) {
            if (!configFile.getParentFile().mkdirs())
                SolidPets.getInstance().getLogger().log(Level.SEVERE, "Couldn't create config dirs!");
            if (!configFile.exists()) {
                PrintWriter pw = new PrintWriter(configFile, "UTF-8");
                pw.println("{}");
                pw.flush();
                pw.close();
            }
        }
    }

    public void reload() {

    }

    public JSONObject getConfigJson() {
        return configJson;
    }
}
