package rubiksCube;
import java.util.ArrayList;
/***********************************************************
 * This class is the engine for a virtual rubik's cube. 
 * 
 * @author  Anthony Sciarini
 * @version 8/4/2017
 ***********************************************************/
public class RubiksEngine {
	
	//TODO Simplify repetitive code. 
	//TODO Add undo method.
	//TODO Add flip method.
	//TODO Add save and load method with serialization.
	//TODO implement shitty grid based GUI.
	
	/**Number of faces on the rubic's cube.*/
	protected final int numFaces = 6; 
	
	/**Array list of the faces on the rubic's cube.*/
	protected ArrayList <int[][]> cube;
	
	/**Array list of previous moves.*/
	protected ArrayList<Move> moveList;
	
	/*****************************************************************
	 * Constructor for the RubicsEnging class initializes a ArrayList
	 * of 6 Integer[][] to represent the faces of the cube.
	 *****************************************************************/
	public RubiksEngine(){
		cube = new ArrayList<int[][]>();//initializes the cube
		moveList = new ArrayList<Move>();//initialize the movelist
		
		int[][] face; //Temporary Integer[][] used to set up each side
		
		int[][] test1 = {{1,2,3},
					   	 {4,5,6},
					     {7,8,9}};
		
		int[][] test2 = {{1,2,3},
				         {4,5,6},
				         {7,8,9}};
		
		//cube.add(test1);//for testing the rotation of the top face.
		
		//sets up one face for cube each iteration
		for(int num = 0; num < numFaces; num++){
			face = new int[3][3];
			//loops through each cell on face.
			for(int r = 0; r < face.length; r++)
				for(int c = 0; c < face[0].length; c++)
					face[r][c] = num;//sets color number of face.
//			if(num == 1)
//				cube.add(test1);
//			else if(num == 3)
//				cube.add(test2);
//			else
				cube.add(face);//adds face to cube;
		}		
	}
	
	/****************************************************************************
	 * This method is used to access private helper methods that move the cube.
	 * @param pivotFace
	 * @param dir
	 * @param row
	 *****************************************************************************/
	public void rotateCube(int pivotFace, String dir, int row){
		
		
		
		switch(dir){
			case "right":
				rotateSideWays(row,dir);
				break;
			case "left":
				rotateSideWays(row,dir);
				break;
			case "up":
			case "down":
				rotateVertically(pivotFace, row, dir);
				break;
				//TODO MOVE TO LIST THAT CAN BE USED FOR UNDO FUNCTION
		}
	}
	
	///////////////////
	//PRIVATE HELPERS//
	///////////////////
	//TODO complete this method.
	private boolean checkMove(int pivot, String dir, int row){
		if(pivot >=0 && pivot <=3)
			if(row >=0 && row <=2)
				dir.compareTo("left");
		
		return true;
	}
	
	/****************************************************************************
	 * Method will rotate a row of the cube left or right.
	 * @param row	int value that specifies which row is being shifted
	 * @param dir   String that determines which way the cube is being shifted.
	 ***************************************************************************/
	private void rotateSideWays(int row, String dir){
		 	//Integer array of the index numbers of the faces in the middle
			//of the cube.
			int[] midFaces = {1,2,3,4};
			
			//The order that the mid rows will be placed.
			int[] rpo = {3,0,1,2};//r.p.o. = Right Placement order 
			
			//the order that the shifted rows will be after shift
			int[] lpo = {1,2,3,0}; //l.p.o. = Left placement order
		
			//2D Array that stores the rows that are be shifted.
			int[][] sr = new int[4][3];   //sr = 'shiftedRows'
				
			if(row >= 0 && row <=2){//If a row is shifted to the right
				//Get each row that is to be shifted.
				for(int index = 0; index < midFaces.length; index++)
					copy1D(sr[index], getRow(row, cube.get(midFaces[index])));
						
			//Put rows in new locations
			if(dir == "right" || dir == "down"){ //if cube is rotated to the right
				for(int index = 0; index < midFaces.length; index++)
					copy1D(getRow(row, cube.get(midFaces[index])),sr[rpo[index]]);
				
			}
				
			//Put rows in new locations
			if(dir == "left" || dir == "up") //if cube is rotated to the left
				for(int index = 0; index < midFaces.length; index++)
					copy1D(getRow(row,cube.get(midFaces[index])),sr[lpo[index]]);
				
			
			if(row == 0) //if top row is shifted 
				shiftTopBottom(cube.get(0), dir); //shift top face
			
			if(row == 2) //if bottom row is shifted 
				shiftTopBottom(cube.get(5), dir);//shift bottom face
			}		
	}
	
