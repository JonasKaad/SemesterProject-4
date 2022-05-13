/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts;

import static dk.sdu.mmmi.modulemon.CommonMap.Data.Direction.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Gorm
 */
public class InteractPartTest {
    
    /**
     * Test of isInRange method, of class InteractPart.
     */
    @Test
    public void testIsInRange() {
        final int TILE_SIZE = 64;
        final float X = 10.0F*TILE_SIZE;   // X of the entity who is tested
        final float Y = 10.0F*TILE_SIZE;   // Y of the entity who is tested
        PositionPart positionPart = new PositionPart(X, Y);             // Making the entity's PositionPart
        InteractPart interactPart = new InteractPart(positionPart, 0);  // Making the entity's InteractPart
        int maxRange = 5;
        boolean result; // The result of the isInRange check
        
        System.out.println("PositionPart: (" + positionPart.getX() + ", " + positionPart.getY() + ")");
        
        // Go through each range from 1 to max
        for (int range = 1; range <= maxRange; range++) {
            System.out.println("Range of InteractionPart: " + range);
            // The entity faces WEST
            positionPart.setDirection(WEST);
            System.out.println("Direction: " + positionPart.getDirection());
            
            // Test if each tile of 64 pixels in that range infront of the entity makes isInRange return true
            for (int i = -range; i < 0; i++) {
                interactPart.setRange(Math.abs(i));

                float x = X+i*TILE_SIZE;
                float y = Y;
                result = interactPart.isInRange(x, y);
                System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertTrue(result);

            }
            // Test if each tile of 64 pixels in that range behind the entity makes isInRange return false
            for (int i = 1; i <= range; i++) {
                interactPart.setRange(Math.abs(i));

                float x = X+i*TILE_SIZE;
                float y = Y;
                result = interactPart.isInRange(x, y);
                System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertFalse(result);

            }

            // Now the entity faces EAST
            positionPart.setDirection(EAST);
            System.out.println("Direction: " + positionPart.getDirection());

            // Test if each tile of 64 pixels in that range infront of the entity makes isInRange return true
            for (int i = -range; i < 0; i++) {
                interactPart.setRange(Math.abs(i));

                float x = X-i*TILE_SIZE;
                float y = Y;
                result = interactPart.isInRange(x, y);
                System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertTrue(result);

            }
            // Test if each tile of 64 pixels in that range behind the entity makes isInRange return false
            for (int i = 1; i <= range; i++) {
                interactPart.setRange(Math.abs(i));

                float x = X-i*TILE_SIZE;
                float y = Y;
                result = interactPart.isInRange(x, y);
                System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertFalse(result);

            }

            // Now the entity faces SOUTH
            positionPart.setDirection(SOUTH);
            System.out.println("Direction: " + positionPart.getDirection());
            
            // Test if each tile of 64 pixels in that range infront of the entity makes isInRange return true
            for (int i = -range; i < 0; i++) {
                interactPart.setRange(Math.abs(i));

                float x = X;
                float y = Y+i*TILE_SIZE;
                result = interactPart.isInRange(x, y);
                System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertTrue(result);

            }
            // Test if each tile of 64 pixels in that range behind the entity makes isInRange return false
            for (int i = 1; i <= range; i++) {
                interactPart.setRange(Math.abs(i));

                float x = X;
                float y = Y+i*TILE_SIZE;
                result = interactPart.isInRange(x, y);
                System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertFalse(result);

            }

            // Now the entity faces NORTH
            positionPart.setDirection(NORTH);
            System.out.println("Direction: " + positionPart.getDirection());

            // Test if each tile of 64 pixels in that range infront of the entity makes isInRange return true
            for (int i = -range; i < 0; i++) {
                interactPart.setRange(Math.abs(i));

                float x = X;
                float y = Y-i*TILE_SIZE;
                result = interactPart.isInRange(x, y);
                System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertTrue(result);

            }
            // Test if each tile of 64 pixels in that range behind the entity makes isInRange return false
            for (int i = 1; i <= range; i++) {
                interactPart.setRange(Math.abs(i));

                float x = X;
                float y = Y-i*TILE_SIZE;
                result = interactPart.isInRange(x, y);
                System.out.println(buildDetails(x ,y, result, Math.abs(i)));
                assertFalse(result);

            }
            
            System.out.println("Testing that it does not interact when the position is not in sight");
            // Assert that you can not interact when the position is not in sight.
            for (float x = X-2*range*TILE_SIZE; x < X+2*range*TILE_SIZE; x += TILE_SIZE) {
                for (float y = Y-2*range*TILE_SIZE; y < Y+2*range*TILE_SIZE; y += TILE_SIZE) {
                    if (X == x || Y == y) continue;
                    interactPart.setRange(Math.abs(range));

                    result = interactPart.isInRange(x, y);
                    //System.out.println(buildDetails(x ,y, result, Math.abs(range)));
                    assertFalse(result);
                }
            }
        }
        System.out.println("Test successful");
    }
    
    // A StringBuilder to build the details, x, y, the result of isInRange, of the loops.
    private StringBuilder buildDetails(float x, float y, boolean result, int range) {
        StringBuilder str_details = new StringBuilder();
        str_details.append("Other Entity: (").append(x).append(", ").append(y).append(")");
        str_details.append(", Interaction range: ").append(range);
        str_details.append(", isInRange(): ").append(result);
        return str_details;
    }
}
