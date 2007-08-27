/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.jpt.core.internal.content.java.mappings.IJavaBasic;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaEmbeddable;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaEmbedded;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaEmbeddedId;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaEntity;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaId;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaManyToMany;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaManyToOne;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaMappedSuperclass;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaOneToMany;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaOneToOne;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaTransient;
import org.eclipse.jpt.core.internal.content.java.mappings.IJavaVersion;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.platform.BaseJpaFactory;

/**
 * Use IJpaFactory to create any IJavaTypeMapping or IJavaAttributeMappings.  This is necessary
 * so that platforms can extend the java model with their own annotations. 
 * IJavaTypeMappingProvider and IJavaAttributeMappingProvider use this factory.
 * See IJpaPlatform.javaTypeMappingProviders() and IJpaPlatform.javaAttributeMappingProviders()
 * for creating new mappings types.
 * @see BaseJpaFactory
 */
public interface IJpaFactory
{
	IJavaEntity createJavaEntity(Type type);
	
	IJavaEmbeddable createJavaEmbeddable(Type type);
	
	IJavaMappedSuperclass createJavaMappedSuperclass(Type type);
		
	IJavaBasic createJavaBasic(Attribute attribute);
	
	IJavaEmbedded createJavaEmbedded(Attribute attribute);
	
	IJavaEmbeddedId createJavaEmbeddedId(Attribute attribute);
	
	IJavaId createJavaId(Attribute attribute);
	
	IJavaManyToMany createJavaManyToMany(Attribute attribute);
	
	IJavaManyToOne createJavaManyToOne(Attribute attribute);
	
	IJavaOneToMany createJavaOneToMany(Attribute attribute);
	
	IJavaOneToOne createJavaOneToOne(Attribute attribute);
	
	IJavaTransient createJavaTransient(Attribute attribute);
	
	IJavaVersion createJavaVersion(Attribute attribute);
}
