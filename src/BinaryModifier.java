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

public class BinaryModifier {
	/**
	 * Maximum bits to change per byte
	 */
	private int maxBits = 0;

	/**
	 * Default Constructor
	 */
	public BinaryModifier() {

	}

	/**
	 * Constructor specifying maximum number of bits to modify per byte of data
	 * 
	 * @param maxBits
	 *            Max number of bits to modify per byte
	 * @throws BinaryException
	 */
	public BinaryModifier(int maxBits) throws BinaryException {
		if (maxBits > 8)
			throw new BinaryException("Can modify at most 8 bits per byte");
		if (maxBits < 1)
			throw new BinaryException("Must modify at least 1 bits per byte");
		this.maxBits = maxBits;
	}

	/**
	 * Set maximum number of bits to modify per byte of data
	 * 
	 * @param maxBits
	 *            Max number of bits to modify per byte
	 * @throws BinaryException
	 */
	public void setMaxBits(int maxBits) throws BinaryException {
		if (maxBits > 8)
			throw new BinaryException("Can modify at most 8 bits per byte");
		if (maxBits < 1)
			throw new BinaryException("Must modify at least 1 bits per byte");
		this.maxBits = maxBits;
		return;
	}

	/**
	 * Gets maximum number of bits to modify per byte of data
	 * 
	 * @return Max number of bits to modify per byte
	 */
	public int getMaxBits() {
		return this.maxBits;
	}

	/**
	 * Encodes a message in a byte array
	 * 
	 * @param data
	 *            Data to encode message in it
	 * @param message
	 *            Message to encode
	 * @return New data with message encoded in it
	 */
	public byte[] encodeMessage(byte[] data, byte[] message) {
		byte[] encoded = data;
		boolean[] messageBits = byteArrToBoolArr(message);
		int index = 0;
		for (int x = 0; x < messageBits.length; x++) {
			encoded[index] = messageBits[x] ? setBit(encoded[index], x % this.maxBits) : unsetBit(encoded[index], x % this.maxBits);
			if ((x + 1) % this.maxBits == 0)
				index++;
		}
		return encoded;
	}

	/**
	 * Decodes a message from a byte array
	 * 
	 * @param data
	 *            Data with message encoded in it
	 * @return Decoded message
	 */
	public byte[] decodeMessage(byte[] data) {
		boolean[] messageBits = new boolean[data.length * this.maxBits];
		int index = 0;
		for (int x = 0; x < messageBits.length; x++) {
			messageBits[x] = getBit(data[index], x % this.maxBits);
			if ((x + 1) % this.maxBits == 0)
				index++;
		}
		return boolArrToByteArr(messageBits);
	}

	/**
	 * Unsets a specific bit in a byte to 0
	 * 
	 * @param data
	 *            Byte to unset bit of
	 * 
	 * @param pos
	 *            Bit to unset
	 * 
	 * @return New byte with specified bit unset
	 */
	public static byte unsetBit(byte data, int pos) {
		return (byte) (data & ~((1 << pos)));
	}

	/**
	 * Sets a specific bit in a byte to 1
	 * 
	 * @param data
	 *            Byte to set bit of
	 * 
	 * @param pos
	 *            Bit to set
	 * 
	 * @return New byte with specified bit set
	 */
	public static byte setBit(byte data, int pos) {
		return (byte) (data | ((1 << pos)));
	}

	/**
	 * Get the bit value of a specific position in a byte
	 * 
	 * @param data
	 *            Byte to get bit value from
	 * 
	 * @param pos
	 *            Position of bit to get value of
	 * 
	 * @return Bit value of specified position in byte
	 */
	public static boolean getBit(byte data, int pos) {
		return ((data >>> pos) & 0x01) == 1;
	}

	/**
	 * Unsets bits (set to 0) of a byte. Number of bits unset is determined from the value of maxBits.
	 * 
	 * @param data
	 *            Byte to unset bits
	 * @return
	 */
	public byte unsetBits(byte data) {
		byte newByte = 0;
		switch (this.maxBits) {
		case 1:
			// Unset the 0th bit
			newByte = (byte) (data & ~((1 << 0)));
			break;
		case 2:
			// Unset the 0th through 1st bit
			newByte = (byte) (data & ~((1 << 0) | (1 << 1)));
			break;
		case 3:
			// Unset the 0th through 2nd bit
			newByte = (byte) (data & ~((1 << 0) | (1 << 1) | (1 << 2)));
			break;
		case 4:
			// Unset the 0th through 3rd bit
			newByte = (byte) (data & ~((1 << 0) | (1 << 1) | (1 << 2) | (1 << 3)));
			break;
		case 5:
			// Unset the 0th through 4th bit
			newByte = (byte) (data & ~((1 << 0) | (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4)));
			break;
		case 6:
			// Unset the 0th through 5th bit
			newByte = (byte) (data & ~((1 << 0) | (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5)));
			break;
		case 7:
			// Unset the 0th through 6th bit
			newByte = (byte) (data & ~((1 << 0) | (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5) | (1 << 6)));
			break;
		case 8:
			// Unset the 0th through 7th bit
			newByte = (byte) (data & ~((1 << 0) | (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7)));
			break;
		}
		return newByte;
	}

