package dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts;

import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.Queue;

public class LinesPart implements EntityPart {
    private Queue<String> lines;

    public LinesPart(Queue<String> lines) {
        this.lines = lines;
    }

    public Queue<String> getLines() {
        return lines;
    }

    public void addLines(String line){
        lines.add(line);
    }


    @Override
    public void process(GameData gameData, World world, Entity entity) {

    }
}
