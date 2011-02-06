/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java;

import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

/**
 * <code>javax.persistence.JoinTable</code>
 */
public class NullJoinTableAnnotation
	extends NullBaseTableAnnotation<JoinTableAnnotation>
	implements JoinTableAnnotation
{	
	public NullJoinTableAnnotation(JavaResourceNode parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
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
		// the JoinTable annotation is missing, add both it and a join column at the same time
		return this.addAnnotation().addJoinColumn(index);
	}
	
	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeJoinColumn(int index) {
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
		// the JoinTable annotation is missing, add both it and a join column at the same time
		return this.addAnnotation().addInverseJoinColumn(index);
	}
	
	public void moveInverseJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeInverseJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}
}