	/**
	 * Sets bits (set to 1) of a byte. Number of bits set is determined from the value of maxBits.
	 * 
	 * @param data
	 *            Byte to set bits
	 * @return
	 */
	public byte setBits(byte data) {
		byte newByte = 0;
		switch (this.maxBits) {
		case 1:
			// Set the 0th bit
			newByte = (byte) (data | ((1 << 0)));
			break;
		case 2:
			// Set the 0th through 1st bit
			newByte = (byte) (data | ((1 << 0) | (1 << 1)));
			break;
		case 3:
			// Set the 0th through 2nd bit
			newByte = (byte) (data | ((1 << 0) | (1 << 1) | (1 << 2)));
			break;
		case 4:
			// Set the 0th through 3rd bit
			newByte = (byte) (data | ((1 << 0) | (1 << 1) | (1 << 2) | (1 << 3)));
			break;
		case 5:
			// Set the 0th through 4th bit
			newByte = (byte) (data | ((1 << 0) | (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4)));
			break;
		case 6:
			// Set the 0th through 5th bit
			newByte = (byte) (data | ((1 << 0) | (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5)));
			break;
		case 7:
			// Set the 0th through 6th bit
			newByte = (byte) (data | ((1 << 0) | (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5) | (1 << 6)));
			break;
		case 8:
			// Set the 0th through 7th bit
			newByte = (byte) (data | ((1 << 0) | (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7)));
			break;
		}
		return newByte;
	}

	/**
	 * Converts a byte to a boolean array (array of bits)
	 * 
	 * @param b
	 *            Byte to convert
	 * 
	 * @return Boolean array of the byte
	 */
	public static boolean[] byteToBool(byte b) {
		boolean bool[] = new boolean[8];
		bool[0] = ((b & 0x01) != 0);
		bool[1] = ((b & 0x02) != 0);
		bool[2] = ((b & 0x03) != 0);
		bool[3] = ((b & 0x04) != 0);
		bool[3] = ((b & 0x05) != 0);
		bool[3] = ((b & 0x06) != 0);
		bool[3] = ((b & 0x07) != 0);
		bool[3] = ((b & 0x08) != 0);
		return bool;
	}

	/**
	 * Converts a byte array to a boolean array
	 * 
	 * @param b
	 *            Byte array to convert
	 * 
	 * @return Boolean representation of the given byte array
	 */
	public static boolean[] byteArrToBoolArr(byte[] b) {
		boolean bool[] = new boolean[b.length * 8];
		for (int x = 0; x < bool.length; x++) {
			bool[x] = false;
			if ((b[x / 8] & (1 << (7 - (x % 8)))) > 0)
				bool[x] = true;
		}
		return bool;
	}

	/**
	 * Converts a boolean array to a byte array
	 * 
	 * @param bool
	 *            Boolean array to convert
	 * 
	 * @return Byte representation of the given boolean array
	 */
	public static byte[] boolArrToByteArr(boolean[] bool) {
		byte[] b = new byte[bool.length / 8];
		for (int x = 0; x < b.length; x++) {
			for (int y = 0; y < 8; y++) {
				if (bool[x * 8 + y]) {
					b[x] |= (128 >>> y);
				}
			}
		}
		return b;
	}

	/**
	 * Embeds the length of data as header along with the data
	 * 
	 * @param data
	 *            Data to add length of data as header to
	 * 
	 * @return New data with length of data as header
	 */
	public static byte[] embedHeader(byte[] data) {
		int len = data.length;
		byte[] b = new byte[len + 3];

		b[0] = (byte) (len >>> 16);
		b[1] = (byte) (len >>> 8);
		b[2] = (byte) (len >>> 0);

		for (int x = 0; x < len; x++) {
			b[x + 3] = data[x];
		}

		return b;
	}

	/**
	 * Gets message length from header
	 * 
	 * @param data
	 *            Data with header to get message length from
	 * 
	 * @return Length of message in data with header
	 */
	public static int getHeader(byte[] data) {
		return 0 << 24 | (data[0] & 0xFF) << 16 | (data[1] & 0xFF) << 8 | (data[2] & 0xFF);
	}

	/**
	 * Gets message from data according to its header data
	 * 
	 * @param data
	 *            Data to get message from
	 * 
	 * @return Message data retrieved from given data
	 */
	public static byte[] getData(byte[] data) {
		byte[] b = new byte[getHeader(data)];
		for (int x = 0; x < b.length; x++) {
			b[x] = data[x + 3];
		}

		return b;
	}

	/**
	 * Converts a byte (8 bits) to an integer
	 * 
	 * @param data
	 *            Byte to convert
	 * @return
	 */
	public static int btod(String data) {
		return Integer.parseInt(data, 2);
	}

	/**
	 * Converts an integer to a binary literal
	 * 
	 * @param data
	 *            Integer to convert
	 * @return
	 */
	public static String dtob(int data) {
		return String.format("%8s", Integer.toBinaryString(data & 0xFF)).replace(' ', '0');
	}

	/**
	 * Converts a byte array to an integer array
	 * 
	 * @param data
	 *            Byte array to convert
	 * 
	 * @return Integer array of original bytes
	 */
	public static int[] byteArrToIntArr(byte[] data) {
		int[] i = new int[data.length];
		for (int x = 0; x < data.length; x++) {
			i[x] = (int) data[x];
		}
		return i;
	}

	/**
	 * Converts an integer array to a byte array
	 * 
	 * @param data
	 *            Integer array to convert
	 * 
	 * @return Byte array of original integers
	 */
	public static byte[] intArrToByteArr(int[] data) {
		byte[] b = new byte[data.length];
		for (int x = 0; x < data.length; x++) {
			b[x] = (byte) data[x];
		}
		return b;
	}
}
