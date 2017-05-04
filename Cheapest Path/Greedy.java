/**
 * Greedy.java
 * 
 * This is the greedy solution to the matrix problem.
 * Note this solution will not always produce the optimal solution
 * to the matrix problem.
 */ 
// Branaugh and Evan
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Greedy implements ActionListener
{
	private MatrixPanel elements; // the panel for the squares
	private ControlPanel controls; // the panel for the control buttons

	/**
	 * The greedy solution to the matrix problem.
	 *
	 * @param elements the GUI for the squares.
	 * @param controls the GUI for the control buttons.
	 */
	public Greedy(MatrixPanel elements, ControlPanel controls) {
		this.elements = elements;
		this.controls = controls;
	}

	/**
	 * Determine the mininum value between squares 'begin' and 'end'
	 * for the specified 'column'.
	 * 
	 * @param begin the index where to begin searching.
	 * @param end the index where to end searching.
	 * @param column the column to be searched.
	 *
	 * @return the index of the minimum value for the specified column
	 */
	public int minimum(int begin, int end, int column) {
		if (elements.valueOf(begin,column) < elements.valueOf(end,column))
			return begin;
		else
			return end;
	}

	/**
	 * Determine the index of the mininum value between squares 'index' and 
	 * 'index + 1' and 'index - 1' for the specified 'column'.
	 *
	 * @param index the index of the minimum value to be searched.
	 * @param column the column to be searched.
	 * 
	 * @return int the index of the minimum value for the specified column
	 */
	public int minimum(int index, int column) {
		int minimumIndex = index - 1;
		if (elements.valueOf(index,column) < elements.valueOf(minimumIndex,column))
			minimumIndex = index;
		if (elements.valueOf(index+1,column) < elements.valueOf(minimumIndex,column))
			minimumIndex = index + 1;

		return minimumIndex;
	}



	/**
	 * This will only be called when the "Greedy"
	 * button is clicked.
	 *
	 * This is an implementation of the greedy algorithm
	 * for the matrix problem. 
	 */
	public void actionPerformed(ActionEvent evt) {
		/**
		 * the cost of this algorithm.
		 */
		int totalCost = 0;

		/**
		 * index represents the index of the lowest
		 * valued square for a given column.
		 */
		int index = 0;

		/**
		 * determine the index of the smallest square
		 * at the leftmost column.
		 */
		int smallest = elements.valueOf(0,0);

		for (int i = 1; i < MatrixPanel.SIZE; i++) { 
			if (elements.valueOf(i,0) < smallest) {
				smallest = elements.valueOf(i,0);
				index = i;
			}
		}

		totalCost = smallest; 

		// highlight the square 
		elements.setValues(index, 0, Color.blue);

		// Beginning of loop to find smallest of all other columns.
		for (int j = 1; j < MatrixPanel.SIZE; j++) {
			// If our current location is in the top row:
			if (index == 0) {
				// Only the 0th and 1st rows need to be considered.
				int one = elements.valueOf(index,j);
				int two = elements.valueOf(index+1,j);
				if (one <= two) {
					// The 0th row is the smallest.
					smallest = one;
				}
				else {
					// The 1st row is the smallest, and our index now changes.
					smallest = two;
					index = index+1;
				}
			}
			// If our current location is in the bottom row.
			else if (index == 5) {
				// Only the 4th and 5th rows need to be considered.
				int one = elements.valueOf(index-1,j);
				int two = elements.valueOf(index,j);
				if (one <= two) {
					// The 4th row is the smallest, and our index now changes.
					smallest = one;
					index = index-1;
				}
				else {
					// The fifth row is the smallest.
					smallest = two;
				}
			}

			else { 
				// The row above our index.
				int one = elements.valueOf(index-1,j);
				// The row at the same height as our index.
				int two = elements.valueOf(index,j);
				// The row below our index.
				int three = elements.valueOf(index+1,j);
				if (one <= two) {
					if (one <= three) {
						smallest = one;
						index = index-1;
					}
					else {
						smallest = three;
						index = index+1;
					}
				}
				if (two < one) {
					if (two <= three) {
						smallest = two;
					}
					else {
						smallest = three;
						index=index+1;
					}
				}
			}
			// Update the total cost, color the path, and move to the next column.
			totalCost = totalCost+smallest;
			elements.setValues(index, j, Color.blue);
		}


		/**
		 * We are done. Now update the output for total cost.
		 */
		controls.setTotal(totalCost);
	}
}
