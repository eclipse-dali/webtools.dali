/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NestableNamedQueryAnnotation;

/**
 * <code>javax.persistence.NamedQueries</code>
 */
public final class SourceNamedQueries1_0Annotation
	extends SourceNamedQueriesAnnotation
{
	public SourceNamedQueries1_0Annotation(JavaResourceNode parent, Type type) {
		super(parent, type);
	}

	@Override
	protected NestableNamedQueryAnnotation buildNamedQuery(int index) {
		// pass the Java resource persistent member as the nested annotation's parent
		// since the nested annotation can be converted to stand-alone
		return SourceNamedQueryAnnotation.createNestedNamedQuery(this.parent, this.annotatedElement, index, this.daa);
	}
}
