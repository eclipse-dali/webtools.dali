/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.JoinTable
 */
public final class BinaryJoinTableAnnotation
	extends BinaryBaseTableAnnotation
	implements JoinTableAnnotation
{
	private final Vector<JoinColumnAnnotation> joinColumns;
	private final Vector<JoinColumnAnnotation> inverseJoinColumns;


	public BinaryJoinTableAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.joinColumns = this.buildJoinColumns();
		this.inverseJoinColumns = this.buildInverseJoinColumns();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.updateJoinColumns();
		this.updateInverseJoinColumns();
	}


	// ********** AbstractBaseTableAnnotation implementation **********

	@Override
	String getNameElementName() {
		return JPA.JOIN_TABLE__NAME;
	}

	@Override
	String getSchemaElementName() {
		return JPA.JOIN_TABLE__SCHEMA;
	}

	@Override
	String getCatalogElementName() {
		return JPA.JOIN_TABLE__CATALOG;
	}

	@Override
	String getUniqueConstraintElementName() {
		return JPA.JOIN_TABLE__UNIQUE_CONSTRAINTS;
	}


	// ********** JoinTableAnnotation implementation **********

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
		Object[] jdtJoinColumns = this.getJdtMemberValues(JPA.JOIN_TABLE__JOIN_COLUMNS);
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

	public JoinColumnAnnotation initializeJoinColumns() {
		throw new UnsupportedOperationException();
	}

	// ***** inverse join columns
	public ListIterator<JoinColumnAnnotation> inverseJoinColumns() {
		return new CloneListIterator<JoinColumnAnnotation>(this.inverseJoinColumns);
	}

	public int inverseJoinColumnsSize() {
		return this.inverseJoinColumns.size();
	}

	public JoinColumnAnnotation inverseJoinColumnAt(int index) {
		return this.inverseJoinColumns.get(index);
	}

	public int indexOfInverseJoinColumn(JoinColumnAnnotation joinColumn) {
		return this.inverseJoinColumns.indexOf(joinColumn);
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

	private Vector<JoinColumnAnnotation> buildInverseJoinColumns() {
		Object[] jdtJoinColumns = this.getJdtMemberValues(JPA.JOIN_TABLE__INVERSE_JOIN_COLUMNS);
		Vector<JoinColumnAnnotation> result = new Vector<JoinColumnAnnotation>(jdtJoinColumns.length);
		for (Object jdtJoinColumn : jdtJoinColumns) {
			result.add(new BinaryJoinColumnAnnotation(this, (IAnnotation) jdtJoinColumn));
		}
		return result;
	}

	// TODO
	private void updateInverseJoinColumns() {
		throw new UnsupportedOperationException();
	}

	public JoinColumnAnnotation initializeInverseJoinColumns() {
		throw new UnsupportedOperationException();
	}

}
