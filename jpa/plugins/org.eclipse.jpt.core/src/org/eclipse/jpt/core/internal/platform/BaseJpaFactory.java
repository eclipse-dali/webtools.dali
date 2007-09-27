/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public abstract class BaseJpaFactory implements IJpaFactory
{
	public IJavaEntity createJavaEntity(Type type) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaEntity(type);
	}
	
	public IJavaEmbeddable createJavaEmbeddable(Type type) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaEmbeddable(type);
	}
	
	public IJavaMappedSuperclass createJavaMappedSuperclass(Type type) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaMappedSuperclass(type);
	}
		
	public IJavaBasic createJavaBasic(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaBasic(attribute);
	}
	
	public IJavaEmbedded createJavaEmbedded(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaEmbedded(attribute);
	}
	
	public IJavaEmbeddedId createJavaEmbeddedId(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaEmbeddedId(attribute);
	}
	
	public IJavaId createJavaId(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaId(attribute);
	}
	
	public IJavaManyToMany createJavaManyToMany(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaManyToMany(attribute);
	}
	
	public IJavaManyToOne createJavaManyToOne(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaManyToOne(attribute);
	}
	
	public IJavaOneToMany createJavaOneToMany(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaOneToMany(attribute);
	}
	
	public IJavaOneToOne createJavaOneToOne(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaOneToOne(attribute);
	}
	
	public IJavaTransient createJavaTransient(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaTransient(attribute);
	}
	
	public IJavaVersion createJavaVersion(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaVersion(attribute);
	}
}
