/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;

public interface JavaPersistentAttribute extends PersistentAttribute, JavaJpaContextNode
{
	
	JavaAttributeMapping getMapping();
	
	JavaAttributeMapping getSpecifiedMapping();
	
	JavaTypeMapping typeMapping();
	
	JavaPersistentType persistentType();
	
	void initializeFromResource(JavaResourcePersistentAttribute persistentAttributeResource);

	void update(JavaResourcePersistentAttribute persistentAttributeResource);
	
	JavaResourcePersistentAttribute getPersistentAttributeResource();
	
	/**
	 * Return whether the attribute contains the given offset into the text file.
	 */
	boolean contains(int offset, CompilationUnit astRoot);

}