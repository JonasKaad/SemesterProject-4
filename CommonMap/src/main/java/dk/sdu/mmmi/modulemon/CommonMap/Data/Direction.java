/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package dk.sdu.mmmi.modulemon.CommonMap.Data;

/**
 *
 * @author Gorm Krings
 */
public enum Direction {
    EAST(0),
    NORTH(90),
    WEST(180),
    SOUTH(270);
	
    private int degree;
    
    Direction(int degree){
    	this.degree = degree;
    }
	
    public int getDegree(){
    	return this.degree;	
    }
}
