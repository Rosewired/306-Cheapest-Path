/**
 * The dynamic programming solution to the matrix problem.
 */
// Branaugh and Evan
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Dynamic implements ActionListener
{
	private MatrixPanel elements;
	private ControlPanel controls;
	private int[][] dynamicValue;
	private int[][] predecessors;

	public Dynamic(MatrixPanel elements, ControlPanel controls)
	{
		this.elements = elements;
		this.controls = controls;
			dynamicValue = new int[MatrixPanel.SIZE][MatrixPanel.SIZE];
			predecessors = new int[MatrixPanel.SIZE][MatrixPanel.SIZE];
		}
	
		/**
	 * This will only be called when the "Dynamic" button is clicked. This is an
	 * implementation of the dynamic programming algorithm for the matrix
	 * problem.
	 */
	public void actionPerformed(ActionEvent evt)
	{

		for (int j = 0; j < dynamicValue.length; j++) {
			// Initializing these arrays.
			dynamicValue[j][0] = elements.valueOf(j, 0);
			predecessors[j][0] = -1;
		}
		// Loop through all columns and rows.
		for (int i = 1; i < dynamicValue.length; i++)
		{
			for (int j = 0; j < dynamicValue[i].length; j++)
			{
				int min = 0;

				// Finding the minimum paths.
				if (j == 0)
				{
					int two = dynamicValue[j][ i - 1];
					int three = dynamicValue[j + 1][ i - 1];
					if(two < three)
						min = two;
					else 
						min = three;
				}
				else if (j == dynamicValue[i].length - 1)
				{
					int one = dynamicValue[j - 1] [i - 1];
					int two = dynamicValue[j][ i - 1];
					if(one < two) {
						min = one;
						predecessors[j][i] = j -1;
					}
					else {
						min = two;
						predecessors[j][i] = j;
					}
				}
				else
				{
					int one = dynamicValue[j - 1] [i - 1];
					int two = dynamicValue[j][ i - 1];
					int three = dynamicValue[j + 1][ i - 1];

					if(one <= two && one <= three)
					{
						min = one;
						predecessors[j][i]=j-1;
					}
					else if(two <= one && two <= three)
					{
						min = two;
						predecessors[j][i]=j;
					}
					else if(three <= two && three <= one)
					{
						min = three;
						predecessors[j][i]=j+1;
					}
				}

				dynamicValue[j][i] = min + elements.valueOf(j,i);
			}
		}
		
		// Find the smallest of all minimum paths.
		int min = Integer.MAX_VALUE;
		int row = 0;
		for(int i = 0; i < dynamicValue[dynamicValue.length-1].length;i++)
		{
			if(dynamicValue[i][dynamicValue.length-1]< min)
			{
				min = dynamicValue[i][dynamicValue.length-1];
				row = i;
			}
		}
		controls.setTotal(min);
		
		// Set the path to red.
		for(int i = dynamicValue.length-1; i >=0; i--)
		{
			elements.setValues(row, i, Color.red);
			row = predecessors[row][i];
		}

	}

	public static void printArray(int[][] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			for (int j = 0; j < array.length; j++)
			{
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
}
