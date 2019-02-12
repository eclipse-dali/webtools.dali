/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.extension.resource;

import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
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
	public JavaBasicMapping buildJavaBasicMapping(JavaSpecifiedPersistentAttribute parent) {
		return new TestJavaBasicMapping(parent);
	}
	
	public JavaTestTypeMapping buildJavaTestTypeMapping(JavaPersistentType parent) {
		return new JavaTestTypeMapping(parent);
	}
	
	public JavaTestAttributeMapping buildJavaTestAttributeMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaTestAttributeMapping(parent);
	}
}
