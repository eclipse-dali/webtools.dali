/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;


public class NullJoinTable extends NullAbstractTable implements JoinTable
{	
	protected NullJoinTable(JavaResource parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return JoinTable.ANNOTATION_NAME;
	}

	public JoinColumn addInverseJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public JoinColumn addJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public int indexOfInverseJoinColumn(JoinColumn joinColumn) {
		throw new UnsupportedOperationException();
	}

	public int indexOfJoinColumn(JoinColumn joinColumn) {
		throw new UnsupportedOperationException();
	}

	public JoinColumn inverseJoinColumnAt(int index) {
		return null;
	}

	public ListIterator<JoinColumn> inverseJoinColumns() {
		return EmptyListIterator.instance();
	}

	public int inverseJoinColumnsSize() {
		return 0;
	}

	public JoinColumn joinColumnAt(int index) {
		return null;
	}

	public ListIterator<JoinColumn> joinColumns() {
		return EmptyListIterator.instance();
	}

	public int joinColumnsSize() {
		return 0;
	}

	public void moveInverseJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeInverseJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public void removeJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}
}
