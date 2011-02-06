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
import org.eclipse.jpt.jpa.core.resource.java.NestableSecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;

/**
 * javax.persistence.SecondaryTable
 */
public final class BinarySecondaryTableAnnotation
	extends BinaryBaseTableAnnotation
	implements NestableSecondaryTableAnnotation
{
	private final Vector<PrimaryKeyJoinColumnAnnotation> pkJoinColumns;

	public BinarySecondaryTableAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.pkJoinColumns = this.buildPkJoinColumns();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.updatePkJoinColumns();
	}


	// ********** BinaryBaseTableAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.SECONDARY_TABLE__NAME;
	}

	@Override
	protected String getSchemaElementName() {
		return JPA.SECONDARY_TABLE__SCHEMA;
	}

	@Override
	protected String getCatalogElementName() {
		return JPA.SECONDARY_TABLE__CATALOG;
	}

	@Override
	protected String getUniqueConstraintElementName() {
		return JPA.SECONDARY_TABLE__UNIQUE_CONSTRAINTS;
	}


	// ************* SecondaryTableAnnotation implementation *******************

	// ***** pk join columns
	public ListIterator<PrimaryKeyJoinColumnAnnotation> pkJoinColumns() {
		return new CloneListIterator<PrimaryKeyJoinColumnAnnotation>(this.pkJoinColumns);
	}

	public int pkJoinColumnsSize() {
		return this.pkJoinColumns.size();
	}

	public PrimaryKeyJoinColumnAnnotation pkJoinColumnAt(int index) {
		return this.pkJoinColumns.get(index);
	}

	public int indexOfPkJoinColumn(PrimaryKeyJoinColumnAnnotation pkJoinColumn) {
		return this.pkJoinColumns.indexOf(pkJoinColumn);
	}

	public PrimaryKeyJoinColumnAnnotation addPkJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	public void movePkJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removePkJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}

	private Vector<PrimaryKeyJoinColumnAnnotation> buildPkJoinColumns() {
		Object[] jdtJoinColumns = this.getJdtMemberValues(JPA.SECONDARY_TABLE__PK_JOIN_COLUMNS);
		Vector<PrimaryKeyJoinColumnAnnotation> result = new Vector<PrimaryKeyJoinColumnAnnotation>(jdtJoinColumns.length);
		for (Object jdtJoinColumn : jdtJoinColumns) {
			result.add(new BinaryPrimaryKeyJoinColumnAnnotation(this, (IAnnotation) jdtJoinColumn));
		}
		return result;
	}

	// TODO
	private void updatePkJoinColumns() {
		throw new UnsupportedOperationException();
	}

}
