/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;



public class GenericJavaNamedQuery extends AbstractJavaQuery implements JavaNamedQuery
{
	
	public GenericJavaNamedQuery(JavaJpaContextNode parent) {
		super(parent);
	}
	
	@Override
	protected NamedQueryAnnotation getResourceQuery() {
		return (NamedQueryAnnotation) super.getResourceQuery();
	}

	public void initialize(NamedQueryAnnotation resourceQuery) {
		super.initialize(resourceQuery);
	}
	
	public void update(NamedQueryAnnotation resourceQuery) {
		super.update(resourceQuery);
	}
}
