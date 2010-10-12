/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableNamedQueryAnnotation;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * javax.persistence.NamedQueries
 */
public final class SourceNamedQueries1_0Annotation
	extends SourceNamedQueriesAnnotation
{
	public SourceNamedQueries1_0Annotation(JavaResourceNode parent, Type type) {
		super(parent, type);
	}

	@Override
	protected NestableNamedQueryAnnotation buildNamedQuery(int index) {
		return SourceNamedQueryAnnotation.createNestedNamedQuery(this, this.annotatedElement, index, this.daa);
	}
}
