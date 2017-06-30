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

import java.security.SecureRandom;

public class OneTimePad {

	private int[] data;
	private int[] key;
	private boolean hasKey;

	/**
	 * Default constructor
	 */
	OneTimePad() {

	}

	/**
	 * Constructor with specified data to pad
	 * 
	 * @param data
	 *            Integer array of data to pad
	 */
	OneTimePad(int[] data) {
		this.data = data;
	}

	/**
	 * Sets the data to pad
	 * 
	 * @param data
	 *            Integer array of data to pad
	 * @return Returns void on completion
	 */
	public void setData(int[] data) {
		this.data = data;
		return;
	}

	/**
	 * Generates an integer array key of equal length to the data filled with secure random numbers
	 * 
	 * @return Returns void on completion
	 */
	public void genKey() {
		int x = 0;
		SecureRandom rand = new SecureRandom();
		this.key = new int[this.data.length];
		for (x = 0; x < this.key.length; x++) {
			int temp = rand.nextInt();
			this.key[x] = ((temp % 256) < 0) ? ((temp % 256) + 256) : (temp % 256);
		}
		this.hasKey = true;
		return;
	}
	
	/**
	 * Sets the One time pad *Key
	 * 
	 * @param key Key to set
	 */
	public void setKey(int[] key){
		this.key = key;
		return;
	}

	/**
	 * Decides whether the key has been created or not
	 * 
	 * @return Returns true if key is created, false if key is not created
	 */
	public boolean hasKey() {
		return this.hasKey;
	}

	/**
	 * Encrypts the set data with the pad
	 * 
	 * @return Returns an integer array of the encrypted padded data
	 */
	public int[] encrypt() {
		int x = 0;
		int[] crypted = new int[key.length];
		for (x = 0; x < this.data.length; x++) {
			crypted[x] = (this.data[x] + this.key[x]) % 256;
		}
		return crypted;
	}

	/**
	 * Decrypts the set data with the pad
	 * 
	 * @return Returns an integer array of the decrypted padded data
	 */
	public int[] decrypt() {
		int x = 0;
		int[] crypted = new int[key.length];
		for (x = 0; x < this.data.length; x++) {
			crypted[x] = ((this.data[x] - this.key[x]) < 0) ? ((this.data[x] - this.key[x]) + 256) : (this.data[x] - this.key[x]);
		}
		return crypted;
	}

}
