/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaQuery;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;

/**
 * Java named query
 */
public class GenericJavaNamedQuery
	extends AbstractJavaQuery<NamedQueryAnnotation>
	implements JavaNamedQuery
{
	public GenericJavaNamedQuery(JavaJpaContextNode parent, NamedQueryAnnotation queryAnnotation) {
		super(parent, queryAnnotation);
	}
}
