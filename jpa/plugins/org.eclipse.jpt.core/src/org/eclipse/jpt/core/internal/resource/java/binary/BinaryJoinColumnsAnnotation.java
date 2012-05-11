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
import org.eclipse.jpt.core.resource.java.JoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinColumnAnnotation;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

/**
 * javax.persistence.JoinColumns
 */
public final class BinaryJoinColumnsAnnotation
	extends BinaryContainerAnnotation<NestableJoinColumnAnnotation>
	implements JoinColumnsAnnotation
{
	private final Vector<NestableJoinColumnAnnotation> joinColumns;


	public BinaryJoinColumnsAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.joinColumns = this.buildJoinColumns();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public Iterable<NestableJoinColumnAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<NestableJoinColumnAnnotation>(this.joinColumns);
	}

	public int getNestedAnnotationsSize() {
		return this.joinColumns.size();
	}

	private Vector<NestableJoinColumnAnnotation> buildJoinColumns() {
		Object[] jdtJoinColumns = this.getJdtMemberValues(JPA.JOIN_COLUMNS__VALUE);
		Vector<NestableJoinColumnAnnotation> result = new Vector<NestableJoinColumnAnnotation>(jdtJoinColumns.length);
		for (Object jdtJoinColumn : jdtJoinColumns) {
			result.add(new BinaryJoinColumnAnnotation(this, (IAnnotation) jdtJoinColumn));
		}
		return result;
	}

	@Override
	public void update() {
		super.update();
		this.updateJoinColumns();
	}

	// TODO
	private void updateJoinColumns() {
		throw new UnsupportedOperationException();
	}

}
