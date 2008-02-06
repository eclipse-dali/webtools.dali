/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;

public interface IJavaPersistentAttribute extends IPersistentAttribute, IJavaJpaContextNode
{
	
	IJavaAttributeMapping getMapping();
	
	IJavaAttributeMapping getSpecifiedMapping();
	
	IJavaTypeMapping typeMapping();
	
	IJavaPersistentType persistentType();
	
	void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource);

	void update(JavaPersistentAttributeResource persistentAttributeResource);
	
	JavaPersistentAttributeResource getPersistentAttributeResource();
	
	/**
	 * Return whether the attribute contains the given offset into the text file.
	 */
	boolean contains(int offset, CompilationUnit astRoot);

}