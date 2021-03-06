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

/**
 * Thrown if binary data encounters an error
 * 
 * @author 16lkarinja
 * 
 */
public class BinaryException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BinaryException() {
		super();
	}

	public BinaryException(String message) {
		super(message);
	}

	public BinaryException(String message, Throwable cause) {
		super(message, cause);
	}

	public BinaryException(Throwable cause) {
		super(cause);
	}
}