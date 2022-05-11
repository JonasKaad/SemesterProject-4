/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts;

import static dk.sdu.mmmi.modulemon.CommonMap.Data.Direction.*;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Gorm
 */
public class InteractPartTest {
    
    public InteractPartTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of isInRange method, of class InteractPart.
     */
    @Test
    public void testIsInRange() {
        final float X = 10.0F*64;
        final float Y = 10.0F*64;
        PositionPart positionPart = new PositionPart(X, Y);
        int maxRange = 5;
        InteractPart instance;
        boolean result;
        
        //System.out.println("PositionPart: (" + positionPart.getX() + ", " + positionPart.getY() + ")");
        
        for (int range = 1; range <= maxRange; range++) {
            //System.out.println("Range of InteractionPart: " + range);
            positionPart.setDirection(WEST);
            //System.out.println("Direction: " + positionPart.getDirection());
            
            for (int i = -range; i < 0; i++) {
                instance = new InteractPart(positionPart, Math.abs(i));

                float x = X+i*64;
                float y = Y;
                result = instance.isInRange(x, y);
                //System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertEquals(true, result);

            }
            for (int i = 1; i <= range; i++) {
                instance = new InteractPart(positionPart, Math.abs(i));

                float x = X+i*64;
                float y = Y;
                result = instance.isInRange(x, y);
                //System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertEquals(false, result);

            }

            positionPart.setDirection(EAST);
            //System.out.println("Direction: " + positionPart.getDirection());


            for (int i = -range; i < 0; i++) {
                instance = new InteractPart(positionPart, Math.abs(i));

                float x = X-i*64;
                float y = Y;
                result = instance.isInRange(x, y);
                //System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertEquals(true, result);

            }
            for (int i = 1; i <= range; i++) {
                instance = new InteractPart(positionPart, Math.abs(i));

                float x = X-i*64;
                float y = Y;
                result = instance.isInRange(x, y);
                //System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertEquals(false, result);

            }

            positionPart.setDirection(SOUTH);
            //System.out.println("Direction: " + positionPart.getDirection());

            for (int i = -range; i < 0; i++) {
                instance = new InteractPart(positionPart, Math.abs(i));

                float x = X;
                float y = Y+i*64;
                result = instance.isInRange(x, y);
                //System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertEquals(true, result);

            }
            for (int i = 1; i <= range; i++) {
                instance = new InteractPart(positionPart, Math.abs(i));

                float x = X;
                float y = Y+i*64;
                result = instance.isInRange(x, y);
                //System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertEquals(false, result);

            }

            positionPart.setDirection(NORTH);
            //System.out.println("Direction: " + positionPart.getDirection());

            for (int i = -range; i < 0; i++) {
                instance = new InteractPart(positionPart, Math.abs(i));

                float x = X;
                float y = Y-i*64;
                result = instance.isInRange(x, y);
                //System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertEquals(true, result);

            }
            for (int i = 1; i <= range; i++) {
                instance = new InteractPart(positionPart, Math.abs(i));

                float x = X;
                float y = Y-i*64;
                result = instance.isInRange(x, y);
                //System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertEquals(false, result);

            }
            
            //System.out.println("No Interaction when Entity is not in sight");
            // Test that you can not interact when Entity is not in sight.
            for (float x = X-2*range*64; x < X+2*range*64; x += 64) {
                for (float y = Y-2*range*64; y < Y+2*range*64; y += 64) {
                    if (X == x || Y == y) continue;
                    instance = new InteractPart(positionPart, Math.abs(range));

                    result = instance.isInRange(x, y);
                    //System.out.println(buildDetails(x ,y, result, Math.abs(range)));
                    assertEquals(false, result);
                }
            }
        }
        System.out.println("Test successful");
    }
    
    private StringBuilder buildDetails(float x, float y, boolean result, int range) {
        StringBuilder str_details = new StringBuilder();
        str_details.append("Other Entity: (").append(x).append(", ").append(y).append(")");
        str_details.append(", Interaction range: ").append(range);
        str_details.append(", isInRange(): ").append(result);
        return str_details;
    }
    
    private void buildInteractionField() {
        
    }
}
