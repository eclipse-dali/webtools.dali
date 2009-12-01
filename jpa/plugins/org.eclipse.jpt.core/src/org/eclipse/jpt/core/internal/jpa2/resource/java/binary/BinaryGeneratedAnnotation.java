/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.binary;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.GeneratedAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.annotation.Generated
 */
public final class BinaryGeneratedAnnotation
	extends BinaryAnnotation
	implements GeneratedAnnotation
{
	private final Vector<String> values;
	private String date;
	private String comments;


	public BinaryGeneratedAnnotation(JavaResourcePersistentType parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.values = this.buildValues();
		this.date = this.buildDate();
		this.comments = this.buildComments();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.updateValues();
		this.setDate_(this.buildDate());
		this.setComments_(this.buildComments());
	}

	// TODO
	private void updateValues() {
		throw new UnsupportedOperationException();
	}


	// ********** GeneratedAnnotation implementation **********

	// ***** values
	public ListIterator<String> values() {
		return new CloneListIterator<String>(this.values);
	}

	public int valuesSize() {
		return this.values.size();
	}

	private Vector<String> buildValues() {
		Object[] jdtValues = this.getJdtMemberValues(VALUE_ELEMENT_NAME);
		Vector<String> result = new Vector<String>(jdtValues.length);
		for (Object value : jdtValues) {
			result.add((String) value);
		}
		return result;
	}

	public void addValue(String value) {
		throw new UnsupportedOperationException();
	}

	public void addValue(int index, String value) {
		throw new UnsupportedOperationException();
	}

	public void moveValue(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeValue(String value) {
		throw new UnsupportedOperationException();
	}

	public void removeValue(int index) {
		throw new UnsupportedOperationException();
	}

	// ***** date
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		throw new UnsupportedOperationException();
	}

	private void setDate_(String date) {
		String old = this.date;
		this.date = date;
		this.firePropertyChanged(DATE_PROPERTY, old, date);
	}

	private String buildDate() {
		return (String) this.getJdtMemberValue(DATE_ELEMENT_NAME);
	}

	// ***** comments
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		throw new UnsupportedOperationException();
	}

	private void setComments_(String comments) {
		String old = this.comments;
		this.comments = comments;
		this.firePropertyChanged(COMMENTS_PROPERTY, old, comments);
	}

	private String buildComments() {
		return (String) this.getJdtMemberValue(COMMENTS_ELEMENT_NAME);
	}

}
