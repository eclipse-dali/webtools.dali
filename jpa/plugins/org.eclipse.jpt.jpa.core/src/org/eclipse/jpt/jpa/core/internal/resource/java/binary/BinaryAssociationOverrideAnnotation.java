/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NestableAssociationOverrideAnnotation;

/**
 * javax.persistence.AssociationOverride
 */
public abstract class BinaryAssociationOverrideAnnotation
	extends BinaryOverrideAnnotation
	implements NestableAssociationOverrideAnnotation
{
	private final Vector<JoinColumnAnnotation> joinColumns;


	protected BinaryAssociationOverrideAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.joinColumns = this.buildJoinColumns();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.updateJoinColumns();
	}


	// ********** BinaryOverrideAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.ASSOCIATION_OVERRIDE__NAME;
	}

	
	// ********** AssociationOverrideAnnotation implementation **********

	// ***** join columns
	public ListIterator<JoinColumnAnnotation> joinColumns() {
		return new CloneListIterator<JoinColumnAnnotation>(this.joinColumns);
	}

	public int joinColumnsSize() {
		return this.joinColumns.size();
	}

	public JoinColumnAnnotation joinColumnAt(int index) {
		return this.joinColumns.get(index);
	}

	public int indexOfJoinColumn(JoinColumnAnnotation joinColumn) {
		return this.joinColumns.indexOf(joinColumn);
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

	private Vector<JoinColumnAnnotation> buildJoinColumns() {
		Object[] jdtJoinColumns = this.getJdtMemberValues(JPA.ASSOCIATION_OVERRIDE__JOIN_COLUMNS);
		Vector<JoinColumnAnnotation> result = new Vector<JoinColumnAnnotation>(jdtJoinColumns.length);
		for (Object jdtJoinColumn : jdtJoinColumns) {
			result.add(new BinaryJoinColumnAnnotation(this, (IAnnotation) jdtJoinColumn));
		}
		return result;
	}

	// TODO
	private void updateJoinColumns() {
		throw new UnsupportedOperationException();
	}

}
