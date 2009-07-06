/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ListIterator;

import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

/**
 * javax.persistence.JoinTable
 */
public class NullJoinTableAnnotation
	extends NullBaseTableAnnotation
	implements JoinTableAnnotation
{	
	public NullJoinTableAnnotation(JavaResourceNode parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected JoinTableAnnotation buildAnnotation() {
		return (JoinTableAnnotation) this.addSupportingAnnotation();
	}

	// ***** join columns
	public ListIterator<JoinColumnAnnotation> joinColumns() {
		return EmptyListIterator.instance();
	}

	public int joinColumnsSize() {
		return 0;
	}

	public JoinColumnAnnotation joinColumnAt(int index) {
		return null;
	}

	public int indexOfJoinColumn(JoinColumnAnnotation joinColumn) {
		throw new UnsupportedOperationException();
	}

	public JoinColumnAnnotation addJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public JoinColumnAnnotation initializeJoinColumns() {
		throw new UnsupportedOperationException();
	}

	// ***** inverse join columns
	public ListIterator<JoinColumnAnnotation> inverseJoinColumns() {
		return EmptyListIterator.instance();
	}

	public int inverseJoinColumnsSize() {
		return 0;
	}

	public JoinColumnAnnotation inverseJoinColumnAt(int index) {
		return null;
	}

	public int indexOfInverseJoinColumn(JoinColumnAnnotation joinColumn) {
		throw new UnsupportedOperationException();
	}

	public JoinColumnAnnotation addInverseJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveInverseJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeInverseJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public JoinColumnAnnotation initializeInverseJoinColumns() {
		throw new UnsupportedOperationException();
	}

}
