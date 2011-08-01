/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

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


	// ********** BinaryBaseTableAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.JOIN_TABLE__NAME;
	}

	@Override
	protected String getSchemaElementName() {
		return JPA.JOIN_TABLE__SCHEMA;
	}

	@Override
	protected String getCatalogElementName() {
		return JPA.JOIN_TABLE__CATALOG;
	}

	@Override
	protected String getUniqueConstraintElementName() {
		return JPA.JOIN_TABLE__UNIQUE_CONSTRAINTS;
	}


	// ********** JoinTableAnnotation implementation **********

	// ***** join columns
	public ListIterable<JoinColumnAnnotation> getJoinColumns() {
		return new LiveCloneListIterable<JoinColumnAnnotation>(this.joinColumns);
	}

	public int getJoinColumnsSize() {
		return this.joinColumns.size();
	}

	public JoinColumnAnnotation joinColumnAt(int index) {
		return this.joinColumns.get(index);
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


	// ***** inverse join columns
	public ListIterable<JoinColumnAnnotation> getInverseJoinColumns() {
		return new LiveCloneListIterable<JoinColumnAnnotation>(this.inverseJoinColumns);
	}

	public int getInverseJoinColumnsSize() {
		return this.inverseJoinColumns.size();
	}

	public JoinColumnAnnotation inverseJoinColumnAt(int index) {
		return this.inverseJoinColumns.get(index);
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

}
