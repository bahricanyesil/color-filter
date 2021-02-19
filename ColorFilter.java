import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class ColorFilter {
	public static void main(String[] args) throws FileNotFoundException {
		String inputFile = args[1]; // I created this string to get the second argument of my program which is input.ppm file. 
		File file = new File(inputFile); // I created this file to reach its content. 
		int mode = Integer.parseInt(args[0]); // I created this integer to get the first argument (mode) which determines how my program works.
		if(mode==0) {
			File output = new File("output.ppm"); // I created this file to write the pixel values after I read them into the 3D array. 
			writing(reading(file), output); // I recalled the 'writing' method to write the final result to ppm file by using 'output' file and input file's 3D arrays. 
 		}
		if(mode==1) {
			File blackwhite = new File("black-and-white.ppm"); // I created this file to write the new black-and-white version of the ppm file.
			blackwhite(reading(file), blackwhite); // I recalled the 'blackwhite' method to obtain black-and-white form of image by using 'blackwhite' file and 3D array form of input file.  
		}
		if(mode==2) {
			String filterr = args[2]; // I created this string to get the third argument of my program which is filter.txt file. 
			File filter = new File(filterr); // I created this file to reach its content. 
			File convolutionfile = new File("convolution.ppm"); // I created this file to write the pixel values after I read them into the 3D array. 
			writingconvolution(reading(file), readingfilter(filter), convolutionfile); // I recalled the 'writingconvolution' method to apply the filters by using filters' 2D and input file's 3D arrays. 
			blackwhite(reading(convolutionfile), convolutionfile); // After applying the filters, I called the 'blackwhite' method to turn the image black-and-white to obtain the final image.
		}
		if(mode==3) {
			int range = Integer.parseInt(args[2]); // I created this integer to get the third argument which determines the range of my program.
			File quantized = new File("quantized.ppm"); // I created this file to save the quantized image after performing the operation.
			int [][][] arrayi = reading(file); // I created this 3D array to get the array form of input file. (Actually, I don't have to do it but just for simplicity. 
			quantization(quantized, range, arrayi); // I recalled the 'quantization' method which quantize the file by using quantized file, range, and 3D array form of input file. 
			writing(arrayi, quantized); // I recalled the 'writing' method to write the final result to ppm file by using quantized file and 3D array form of input file. 
		}
	}
	
	// I create this method to obtain black-and-white form of image by using 'blackwhite' file and 3D array form of input file.  
	public static void blackwhite(int [][][] RGB, File file) throws FileNotFoundException { 
		PrintStream output = new PrintStream(file);  // I created this PrintStream to write the black-white version of the file to a new file.
		output.println("P3");
		output.println(RGB.length+" "+RGB[0].length);
		output.println("255");
		int average = 0; // I created this integer to find the average value of each pixel each time.
		// I pass over each pixel and every value in pixels.
		for(int i=0; i<RGB.length; i++) {
			for(int j=0; j<RGB[0].length; j++) {
				for(int t=0; t<3; t++) {
					average += RGB [i][j][t];
				}
				average = average/3;
				output.print(average+ " " + average + " " + average + " ");
				output.print("\t");
				average = 0;
			}
			output.println();
		}
	}
	
	// I create this method to read the filter from the “filter.txt” file to a 2D array.  
	public static int[][] readingfilter(File file) throws FileNotFoundException {
		Scanner console = new Scanner(file); // I create this Scanner to make the file readable. 
		String name = console.nextLine(); // I create this String to take the first line which indicates the row and column number. 
		int columns = Integer.parseInt(name.substring(0, name.indexOf("x"))); // I create this integer as the number of columns.
		int rows = Integer.parseInt(name.substring(name.indexOf("x")+1)); // I create this integer as the number of rows.
		int [][] filter = new int [rows][columns]; // I create this 2D array to read the filter.
		// I pass over each value of the filter.
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {	
				filter[i][j] = console.nextInt();
			}
		}
		return filter;
	}
	
	// I create this method to apply the filters by using filters' 2D and input file's 3D arrays. 
	public static void writingconvolution(int RGB[][][], int filter[][], File convolutionfile) throws FileNotFoundException {
		int output2 = 0; // I create this integer as the value of 'red' part of each pixel.
		int output3 = 0; // I create this integer as the value of 'green' part of each pixel.
		int output4 = 0; // I create this integer as the value of 'blue' part of each pixel.
		int x = 0; // I create this integer to finish the outer while loop (I could create a for loop also).
		int y = 0;  // I create this integer to finish the inner while loop (I could create a for loop also).
		PrintStream output = new PrintStream(convolutionfile); // I created this PrintStream to write new version of the file to a new file.
		output.println("P3");
		output.println((RGB.length-(filter.length-1))+" "+(RGB[0].length-(filter.length-1)));
		output.println("255");
		// I pass over each pixel and every value in pixels.
		while(x<=RGB.length-filter.length) {
			while(y<=RGB[0].length-filter.length) {
					for(int i=0;i<filter.length;++i){
						for(int j=0;j<filter[0].length;++j){
							// I sum the 'red' values that I obtained by multiplying the value in the filter and the values in the pixels.
							output2 = output2 + RGB[x+i][y+j][0]*filter[i][j];
						}
					}
					if(output2<0) {
						output2 = 0;
					}
					if(output2>255) {
						output2 = 255;
					}
					output.print(output2 + " ");
					// I sum the 'green' values that I obtained by multiplying the value in the filter and the values in the pixels.
					for(int i=0;i<filter.length;++i){
						for(int j=0;j<filter[0].length;++j){
							output3 = output3 + RGB[x+i][y+j][1]*filter[i][j];
						}
					}
					if(output3<0) {
						output3 = 0;
					}
					if(output3>255) {
						output3 = 255;
					}
					output.print(output3 + " ");
					// I sum the 'blue' values that I obtained by multiplying the value in the filter and the values in the pixels.
					for(int i=0;i<filter.length;++i){
						for(int j=0;j<filter[0].length;++j){
							output4 = output4 + RGB[x+i][y+j][2]*filter[i][j];
						}
					}
					if(output4<0) {
						output4 = 0;
					}
					if(output4>255) {
						output4 = 255;
					}
					output.print(output4 + " ");
				output.print("\t");
				y++;
				output2 = 0;
				output3 = 0;
				output4 = 0;
			}
			output.println();
			x++;
			y=0;
		}
	}
	
	// I create this method to write the final result to ppm file by using a file and input file's 3D arrays in the 0th and 3rd mode. 
	public static void writing(int [][][] RGB, File file) throws FileNotFoundException {
		PrintStream output = new PrintStream(file); // I created this PrintStream to make the file writable.
		output.println("P3");
		output.println(RGB.length+" "+RGB[0].length);
		output.println("255");
		// I pass over each pixel and every value in pixels.
		for(int i=0; i<RGB.length; i++) {
			for(int j=0; j<RGB[0].length; j++) {
				for(int t=0; t<3; t++) {
					output.print(RGB[i][j][t] + " ");
				}
				output.print("\t");
			}
			output.println();
		}
	}
	
	// I create this method to read the contents of the PPM file into a 3D array. 
	public static int[][][] reading(File file) throws FileNotFoundException {
		Scanner console = new Scanner(file); // I create this Scanner to make the file readable. 
		console.nextLine();
		int columns = console.nextInt(); // I create this integer to know the number of columns.
		int rows = console.nextInt(); // I create this integer to know the number of rows.
		console.nextLine();
		console.nextLine();
		int [][][] RGB = new int [rows][columns][3]; // I create this 3D array to write the contents of the ppm file.
		// I pass over each pixel and every value in pixels.
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {	
				for(int t=0; t<3; t++) {
					RGB [i][j][t] = console.nextInt();
				}
			}
		}
		return RGB;
	}
	
	// I created this method to quantize each pixel by using quantized file, range, and 3D array form of input file. 
	public static void quantization(File quantized, int range, int [][][] RGB) {
		boolean [][][] kontrol = new boolean [RGB.length][RGB[0].length][RGB[0][0].length]; // I created this 3D boolean array to control whether the pixel has changed or not.
		// I pass over each pixel and every value in pixels row by row (starting from red to blue).
		for(int z=0; z<3; z++) {
			for(int x=0; x<RGB.length; x++) {
				for(int y=0; y<RGB[0].length; y++) {
					 checkCell(x, y, z, range, kontrol, RGB);
					 kontrol[x][y][z] = true;
				}
			}
		}
	}
	
	// I created this method to be able to quantize pixels more regularly and simply instead of doing this work in only one method. 
	// I looked at the values of 6 neighbors of each element after I recalled this method in the quantization method. 
	// if the neighbors of an element have the value within the same range as the element itself, then I made those pixel values equal to the value of the element.
	public static void checkCell(int x, int y, int z, int range, boolean[][][] kontrol, int[][][] RGB) {
		// I controlled whether the pixel is the final pixel or not.
		if(y!=RGB[0].length-1) {
			// I controlled whether the neighboring element has the pixel value within the same range or not. 
			if(RGB[x][y+1][z]<=RGB[x][y][z]+range&&RGB[x][y+1][z]>=RGB[x][y][z]-range&&kontrol[x][y+1][z]==false) {
				RGB[x][y+1][z]=RGB[x][y][z];
				kontrol[x][y+1][z] = true;
				checkCell(x, y+1, z, range, kontrol, RGB);
				// This is the recursion part of my code. I recalled the method with the new parameters to look at the neighbor's neighbor.
				// I'm not writing again because I repeat this part in each if.
			}
		}
		// I controlled whether the pixel is the first pixel or not.
		if(y!=0) {
			// I controlled whether the neighboring element has the pixel value within the same range or not. 
			if(RGB[x][y-1][z]<=RGB[x][y][z]+range&&RGB[x][y-1][z]>=RGB[x][y][z]-range&&kontrol[x][y-1][z]==false) {
				RGB[x][y-1][z]=RGB[x][y][z];
				kontrol[x][y-1][z] = true;
				checkCell(x, y-1, z, range, kontrol, RGB);
			}
		}
		// I controlled whether the row is the final row or not.
		if(x!=RGB.length-1) {
			// I controlled whether the neighboring element has the pixel value within the same range or not. 
			if(RGB[x+1][y][z]<=RGB[x][y][z]+range&&RGB[x+1][y][z]>=RGB[x][y][z]-range&&kontrol[x+1][y][z]==false) {
				RGB[x+1][y][z]=RGB[x][y][z];
				kontrol[x+1][y][z] = true;
				checkCell(x+1, y, z, range, kontrol, RGB);
			}
		}
		// I controlled whether the row is the first row or not.
		if(x!=0) {
			// I controlled whether the neighboring element has the pixel value within the same range or not. 
			if(RGB[x-1][y][z]<=RGB[x][y][z]+range&&RGB[x-1][y][z]>=RGB[x][y][z]-range&&kontrol[x-1][y][z]==false) {
				RGB[x-1][y][z]=RGB[x][y][z];
				kontrol[x-1][y][z] = true;
				checkCell(x-1, y, z, range, kontrol, RGB);
			}
		}
		// I controlled whether it is the last value in each pixel or not.
		if(z!=2) {
			// I controlled whether the neighboring element has the pixel value within the same range or not. 
			if(RGB[x][y][z+1]<=RGB[x][y][z]+range&&RGB[x][y][z+1]>=RGB[x][y][z]-range&&kontrol[x][y][z+1]==false) {
				RGB[x][y][z+1]=RGB[x][y][z];
				kontrol[x][y][z+1] = true;
				checkCell(x, y, z+1, range, kontrol, RGB);
			}
		}
		// I controlled whether it is the first value in each pixel or not.
		if(z!=0) {
			// I controlled whether the neighboring element has the pixel value within the same range or not. 
			if(RGB[x][y][z-1]<=RGB[x][y][z]+range&&RGB[x][y][z-1]>=RGB[x][y][z]-range&&kontrol[x][y][z-1]==false) {
				RGB[x][y][z-1]=RGB[x][y][z];
				kontrol[x][y][z-1] = true;
				checkCell(x, y, z-1, range, kontrol, RGB);
			}
		}
	}
}
