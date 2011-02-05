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
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NamedNativeQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NestableNamedNativeQueryAnnotation;

/**
 * javax.persistence.NamedNativeQueries
 */
public final class BinaryNamedNativeQueriesAnnotation
	extends BinaryContainerAnnotation<NestableNamedNativeQueryAnnotation>
	implements NamedNativeQueriesAnnotation
{
	private final Vector<NestableNamedNativeQueryAnnotation> namedNativeQueries;


	public BinaryNamedNativeQueriesAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.namedNativeQueries = this.buildNamedNativeQueries();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public Iterable<NestableNamedNativeQueryAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<NestableNamedNativeQueryAnnotation>(this.namedNativeQueries);
	}

	public int getNestedAnnotationsSize() {
		return this.namedNativeQueries.size();
	}

	private Vector<NestableNamedNativeQueryAnnotation> buildNamedNativeQueries() {
		Object[] jdtQueries = this.getJdtMemberValues(JPA.NAMED_NATIVE_QUERIES__VALUE);
		Vector<NestableNamedNativeQueryAnnotation> result = new Vector<NestableNamedNativeQueryAnnotation>(jdtQueries.length);
		for (Object jdtQuery : jdtQueries) {
			result.add(new BinaryNamedNativeQueryAnnotation(this, (IAnnotation) jdtQuery));
		}
		return result;
	}

	@Override
	public void update() {
		super.update();
		this.updateNamedNativeQueries();
	}

	// TODO
	private void updateNamedNativeQueries() {
		throw new UnsupportedOperationException();
	}

}
