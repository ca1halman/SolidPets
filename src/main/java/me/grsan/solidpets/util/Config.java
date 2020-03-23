package me.grsan.solidpets.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.grsan.solidpets.SolidPets;
import me.grsan.solidpets.pets.Pet;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

public class Config {

    private File configFile;
    private JSONObject configJson;
    private JSONParser jsonParser;

    private HashMap<String, Object> defaultJson = new HashMap<>();

    public Config(File configFile) throws FileNotFoundException, UnsupportedEncodingException {
        this.configFile = configFile;

        if (!configFile.getParentFile().exists()) {
            if (!configFile.getParentFile().mkdirs())
                SolidPets.getInstance().getLogger().log(Level.SEVERE, "Couldn't create config dirs!");
        }
        if (!configFile.getAbsoluteFile().exists()) {
            PrintWriter pw = new PrintWriter(configFile, "UTF-8");
            pw.println("{\"owners\":[],\"pets\":{}}");
            pw.flush();
            pw.close();
        }
        this.jsonParser = new JSONParser();
        reload();
    }

    public void reload() {
        try {
            configJson = (JSONObject) jsonParser.parse(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        TreeMap<String, Object> outputMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        outputMap.putAll(configJson);

        Gson g = new GsonBuilder().setPrettyPrinting().create();
        String prettyJsonString = g.toJson(outputMap);

        try {
            FileWriter fw = new FileWriter(configFile);
            fw.write(prettyJsonString);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public JSONObject getRawJson() {
        return configJson;
    }

    public HashMap<OfflinePlayer, ArrayList<Pet>> getPlayerPets() {
        HashMap<OfflinePlayer, ArrayList<Pet>> ret = new HashMap<>();
        JSONArray ownerArray = (JSONArray)configJson.get("owners");
        for (Object obj : ownerArray) {
            JSONObject ownerObj = (JSONObject) obj;
            OfflinePlayer owner = SolidPets.getInstance().getServer().getOfflinePlayer(UUID.fromString((String)ownerObj.get("uuid")));

            ArrayList<Pet> ownedPets = new ArrayList<>();
            JSONArray ownedPetsJsonArray = (JSONArray) ownerObj.get("owns");
            for (Object owned : ownedPetsJsonArray) {
                UUID uuid = UUID.fromString((String) owned);
                JSONObject petJson = (JSONObject) ((JSONObject) configJson.get("pets")).get((String) owned);

                Pet pet = new Pet(owner,Rarity.valueOf((String)petJson.get("rarity")), uuid, (String)petJson.get("type"));

                ownedPets.add(pet);
            }

            ret.put(owner, ownedPets);
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    public void savePlayerPets() {
        HashMap<OfflinePlayer, ArrayList<Pet>> playerPets = SolidPets.getInstance().getPlayerPets();
        JSONArray owners = new JSONArray();
        JSONObject pets = new JSONObject();

        for(OfflinePlayer owner : playerPets.keySet()) {
            JSONObject ownerObject = new JSONObject();
            ownerObject.put("uuid", owner.getUniqueId().toString());

            JSONArray owns = new JSONArray();
            for (Pet petOwned : playerPets.get(owner)) {
                owns.add(petOwned.getUuid().toString());

                pets.put(petOwned.getUuid().toString(), petOwned.serialize());
            }

            ownerObject.put("owns", owns);

            owners.add(ownerObject);
        }

        configJson.put("owners", owners);
        configJson.put("pets", pets);
    }
}
