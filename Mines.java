package mines; /*mines package*/

//import Random library
import java.util.Random;

//define Mines that have height, width, number
//of mines, Place (inner class) matrix, and flag
//for show all check. In addition, the class have
//constructor, inner class Place, method that add
//mine to specific place, method that open specific
//place, method that put flag sign on specific place,
//method that check if the game over, method that
//return the appropriate string to specific place,
//method that set the show all check flag, method that
//check if specific indexes are inside the grid, and
//method that present the whole appropriate strings
//for the whole grid (the table of the game)
public class Mines {
	// define height, width,
	// and mines quantity
	private int height, width;
	private int numofMines;
	// define places matrix
	private Place[][] matrix;
	// define flag for show all
	private boolean ShowFlag;

	// define mines constructor
	public Mines(int height, int width, int numMines) {
		int i, j;
		// initialize places matrix
		matrix = new Place[height][width];
		this.numofMines = numMines;
		this.width = width;
		this.height = height;
		// initialize the flag of
		// the show all to false
		this.ShowFlag = false;
		// initialize the places of the matrix, at
		// the beginning no place is open, no place
		// have mine, and no place sign by flag
		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				matrix[i][j] = new Place(false, false, false, 0);
			}
		}
		// put mines randomly in
		// the grid (game table)
		Random random = new Random();
		i = 0;
		while (i <numofMines) {
			// check if the add of the mine succeed
			// and try again to add if it doesn't
			if (!addMine(random.nextInt(height), random.nextInt(width)))
				i--;
			i++;
		}
	}

	// define inner Place class that have flag
	// for know if the place have mine, flag for
	// know if the specific place is open, flag
	// for know if the place sign by flag, and
	// counter of the mines around the place. In
	// addition, the class have method for set the
	// flag for the mine, method that set the flag
	// of the open, method that set the flag of the
	// sign flag, method that set the counter of the
	// mines, method that return the flag of the mine,
	// method that return the flag of the open, method
	// that return the flag of the sign flag, and method
	// that return the counter of the mines
	private class Place {
		// define flags
		private boolean MineFlag, OpenFlag, Flag;
		// define the counter of
		// the mines around the place
		private int AroundMe;

		// define Place constructor
		private Place(boolean MineFlag, boolean OpenFlag, boolean Flag, int AroundMe) {
			this.MineFlag = MineFlag;
			this.OpenFlag = OpenFlag;
			this.AroundMe = AroundMe;
			this.Flag = Flag;
		}

		// define method that
		// set the mine flag
		private void setMineFlag(boolean flag) {
			MineFlag = flag;
		}

		// define method that
		// set the open flag
		private void setOpenFlag(boolean flag) {
			OpenFlag = flag;
		}

		// define method that
		// set the sign flag
		private void setFlag(boolean flag) {
			Flag = flag;
		}

		// define method that
		// set the counter
		private void setAroundMe(int integer) {
			AroundMe = integer;
		}

		// define method that
		// return the mine flag
		private boolean getMineFlag() {
			return MineFlag;
		}

		// define method that
		// return the open flag
		private boolean getOpenFlag() {
			return OpenFlag;
		}

		// define method that
		// return the sign flag
		private boolean getFlag() {
			return Flag;
		}

		// define method that
		// return the counter
		private int getAroundMe() {
			return AroundMe;
		}
	}

	// define method that add
	// mine to specific place
	public boolean addMine(int i, int j) {
		// if the indexes are out of
		// bounds of their is already
		// mine their return false
		if (!isInside(i, j) || matrix[i][j].getMineFlag())
			return false;
		// put mine in the place
		// and initialize to 0 the
		// place (because of the mine)
		matrix[i][j].setMineFlag(true);
		matrix[i][j].setAroundMe(0);
		int i1, j1;
		// add to the neighbors 1 to
		// the counter for this new mine
		for (i1 = (-1); i1 < 2; i1++) {
			for (j1 = (-1); j1 < 2; j1++) {
				// if the indexes are legal and the neighbor
				// is not a mine, add 1 to the counter of it
				if (isInside(i + i1, j + j1) && !matrix[i + i1][j + j1].getMineFlag())
					matrix[i + i1][j + j1].AroundMe++;
			}
		}
		return true;
	}

	// define method that open
	// specific place in the grid
	public boolean open(int i, int j) {
		// if the indexes are not legal or
		// the place have mine return false
		if (!isInside(i, j) || matrix[i][j].getMineFlag()) {
			return false;
		}
		// if the place is already
		// open return true and get out
		if (matrix[i][j].getOpenFlag()) {
			return true;
		}
		// open the place
		matrix[i][j].setOpenFlag(true);
		int i1, j1;
		boolean flag = true;
		// check if at least 1 of the
		// neighbors is mine (legal neighbors)
		for (i1 = (-1); i1 < 2; i1++) {
			for (j1 = (-1); j1 < 2; j1++) {
				// check if the indexes
				// are legal indexes
				if (isInside(i + i1, j + j1)) {
					// sign if exist mine
					if (matrix[i + i1][j + j1].getMineFlag())
						flag = false;
				}
			}
		}
		// if their are no mines in
		// the neighbors open all of
		// the neighbors (with recursion)
		if (flag) {
			for (i1 = (-1); i1 < 2; i1++) {
				for (j1 = (-1); j1 < 2; j1++) {
					// check if it not
					// our place itself
					if (i1 != 0 || j1 != 0)
						open(i + i1, j + j1);
				}
			}
		}
		return true;
	}

	// define method that sign
	// with flag a specific place
	public void toggleFlag(int x, int y) {
		// if the indexes are legal and the flag
		// is still not open, put sign on the place
		// if their is no sign, and remove the
		// sign if their is already sign on it
		if (isInside(x, y) && (!matrix[x][y].getOpenFlag()))
			matrix[x][y].setFlag(!matrix[x][y].getFlag());
	}

	// define method that check if the
	// game is over - if the player
	// already found all of the places
	// that are without mines
	public boolean isDone() {
		int i, j;
		// check all of the places
		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				// if exist place that it is not,
				// mine or open, return false
				if (!(matrix[i][j].getMineFlag() || matrix[i][j].getOpenFlag()))
					return false;
			}
		}
		// if all of the places are
		// mine or open return true
		return true;
	}

	// define method that return
	// string for specific place
	// according to it situation
	public String get(int i, int j) {
		// if the place is open or show all
		// flag is true, if the place is sign
		// present "F" and else present "."
		if (!(matrix[i][j].getOpenFlag() || ShowFlag)) {
			if (matrix[i][j].Flag)
				return "F";
			return ".";
		}
		// else, if their is mine
		// in this place return "X"
		if (matrix[i][j].getMineFlag())
			return "X";
		// else, if there are no mines
		// around this place return " "
		if (matrix[i][j].AroundMe == 0)
			return " ";
		// else, return the quantity
		// of the mines around the place
		return String.valueOf(matrix[i][j].getAroundMe());
	}

	// define method that
	// set the show all flag
	public void setShowAll(boolean showAll) {
		ShowFlag = showAll;
	}

	// define method that check if the
	// indexes are legal (inside the bounds)
	private boolean isInside(int i, int j) {
		return (i < height && i > (-1) && j < width && j > (-1));
	}

	@Override
	// define method that present
	// the whole grid (table game)
	public String toString() {
		int i, j;
		// build the string
		// using get method
		StringBuilder str = new StringBuilder();
		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				str.append(get(i, j));
			}
			str.append("\n");
		}
		// return the string
		return str.toString();
	}
}