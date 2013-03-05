/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.extension.resource;

import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaFactory;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;

public class TestJpaFactory
	extends AbstractJpaFactory
{
	@Override
	public JavaEntity buildJavaEntity(JavaPersistentType parent, EntityAnnotation entityAnnotation) {
		return new TestJavaEntity(parent, entityAnnotation);
	}
	
	@Override
	public JavaBasicMapping buildJavaBasicMapping(JavaModifiablePersistentAttribute parent) {
		return new TestJavaBasicMapping(parent);
	}
	
	public JavaTestTypeMapping buildJavaTestTypeMapping(JavaPersistentType parent) {
		return new JavaTestTypeMapping(parent);
	}
	
	public JavaTestAttributeMapping buildJavaTestAttributeMapping(JavaModifiablePersistentAttribute parent) {
		return new JavaTestAttributeMapping(parent);
	}
}
