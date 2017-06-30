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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String args[]) {
		try {
			/*
			BMPLoader loader = new BMPLoader("1.bmp");
			ImageViewer iv = new ImageViewer(loader.getImageBuffer());
			iv.drawImage();
			SecureRandom sr = new SecureRandom();
			for(int x = 0; x < loader.getWidth(); x++){
				for(int y = 0; y < loader.getHeight(); y++){
					loader.setRed(x, y, (byte)Math.floor(sr.nextDouble() * 255));
					loader.setGreen(x, y, (byte)Math.floor(sr.nextDouble() * 255));
					loader.setBlue(x, y, (byte)Math.floor(sr.nextDouble() * 255));
				}
			}
			loader.recalcBuffer();
			ImageViewer iv2 = new ImageViewer(loader.getImageBuffer());
			iv2.drawImage();
			loader.saveToFile("forYou.bmp");
			*/

			System.out.print("Draw Image (i) encode (e) or decode (d)? : ");

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String temp = "";
			temp = reader.readLine();

			if(temp.equals("i")) drawImage();
			if(temp.equals("e")) encode();
			if(temp.equals("d")) decode();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	public static void drawImage() throws IOException, ObjectException {
		System.out.print("Enter BMP File to View: ");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String temp = "";
		temp = reader.readLine();
		BMPLoader loader = new BMPLoader(temp);

		ImageViewer iv = new ImageViewer(loader.getImageBuffer());
		iv.drawImage();
		reader.close();
		return;
	}

	public static void encode() throws IOException, NumberFormatException, BinaryException {
		System.out.print("Enter BMP File to Encode: ");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String temp = "";
		temp = reader.readLine();
		BMPLoader loader = new BMPLoader(temp);

		System.out.print("Enter maxBits value: ");
		temp = reader.readLine();
		BinaryModifier bm = new BinaryModifier(Integer.parseInt(temp));

		System.out.print("Enter message to Encode: ");
		loader.setPixelData(bm.encodeMessage(loader.getPixelData(), BinaryModifier.embedHeader(reader.readLine().getBytes())));
		loader.recalcBuffer();

		System.out.print("Enter File to Save: ");
		loader.saveToFile(reader.readLine());

		reader.close();
		return;
	}

	public static void decode() throws NumberFormatException, BinaryException, IOException {
		System.out.print("Enter BMP File to Decode: ");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String temp = "";
		temp = reader.readLine();
		BMPLoader loader = new BMPLoader(temp);

		System.out.print("Enter maxBits value: ");
		temp = reader.readLine();
		BinaryModifier bm = new BinaryModifier(Integer.parseInt(temp));

		System.out.println(new String(BinaryModifier.getData((bm.decodeMessage(loader.getPixelData())))));
		reader.close();
		return;
	}

}
