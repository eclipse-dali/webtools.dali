/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jpt.core.internal.resource.java.source.SourceNamedQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableNamedQueryAnnotation;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 *  SourceNamedQueries2_0Annotation
 */
public class SourceNamedQueries2_0Annotation extends SourceNamedQueriesAnnotation
{
	public SourceNamedQueries2_0Annotation(JavaResourceNode parent, Type type) {
		super(parent, type);
	}

	protected NestableNamedQueryAnnotation buildNamedQuery(int index) {
		return SourceNamedQuery2_0Annotation.createNestedNamedQuery(this, member, index, this.daa);
	}
}
