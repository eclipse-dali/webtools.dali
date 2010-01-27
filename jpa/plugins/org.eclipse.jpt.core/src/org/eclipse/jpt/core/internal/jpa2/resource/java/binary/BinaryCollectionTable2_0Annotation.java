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
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryBaseTableAnnotation;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryJoinColumnAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.CollectionTable2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.CollectionTable
 */
public final class BinaryCollectionTable2_0Annotation
	extends BinaryBaseTableAnnotation
	implements CollectionTable2_0Annotation
{
	private final Vector<JoinColumnAnnotation> joinColumns;


	public BinaryCollectionTable2_0Annotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
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


	// ********** BinaryBaseTableAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA2_0.COLLECTION_TABLE__NAME;
	}

	@Override
	protected String getSchemaElementName() {
		return JPA2_0.COLLECTION_TABLE__SCHEMA;
	}

	@Override
	protected String getCatalogElementName() {
		return JPA2_0.COLLECTION_TABLE__CATALOG;
	}

	@Override
	protected String getUniqueConstraintElementName() {
		return JPA2_0.COLLECTION_TABLE__UNIQUE_CONSTRAINTS;
	}


	// ********** CollectionTable2_0Annotation implementation **********

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
		Object[] jdtJoinColumns = this.getJdtMemberValues(JPA2_0.COLLECTION_TABLE__JOIN_COLUMNS);
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
}
