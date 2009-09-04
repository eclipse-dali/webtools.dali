/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.platform.AbstractJpaFactory;

public class TestJpaFactory extends AbstractJpaFactory
{
	
	@Override
	public JavaEntity buildJavaEntity(JavaPersistentType parent) {
		return new TestJavaEntity(parent);
	}
	
	@Override
	public JavaBasicMapping buildJavaBasicMapping(JavaPersistentAttribute parent) {
		return new TestJavaBasicMapping(parent);
	}
	
	public JavaTestTypeMapping buildJavaTestTypeMapping(JavaPersistentType parent) {
		return new JavaTestTypeMapping(parent);
	}
	
	public JavaTestAttributeMapping buildJavaTestAttributeMapping(JavaPersistentAttribute parent) {
		return new JavaTestAttributeMapping(parent);
	}
}
