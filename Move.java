package rubiksCube;

public class Move {
	
	/**Which face of the cube is being rotated up or down.*/
	private int pivot;
	
	/**Which row of the cube is being rotated up or down.*/
	private int row;
	
	/**The direction that the code is being moved.*/
	private String dir;

	
	
	/*************************************************************************
	 * Constructor that takes in parameters for each instance variable. 
	 * @param pivot		Integer, which face is being rotated up or down. 
	 * @param row		Integer, which row of the cube is being updated.
	 * @param dir		String, the direction that the cube is being rotated.
	 ************************************************************************/
	public Move(int pivot, int row, String dir) {
		super();
		this.pivot = pivot;
		this.row = row;
		this.dir = dir;
	}


	/**************************************
	 * gets the pivot face of the cube.
	 * @return Integer, 1 or 2.
	 *************************************/
	public int getPivot() {
		return pivot;
	}


	/**********************************************
	 * Sets the pivot of the cube.
	 * @param pivot Integer, should be 1, 2 or 0
	 *********************************************/
	public void setPivot(int pivot) {
		this.pivot = pivot;
	}


	/************************************************
	 * Gets the row that the cube is being rotated.
	 * @return Integer, 0,1, or 2.
	 ***********************************************/
	public int getRow() {
		return row;
	}


	/*************************************************
	 * Sets the row that the cube is being rotated.
	 * @param row Integer, 0,1, or 2.
	 ************************************************/
	public void setRow(int row) {
		this.row = row;
	}



	public String getDir() {
		return dir;
	}



	public void setDir(String dir) {
		this.dir = dir;
	}
	
	
	
	

}
