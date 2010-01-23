/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.binary;

import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryContainerAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyJoinColumns2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.NestableMapKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

/**
 * javax.persistence.MapKeyJoinColumns
 */
public final class BinaryMapKeyJoinColumns2_0Annotation
	extends BinaryContainerAnnotation<NestableMapKeyJoinColumnAnnotation>
	implements MapKeyJoinColumns2_0Annotation
{
	private final Vector<NestableMapKeyJoinColumnAnnotation> mapKeyJoinColumns;


	public BinaryMapKeyJoinColumns2_0Annotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.mapKeyJoinColumns = this.buildMapKeyJoinColumns();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public Iterable<NestableMapKeyJoinColumnAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<NestableMapKeyJoinColumnAnnotation>(this.mapKeyJoinColumns);
	}

	public int getNestedAnnotationsSize() {
		return this.mapKeyJoinColumns.size();
	}

	private Vector<NestableMapKeyJoinColumnAnnotation> buildMapKeyJoinColumns() {
		Object[] jdtMapKeyJoinColumns = this.getJdtMemberValues(JPA2_0.MAP_KEY_JOIN_COLUMNS__VALUE);
		Vector<NestableMapKeyJoinColumnAnnotation> result = new Vector<NestableMapKeyJoinColumnAnnotation>(jdtMapKeyJoinColumns.length);
		for (Object jdtMapKeyJoinColumn : jdtMapKeyJoinColumns) {
			result.add(new BinaryMapKeyJoinColumn2_0Annotation(this, (IAnnotation) jdtMapKeyJoinColumn));
		}
		return result;
	}

	@Override
	public void update() {
		super.update();
		this.updateMapKeyJoinColumns();
	}

	// TODO
	private void updateMapKeyJoinColumns() {
		throw new UnsupportedOperationException();
	}

}
