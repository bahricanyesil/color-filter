# color-filter
Color Filter Project - Introduction to Computer Engineering

1.	PROBLEM DESCRIPTION

We were asked to make some calculations on 3D pixel arrays of some images. This task should be executed in at least two methods. We should use the ppm image format for reading and writing image files. Firstly, we should read a ppm image file into a 3D array and then write it to another ppm file. Secondly, we should calculate a color-channel based average and write a black-and-white version of the input image as ppm. Thirdly, we should perform convolution operation using a 2D array which is called filter on the input image and then write the final result of this operation to another ppm file. Finally, we should check the values of the neighboring pixels to see if they are within a given range. If these pixels are in the same range, we should make them equal. This process is called color quantization and we should implemet this process to every pixel, starting from red to blue, row by row. Then we should write the quantized image to a final ppm file. The easiest way to handle this project is to create several methods for different parts of the project. Each method in my project does specific task and I recalled them according to the mode number.

2.	PROBLEM SOLUTION

- I used 7 static methods in addition to my main method. Even though I found different ways that have more or less than seven methods, I think that the best readable format of the code shouldn’t have more or less methods. For the first part of the project, I read the contents of the input.ppm file into a 3D array. I did this part with the reading method. I create a 3D integer array and filled with the values given in the body part of the PPM file. 

![color-filter](https://github.com/bahricanyesil/color-filter/blob/main/screenshot/Case1.png)

- For second part of my project, I created the ‘writing’ method to write the contents of the 3D pixel array to a PPM file. I created a new file whose name is “output.ppm”. After I read the input.ppm file into 3D array, I filled this new file with those pixel values according to the rules of the ppm format. I also checked the image in Irfanview. 

- For the third part, I created ‘black-and-white’ method to calculate the color-channel average values and convert the colored image to black-and-white. I calculated the average color values ,by creating an integer and changing its value in for loops, of each pixel using the three color channels (RGB). Then I assigned the calculated average value to all three color channels for all the pixels in the image to obtain the black white version of the image. Finally, I wrote the new black-and-white version to another ppm image whose name is “black-and-white.ppm”.

![color-filter](https://github.com/bahricanyesil/color-filter/blob/main/screenshot/Case2.png)

- For the forth part, I created and used two methods. The name of the first one is ‘writingconvolution’ and the name of the second one is ‘readingfilter’. I recalled the ‘readingfilter’ method as a parameter in the ‘writingconvolution’ method since it returns the 2D array form of the filter. In the ‘readingfilter’ method, I read the filter from the “filter.txt” file to a 2D array like I did in the ‘reading’ method. Then, I recalled the writingconvolution method with the parameters which are readingfilter and reading methods since they return integer arrays. I applied convolution to each color channel of the image separately. After that, I saved the output file to “convolution.ppm”. And finally, after applying the filters, I called the “blackwhite” method to turn the image black-and-white to obtain the final image.

![color-filter](https://github.com/bahricanyesil/color-filter/blob/main/screenshot/Case3.png)
 
- For the final and the the most hard (in my opinion) part of the project, I created two methods whose names are “quantization” and “checkCell”. In the “quantization” method, I created a boolean array to control whether I changed the pixel values or not. I passed over each pixel and every value in the pixels by using for loops. I also called the “checkCell” method each time. After I traversed the pixels of an input image one by one, in the “checkCell” method, I checked if the values of a pixel’s neighbors are within a given range by following the rules that you mentioned in the description pdf, and made their values equal if they are in the same value range. I started from color channel 0 (red), went row by row, and then to channel 1 (green), went row by row, and then finally to channel 2 (blue), went row by row and finished. I used 6 different ‘if’ statements for each neighbor. If I changed the value of that pixel, I also changed its value in the boolean array to true from false. And of course, I recalled the method itself with the values of the new pixel to obtain a recursive program. After performing all of these operations, I saved the quantized image as “quantized.ppm”.

![color-filter](https://github.com/bahricanyesil/color-filter/blob/main/screenshot/Case4.png)
