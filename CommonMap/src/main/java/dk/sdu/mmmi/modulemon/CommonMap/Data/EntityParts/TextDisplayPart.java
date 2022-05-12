package dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts;

import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.LinkedList;
import java.util.Queue;

public class TextDisplayPart implements EntityPart {
    private Queue<String> lines;

    public TextDisplayPart(Queue<String> lines) {
        this.lines = lines;
    }

    public Queue<String> getLines() {
        Queue linesCopy = new LinkedList();
        for (String line : lines) {
            linesCopy.add(line);
        }
        return linesCopy;
    }

    public void addLines(String line){
        lines.add(line);
    }


    @Override
    public void process(GameData gameData, World world, Entity entity) {

    }
}
