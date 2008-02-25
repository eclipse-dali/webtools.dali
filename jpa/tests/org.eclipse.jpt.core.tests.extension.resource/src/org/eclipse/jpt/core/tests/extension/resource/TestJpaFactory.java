/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jpt.core.internal.content.java.mappings.IJavaBasic;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaEntity;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.platform.BaseJpaFactory;

public class TestJpaFactory extends BaseJpaFactory
{
	@Override
	public JavaEntity createJavaEntity(Type type) {
		return new TestJavaEntity(type);
	}
	
	@Override
	public IJavaBasic createJavaBasic(Attribute attribute) {
		return new TestJavaBasic(attribute);
	}
	
	public TestTypeMapping createTestTypeMapping(Type type) {
		return new TestTypeMapping(type);
	}
	
	public TestAttributeMapping createTestAttributeMapping(Attribute attribute) {
		return new TestAttributeMapping(attribute);
	}
}
