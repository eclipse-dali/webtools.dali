/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.swing;

import javax.swing.Icon;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;

/**
 * This implementation of Displayable converts any Object
 * to a Displayable. Subclass it to override #displayString() and
 * #icon() if necessary. Change notification will be fired if the
 * object is changed.
 * 
 * This can be used for Strings - the display string
 * will simply be the String itself.
 */
public class SimpleDisplayable
	extends AbstractModel
	implements Displayable
{
	/** The object to be converted to a Displayable. */
	protected Object object;


	/**
	 * Construct a displayable for the specified object.
	 */
	public SimpleDisplayable(Object object) {
		super();
		this.object = object;
	}

	public SimpleDisplayable(boolean b) {
		this(Boolean.valueOf(b));
	}

	public SimpleDisplayable(char c) {
		this(Character.valueOf(c));
	}

	public SimpleDisplayable(byte b) {
		this(Byte.valueOf(b));
	}

	public SimpleDisplayable(short s) {
		this(Short.valueOf(s));
	}

	public SimpleDisplayable(int i) {
		this(Integer.valueOf(i));
	}

	public SimpleDisplayable(long l) {
		this(Long.valueOf(l));
	}

	public SimpleDisplayable(float f) {
		this(Float.valueOf(f));
	}

	public SimpleDisplayable(double d) {
		this(Double.valueOf(d));
	}


	// ********** Displayable implementation **********

	public String displayString() {
		return this.object.toString();
	}

	public Icon icon() {
		return null;
	}


	// ********** accessors **********

	public Object getObject() {
		return this.object;
	}

	public void setObject(Object object) {
		String oldDisplayString = this.displayString();
		Icon oldIcon = this.icon();
		this.object = object;
		this.firePropertyChanged(DISPLAY_STRING_PROPERTY, oldDisplayString, this.displayString());
		this.firePropertyChanged(ICON_PROPERTY, oldIcon, this.icon());
	}

	public boolean getBoolean() {
		return ((Boolean) this.object).booleanValue();
	}

	public void setBoolean(boolean b) {
		this.setObject(Boolean.valueOf(b));
	}

	public char getChar() {
		return ((Character) this.object).charValue();
	}

	public void setChar(char c) {
		this.setObject(Character.valueOf(c));
	}

	public byte getByte() {
		return ((Byte) this.object).byteValue();
	}

	public void setByte(byte b) {
		this.setObject(Byte.valueOf(b));
	}

	public short getShort() {
		return ((Short) this.object).shortValue();
	}

	public void setShort(short s) {
		this.setObject(Short.valueOf(s));
	}

	public int getInt() {
		return ((Integer) this.object).intValue();
	}

	public void setInt(int i) {
		this.setObject(Integer.valueOf(i));
	}

	public long getLong() {
		return ((Long) this.object).longValue();
	}

	public void setLong(long l) {
		this.setObject(Long.valueOf(l));
	}

	public float getFloat() {
		return ((Float) this.object).floatValue();
	}

	public void setFloat(float f) {
		this.setObject(Float.valueOf(f));
	}

	public double getDouble() {
		return ((Double) this.object).doubleValue();
	}

	public void setDouble(double d) {
		this.setObject(Double.valueOf(d));
	}


	// ********** override methods **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.object);
	}

}
