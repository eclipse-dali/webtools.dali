/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryNamedQueriesAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NestableNamedQueryAnnotation;

/**
 * javax.persistence.NamedQueries
 */
public final class BinaryNamedQueries1_0Annotation
	extends BinaryNamedQueriesAnnotation
{
	public BinaryNamedQueries1_0Annotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	@Override
	protected NestableNamedQueryAnnotation buildNamedQuery(Object jdtQuery) {
		return new BinaryNamedQuery1_0Annotation(this, (IAnnotation) jdtQuery);
	}

}