	/******************************************************************
	 * This method will rotate the cube up or down with the help
	 * of the transpose method and the shift sideways method.
	 * @param row	Integer the contains the row number being shifted.
	 * @param dir	String, contains direction being shifted.
	 ******************************************************************/
	private void rotateVertically(int pivotFace, int row, String dir){
		//CASE 1: Rotated vertically from face 1.
		//Index number of faces that will be changed if cube is rotated vertically. 
		int[] vr1 = {1,5,3,0};
		//		
		//CASE 2:Rotated vertically from face 2.
		//Index number of faces that will be changed if cube is rotated vertically.
		int[] vr2 = {2,5,4,0};
		//
		
		//placement order down, order faces will be placed if they are rotated down.
		int[] pod = {3,0,1,2};
		
		//placement order up, order faces will be placed if they are rotated down.
		int[] pou = {1,2,3,0};
		
		
		int[][] sr = new int[4][3]; //contains rows that are being moved
		
		if(pivotFace == 1){
			//Transpose rotated faces
			for(int idx = 0; idx < vr1.length; idx++)
				transpose(cube.get(vr1[idx]));
			
			//Now that rotated faces have been transposed the shift sideways algorithms
			//can be used to rotate the cube.
			for(int idx = 0; idx < vr1.length; idx++)
				copy1D(sr[idx], getRow(row,cube.get(vr1[idx])));
			
			//place each row in the correct location after rotation
			if(dir == "down")
				for(int idx = 0; idx < vr1.length; idx++)
					copy1D(getRow(row,cube.get(vr1[idx])),sr[pod[idx]]);
			
			if(dir == "up")
				for(int idx = 0; idx < vr1.length; idx++)
					copy1D(getRow(row,cube.get(vr1[idx])),sr[pou[idx]]);

			if(row == 0) //If bottom column shifted
				shiftTopBottom(cube.get(4),dir);
			
			if(row == 2) //If top column shifted
				shiftTopBottom(cube.get(2),dir);
			
			//Transpose rotated faces
			for(int idx = 0; idx < vr1.length; idx++)
				transpose(cube.get(vr1[idx]));
		}
		
		if(pivotFace == 2){
		//Transpose rotated faces
			for(int idx = 0; idx < vr2.length; idx++)
				transpose(cube.get(vr2[idx]));
			
		//Now that rotated faces have been transposed the shift sideways algorithms
		//can be used to rotate the cube.
		for(int idx = 0; idx < vr2.length; idx++)
			copy1D(sr[idx], getRow(row,cube.get(vr2[idx])));
		
		//place each row in the correct location after rotation
		if(dir == "down")
			for(int idx = 0; idx < vr2.length; idx++)
				copy1D(getRow(row,cube.get(vr2[idx])),sr[pod[idx]]);
		
		if(dir == "up")
			for(int idx = 0; idx < vr2.length; idx++)
				copy1D(getRow(row,cube.get(vr2[idx])),sr[pou[idx]]);
		
		if(row == 0) //If bottom column is shifted
			shiftTopBottom(cube.get(1), dir);
		
		if(row == 2)//if top column is shifted
			shiftTopBottom(cube.get(3), dir);
		
		//Transpose rotated faces
		for(int idx = 0; idx < vr2.length; idx++)
			transpose(cube.get(vr2[idx]));
		}
	}
	
