/*
Copyright © 2015-2017 Leejae Karinja

This file is part of Java Bitmap Message.

Java Bitmap Message is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Java Bitmap Message is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Java Bitmap Message.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Loads a Bitmap Image
 * 
 * @author 16lkarinja
 * 
 */
public class BMPLoader {

	/**
	 * Alpha value per group of bytes
	 */
	public static final int RGB_ALPHA = 0;
	/**
	 * Red value per group of bytes
	 */
	public static final int RGB_RED = 1;
	/**
	 * Green value per group of bytes
	 */
	public static final int RGB_GREEN = 2;
	/**
	 * Blue value per group of bytes
	 */
	public static final int RGB_BLUE = 3;

	/**
	 * If the BMP image is using an alpha layer, almost always it is not (Keep as false)
	 */
	private boolean usingAlpha = false;

	/**
	 * ImageBuffer of the bitmap image
	 */
	private BufferedImage imageBuffer = null;

	/**
	 * File containing the bitmap image
	 */
	private File bmpFile;

	/**
	 * Height of the Bitmap Image
	 */
	private int bmpHeight;
	/**
	 * Width of the Bitmap Image
	 */
	private int bmpWidth;
	/**
	 * RGB Data of the Bitmap Image, in the form of "Y, X, Color"
	 */
	private byte[][][] rgbData;

	/**
	 * Default Constructor
	 */
	public BMPLoader() {

	}

