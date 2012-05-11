/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestablePrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

/**
 * javax.persistence.PrimaryKeyJoinColumns
 */
public final class BinaryPrimaryKeyJoinColumnsAnnotation
	extends BinaryContainerAnnotation<NestablePrimaryKeyJoinColumnAnnotation>
	implements PrimaryKeyJoinColumnsAnnotation
{
	private final Vector<NestablePrimaryKeyJoinColumnAnnotation> pkJoinColumns;


	public BinaryPrimaryKeyJoinColumnsAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.pkJoinColumns = this.buildPkJoinColumns();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public Iterable<NestablePrimaryKeyJoinColumnAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<NestablePrimaryKeyJoinColumnAnnotation>(this.pkJoinColumns);
	}

	public int getNestedAnnotationsSize() {
		return this.pkJoinColumns.size();
	}

	private Vector<NestablePrimaryKeyJoinColumnAnnotation> buildPkJoinColumns() {
		Object[] jdtJoinColumns = this.getJdtMemberValues(JPA.PRIMARY_KEY_JOIN_COLUMNS__VALUE);
		Vector<NestablePrimaryKeyJoinColumnAnnotation> result = new Vector<NestablePrimaryKeyJoinColumnAnnotation>(jdtJoinColumns.length);
		for (Object jdtJoinColumn : jdtJoinColumns) {
			result.add(new BinaryPrimaryKeyJoinColumnAnnotation(this, (IAnnotation) jdtJoinColumn));
		}
		return result;
	}

	@Override
	public void update() {
		super.update();
		this.updatePkJoinColumns();
	}

	// TODO
	private void updatePkJoinColumns() {
		throw new UnsupportedOperationException();
	}

}

