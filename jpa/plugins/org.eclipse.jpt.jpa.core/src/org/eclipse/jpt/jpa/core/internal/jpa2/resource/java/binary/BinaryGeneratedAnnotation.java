/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.GeneratedAnnotation;

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


	public BinaryGeneratedAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
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
	public ListIterable<String> getValues() {
		return new LiveCloneListIterable<String>(this.values);
	}

	public int getValuesSize() {
		return this.values.size();
	}

	public String getValue(int index) {
		return this.values.get(index);
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
