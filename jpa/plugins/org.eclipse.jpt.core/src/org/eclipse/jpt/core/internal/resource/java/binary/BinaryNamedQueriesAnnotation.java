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
import org.eclipse.jpt.core.resource.java.NamedQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NestableNamedQueryAnnotation;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.NamedQueries
 */
public final class BinaryNamedQueriesAnnotation
	extends BinaryContainerAnnotation<NestableNamedQueryAnnotation>
	implements NamedQueriesAnnotation
{
	private final Vector<NestableNamedQueryAnnotation> namedQueries;


	public BinaryNamedQueriesAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.namedQueries = this.buildNamedQueries();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public ListIterator<NestableNamedQueryAnnotation> nestedAnnotations() {
		return new CloneListIterator<NestableNamedQueryAnnotation>(this.namedQueries);
	}

	public int nestedAnnotationsSize() {
		return this.namedQueries.size();
	}

	private Vector<NestableNamedQueryAnnotation> buildNamedQueries() {
		Object[] jdtQueries = this.getJdtMemberValues(JPA.NAMED_QUERIES__VALUE);
		Vector<NestableNamedQueryAnnotation> result = new Vector<NestableNamedQueryAnnotation>(jdtQueries.length);
		for (Object jdtQuery : jdtQueries) {
			result.add(new BinaryNamedQueryAnnotation(this, (IAnnotation) jdtQuery));
		}
		return result;
	}

	@Override
	public void update() {
		super.update();
		this.updateNamedQueries();
	}

	// TODO
	private void updateNamedQueries() {
		throw new UnsupportedOperationException();
	}

}
