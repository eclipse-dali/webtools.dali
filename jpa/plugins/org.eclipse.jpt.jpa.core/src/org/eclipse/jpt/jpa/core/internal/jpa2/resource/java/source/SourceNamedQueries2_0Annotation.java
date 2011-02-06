/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source;

import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceNamedQueriesAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NestableNamedQueryAnnotation;

/**
 * <code>javax.persistence.NamedQueries</code>
 */
public final class SourceNamedQueries2_0Annotation
	extends SourceNamedQueriesAnnotation
{
	public SourceNamedQueries2_0Annotation(JavaResourceNode parent, Type type) {
		super(parent, type);
	}

	@Override
	protected NestableNamedQueryAnnotation buildNamedQuery(int index) {
		// pass the Java resource persistent member as the nested annotation's parent
		// since the nested annotation can be converted to stand-alone
		return SourceNamedQuery2_0Annotation.createNestedNamedQuery(this.parent, this.annotatedElement, index, this.daa);
	}
}
