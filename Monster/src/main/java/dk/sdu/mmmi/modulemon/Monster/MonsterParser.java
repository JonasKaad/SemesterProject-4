package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import dk.sdu.mmmi.modulemon.common.OSGiFileHandleByteReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonsterParser {
    private static HashMap<String, MonsterType> monsterTypeHashMap = new HashMap<String, MonsterType>(){{
        put("fire", MonsterType.FIRE);
        put("water", MonsterType.WATER);
        put("grass", MonsterType.GRASS);
        put("air", MonsterType.AIR);
        put("earth", MonsterType.EARTH);
        put("lightning", MonsterType.LIGHTNING);
    }};

    /**
     *
     * @param monstersURL A path to the relevant resource
     * @param movesURL A path to the relevant resource
     * @return Returns a list of monsters
     */
    public static IMonster[] parseMonsters(String monstersURL, String movesURL) throws IOException, URISyntaxException {
        JSONArray JSONmonsters = loadJSONArray(monstersURL);
        JSONArray JSONmoves = loadJSONArray(movesURL);

        HashMap<String, IMonsterMove> monsterMoveHashMap = instantiateMoves(JSONmoves);

        return instantiateMonsters(JSONmonsters, monsterMoveHashMap);
    }

    private static JSONArray loadJSONArray(String path) throws IOException {
        String jsonString = new String(new OSGiFileHandleByteReader(path, MonsterParser.class).readBytes(), StandardCharsets.UTF_8);
        return new JSONArray(jsonString);
    }

    private static HashMap<String, IMonsterMove> instantiateMoves(JSONArray jsonMoves) {
        HashMap<String, IMonsterMove> moves = new HashMap<>();
        for (int i = 0; i < jsonMoves.length(); i++) {
            JSONObject JSONMove = jsonMoves.getJSONObject(i);
            IMonsterMove move = new MonsterMove(JSONMove.getString("name"),
                    JSONMove.getInt("damage"),
                    monsterTypeHashMap.get(JSONMove.getString("type").toLowerCase()));
            moves.put(JSONMove.getString("id"), move);
        }
        return moves;
    }

    private static IMonster[] instantiateMonsters(JSONArray JSONMonsters, HashMap<String, IMonsterMove> movesHash) {
        IMonster[] monsters = new IMonster[JSONMonsters.length()];
        for (int i = 0; i < JSONMonsters.length(); i++) {
            JSONObject JSONMonster = JSONMonsters.getJSONObject(i);
            List<IMonsterMove> currentMonsterMoves = new ArrayList<>();
            for (int j = 0; j < JSONMonster.getJSONArray("moves").length(); j++) {
                currentMonsterMoves.add(movesHash.get(JSONMonster.getJSONArray("moves").getString(j)));
            }
            IMonster monster = new Monster(JSONMonster.getString("name"),
                    monsterTypeHashMap.get(JSONMonster.getString("type").toLowerCase()),
                    JSONMonster.getInt("hp"),
                    JSONMonster.getInt("defence"),
                    JSONMonster.getInt("attack"),
                    JSONMonster.getInt("speed"),
                    currentMonsterMoves,
                    JSONMonster.getString("front"),
                    JSONMonster.getString("back"),
                    JSONMonster.getInt("id")
            );
            monsters[monster.getID()] = monster;
        }
        return monsters;
    }
}
