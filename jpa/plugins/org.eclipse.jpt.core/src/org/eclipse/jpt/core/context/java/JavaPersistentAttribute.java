/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaPersistentAttribute extends PersistentAttribute, JavaJpaContextNode
{
	
	JavaAttributeMapping getMapping();
	
	JavaAttributeMapping getSpecifiedMapping();
	
	JavaTypeMapping getTypeMapping();
	
	JavaPersistentType getPersistentType();
	
	void initializeFromResource(JavaResourcePersistentAttribute resourcePersistentAttribute);

	void update(JavaResourcePersistentAttribute resourcePersistentAttribute);
	
	JavaResourcePersistentAttribute getResourcePersistentAttribute();
	
	/**
	 * Return whether the attribute contains the given offset into the text file.
	 */
	boolean contains(int offset, CompilationUnit astRoot);

}