	/**
	 * Constructs a BMPLoader Object with a specified Bitmap File
	 * 
	 * @param fileName
	 *            Name of the Bitmap File to associate the BMPLoader Object with
	 */
	public BMPLoader(String fileName) {
		try {
			this.setFile(fileName);
		} catch (FileException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the Bitmap File
	 * 
	 * @param fileName
	 *            Name of the Bitmap File to associate the BMPLoader Object with
	 * @throws FileException
	 */
	public void setFile(String fileName) throws FileException {
		this.bmpFile = new File(fileName);
		if (!this.bmpFile.exists())
			throw new FileException("The specified File does not exist");
		if (!this.bmpFile.isFile())
			throw new FileException("The specified File is not a File");
		if (!this.bmpFile.canRead())
			throw new FileException("The specified File cannot be read");
		if (!this.bmpFile.getName().toLowerCase().endsWith(".bmp"))
			throw new FileException("The specified File is not a Bitmap Image");
		this.loadFile();
		return;
	}

	/**
	 * Loads the Image Data into a byte array of Y, X, Color
	 */
	public void loadFile() {
		try {
			this.imageBuffer = ImageIO.read(this.bmpFile);
			this.bmpHeight = imageBuffer.getHeight();
			this.bmpWidth = imageBuffer.getWidth();
			this.rgbData = new byte[this.bmpHeight][this.bmpWidth][4];
			for (int y = 0; y < this.bmpHeight; y++) {
				for (int x = 0; x < this.bmpWidth; x++) {
					int pixelData = imageBuffer.getRGB(x, y);
					if (this.usingAlpha)
						this.rgbData[y][x][RGB_ALPHA] = (byte) (pixelData >> 24);
					this.rgbData[y][x][RGB_RED] = (byte) (pixelData >> 16);
					this.rgbData[y][x][RGB_GREEN] = (byte) (pixelData >> 8);
					this.rgbData[y][x][RGB_BLUE] = (byte) (pixelData);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	public void saveToFile(String fileName) {
		try {
			ImageIO.write(this.imageBuffer, "BMP", new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	public byte[] getPixelData() {
		byte[] data;
		if (this.usingAlpha) {
			data = new byte[this.bmpHeight * this.bmpWidth * 4];
		} else {
			data = new byte[this.bmpHeight * this.bmpWidth * 3];
		}
		int index = 0;
		for (int x = 0; x < this.bmpWidth; x++) {
			for (int y = 0; y < this.bmpHeight; y++) {
				if (this.usingAlpha) {
					data[index] = this.rgbData[y][x][RGB_ALPHA];
					index++;
				}
				data[index] = this.rgbData[y][x][RGB_RED];
				index++;
				data[index] = this.rgbData[y][x][RGB_GREEN];
				index++;
				data[index] = this.rgbData[y][x][RGB_BLUE];
				index++;
			}
		}
		return data;
	}

	public void setPixelData(byte[] data) {
		int index = 0;
		for (int x = 0; x < this.bmpWidth; x++) {
			for (int y = 0; y < this.bmpHeight; y++) {
				if (this.usingAlpha) {
					this.rgbData[y][x][RGB_ALPHA] = data[index];
					index++;
				}
				this.rgbData[y][x][RGB_RED] = data[index];
				index++;
				this.rgbData[y][x][RGB_GREEN] = data[index];
				index++;
				this.rgbData[y][x][RGB_BLUE] = data[index];
				index++;
			}
		}
		return;
	}

	/**
	 * Gets a String representation of the image data at the specified pixel
	 * 
	 * @param x
	 *            X coordinate of the pixel
	 * @param y
	 *            Y coordinate of the pixel
	 * 
	 * @return String representation of the image data
	 * @throws PixelException
	 */
	public String pixelData(int x, int y) throws PixelException {
		String pixelData = "";
		try {
			if (this.usingAlpha)
				pixelData += "Alpha: " + this.rgbData[y][x][RGB_ALPHA] + "\n";
			pixelData += "Red: " + this.rgbData[y][x][RGB_RED] + "\n";
			pixelData += "Green: " + this.rgbData[y][x][RGB_GREEN] + "\n";
			pixelData += "Blue: " + this.rgbData[y][x][RGB_BLUE] + "\n";
		} catch (IndexOutOfBoundsException e) {
			throw new PixelException();
		}
		return pixelData;
	}

	/**
	 * Gets a byte representation of the alpha data at the specified pixel
	 * 
	 * @param x
	 *            X coordinate of the pixel
	 * @param y
	 *            Y coordinate of the pixel
	 * 
	 * @return Byte value of the Alpha level
	 * @throws PixelException
	 */
	public byte getAplha(int x, int y) throws PixelException {
		try {
			return this.rgbData[y][x][RGB_ALPHA];
		} catch (IndexOutOfBoundsException e) {
			throw new PixelException();
		}
	}

	/**
	 * Gets a byte representation of the red data at the specified pixel
	 * 
	 * @param x
	 *            X coordinate of the pixel
	 * @param y
	 *            Y coordinate of the pixel
	 * 
	 * @return Byte value of the Red level
	 * @throws PixelException
	 */
	public byte getRed(int x, int y) throws PixelException {
		try {
			return this.rgbData[y][x][RGB_RED];
		} catch (IndexOutOfBoundsException e) {
			throw new PixelException();
		}
	}

	/**
	 * Gets a byte representation of the green data at the specified pixel
	 * 
	 * @param x
	 *            X coordinate of the pixel
	 * @param y
	 *            Y coordinate of the pixel
	 * 
	 * @return Byte value of the Green level
	 * @throws PixelException
	 */
	public byte getGreen(int x, int y) throws PixelException {
		try {
			return this.rgbData[y][x][RGB_GREEN];
		} catch (IndexOutOfBoundsException e) {
			throw new PixelException();
		}
	}

	/**
	 * Gets a byte representation of the blue data at the specified pixel
	 * 
	 * @param x
	 *            X coordinate of the pixel
	 * @param y
	 *            Y coordinate of the pixel
	 * 
	 * @return Byte value of the Blue level
	 * @throws PixelException
	 */
	public byte getBlue(int x, int y) throws PixelException {
		try {
			return this.rgbData[y][x][RGB_BLUE];
		} catch (IndexOutOfBoundsException e) {
			throw new PixelException();
		}
	}

	/**
	 * Sets the alpha value at the specified pixel
	 * 
	 * @param x
	 *            X coordinate of the pixel
	 * @param y
	 *            Y coordinate of the pixel
	 * @param value
	 *            Byte value to set
	 * 
	 * @throws PixelException
	 */
	public void setAplha(int x, int y, byte value) throws PixelException {
		try {
			this.rgbData[y][x][RGB_ALPHA] = value;
		} catch (IndexOutOfBoundsException e) {
			throw new PixelException();
		}
		return;
	}

	/**
	 * Sets the alpha value at the specified pixel
	 * 
	 * @param x
	 *            X coordinate of the pixel
	 * @param y
	 *            Y coordinate of the pixel
	 * @param value
	 *            Byte value to set
	 * 
	 * @throws PixelException
	 */
	public void setRed(int x, int y, byte value) throws PixelException {
		try {
			this.rgbData[y][x][RGB_RED] = value;
		} catch (IndexOutOfBoundsException e) {
			throw new PixelException();
		}
		return;
	}

	/**
	 * Sets the alpha value at the specified pixel
	 * 
	 * @param x
	 *            X coordinate of the pixel
	 * @param y
	 *            Y coordinate of the pixel
	 * @param value
	 *            Byte value to set
	 * 
	 * @throws PixelException
	 */
	public void setGreen(int x, int y, byte value) throws PixelException {
		try {
			this.rgbData[y][x][RGB_GREEN] = value;
		} catch (IndexOutOfBoundsException e) {
			throw new PixelException();
		}
		return;
	}

	/**
	 * Sets the alpha value at the specified pixel
	 * 
	 * @param x
	 *            X coordinate of the pixel
	 * @param y
	 *            Y coordinate of the pixel
	 * @param value
	 *            Byte value to set
	 * 
	 * @throws PixelException
	 */
	public void setBlue(int x, int y, byte value) throws PixelException {
		try {
			this.rgbData[y][x][RGB_BLUE] = value;
		} catch (IndexOutOfBoundsException e) {
			throw new PixelException();
		}
		return;
	}

	public void recalcBuffer() {
		for (int x = 0; x < this.bmpWidth; x++) {
			for (int y = 0; y < this.bmpHeight; y++) {
				int a = 0;
				if (this.usingAlpha)
					a = (this.rgbData[y][x][RGB_ALPHA] << 24) & 0xFF000000;
				int r = (this.rgbData[y][x][RGB_RED] << 16) & 0x00FF0000;
				int g = (this.rgbData[y][x][RGB_GREEN] << 8) & 0x0000FF00;
				int b = this.rgbData[y][x][RGB_BLUE] & 0x000000FF;
				this.imageBuffer.setRGB(x, y, 0x00000000 | a | r | g | b);
			}
		}
		return;
	}

	/**
	 * Gets the height of the BMP image
	 * 
	 * @return Height of image
	 */
	public int getHeight() {
		return this.bmpHeight;
	}

	/**
	 * Gets the width of the BMP image
	 * 
	 * @return Width of image
	 */
	public int getWidth() {
		return this.bmpWidth;
	}

	/**
	 * Gets the Image Buffer of the BMP image
	 * 
	 * @return BufferedImage of the BMP image
	 * @throws ObjectException
	 */
	public BufferedImage getImageBuffer() throws ObjectException {
		if (this.imageBuffer == null)
			throw new ObjectException("Image is null");
		return this.imageBuffer;
	}

}
