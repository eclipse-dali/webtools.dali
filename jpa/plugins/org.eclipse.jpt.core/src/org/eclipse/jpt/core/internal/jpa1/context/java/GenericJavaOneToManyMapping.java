/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaRelationshipReference;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOneToManyMapping;
import org.eclipse.jpt.core.jpa2.resource.java.OneToMany2_0Annotation;


public class GenericJavaOneToManyMapping
	extends AbstractJavaOneToManyMapping<OneToMany2_0Annotation>
{
	
	public GenericJavaOneToManyMapping(JavaPersistentAttribute parent) {
		super(parent);
	}	
	
	@Override
	protected JavaRelationshipReference buildRelationshipReference() {
		return new GenericJavaOneToManyRelationshipReference(this);
	}

}
