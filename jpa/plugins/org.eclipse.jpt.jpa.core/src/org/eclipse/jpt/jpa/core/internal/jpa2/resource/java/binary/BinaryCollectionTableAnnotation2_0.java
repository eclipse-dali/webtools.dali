/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryBaseTableAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.CollectionTableAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;

/**
 * <code>javax.persistence.CollectionTable</code>
 */
public final class BinaryCollectionTableAnnotation2_0
	extends BinaryBaseTableAnnotation
	implements CollectionTableAnnotation2_0
{
	private final Vector<JoinColumnAnnotation> joinColumns;


	public BinaryCollectionTableAnnotation2_0(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
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
	public ListIterable<JoinColumnAnnotation> getJoinColumns() {
		return IterableTools.cloneLive(this.joinColumns);
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
}