	private void transpose(int[][] face){
		int temp0_1 = face[0][1];
		int temp0_2 = face[0][2];
		int temp1_2 = face[1][2];
		
		//Transpose top right
		face[0][1] = face[1][0];
		face[0][2] = face[2][0];
		face[1][2] = face[2][1];
		
		//Transpose bottom left		
		face[1][0] = temp0_1;
		face[2][0] = temp0_2;
		face[2][1] = temp1_2;
	}
	
	private void shiftTopBottom(int[][] top, String dir){

		//temp arrays and ints used to rotate the top face
		int[] temp1 = new int [3];
		int[] temp2 = new int [2];
		int[] temp3 = new int [2];
		int temp4 = 0;
		
		//fill temp1
		for(int index = 0; index < temp1.length; index++)
			temp1[index] = top[0][index];
		
		//fill temp2
		for(int index = 0; index < temp2.length; index++)
			temp2[index] = top[index+1][2];
		
		//fill temp3
		for(int index = 0; index < temp3.length; index++)
			temp3[index] = top[2][1 - index];
		
		//fill temp4
		temp4 = top[1][0];
		
		//place temp1 - 4 in location after rotation
		
		switch(dir){
		case "down":
		case "right":
			//place temp1
			for(int index = 0; index < temp1.length; index++)
				top[index][2] = temp1[index];
			
			//place temp2
			for(int index = 0; index < temp2.length; index++)
				top[2][1 - index] = temp2[index];
			
			//place temp3
			for(int index = 0; index < temp3.length; index++)
				top[1 - index][0] = temp3[index];
			
			//place temp4
			top[0][1] = temp4;
			break;
			
		case "up":	
		case "left":
			//place temp1
			for(int index  = 0; index < temp1.length; index++)
				top[index][0] = temp1[2 - index];
			
			//place temp2
			for(int index = 0; index < temp2.length; index++)
				top[0][index + 1] = temp2[index];
			
			//place temp3
			for(int index = 0; index < temp3.length; index++)
				top[index + 1][2] = temp3[index];
			
			//place temp4
			top[2][1] = temp4;
			break;
			
		default:
			break;
		}
	}
	
	/*******************************************************************
	 * Given a 2D array and a row index number, this method will return
	 * the corresponding rows.
	 * @param rowIndex		row that is to be returned
	 * @param array         array that contains said row
	 * @return				a array of integers
	 *******************************************************************/
	private int[] getRow(int rowIndex, int[][] _2D_array) {
		return _2D_array[rowIndex];
	}
	
	/*****************************************
	 * copies 1D array og to 1D array copy.
	 * @param og	1D array
	 * @param copy  1D array
	 ****************************************/
	private void copy1D(int[] copyOg, int[] og){
		//copies og to copy
		for(int x = 0; x < og.length; x++)
			copyOg[x] = og[x];
	}					
		
	/***********************************
	 * Displays each face of the cube.
	 ***********************************/
	private void display(){

		int count = 0;
		for(int[][] face:cube){
			System.out.println("Face: " + count);
			for(int r = 0; r < face.length; r++){
				for(int c = 0; c < face[0].length; c++ )
					System.out.print(face[r][c]);//prints a row
				System.out.println();
			}
			count++;
		}
	}

	/***********************************
	 * Main method. Used to test class.
	 * @param args
	 ***********************************/
	public static void main(String [] args){
		RubiksEngine game = new RubiksEngine();
		game.display();
		
//		game.shiftRight(0);
//		System.out.println("Attempt to shift top row right");
//		game.display();
	    
//		game.shiftRight(1);
//		System.out.println("Attempt to shift mid row right");
//		game.display();
	
		game.rotateCube(2,"up", 0);
		System.out.println("Test Rotation up: ");
		game.display();
	}	
}