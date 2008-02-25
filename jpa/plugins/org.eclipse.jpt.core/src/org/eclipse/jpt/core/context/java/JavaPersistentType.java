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

import java.util.ListIterator;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

public interface JavaPersistentType extends PersistentType, JavaJpaContextNode
{
	JavaTypeMapping getMapping();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaPersistentAttribute> attributes();
	
	JavaPersistentAttribute attributeNamed(String attributeName);

	/**
	 * Resolve and return the attribute named <code>attributeName</code> if it
	 * is distinct and exists within the context of this type
	 */
	PersistentAttribute resolveAttribute(String attributeName);
	
	void initializeFromResource(JavaResourcePersistentType persistentTypeResource);

	void update(JavaResourcePersistentType persistentTypeResource);
	
	/**
	 * Return whether any attribute in this persistent type contains a mapping annotation
	 * @return
	 */
	boolean hasAnyAttributeMappingAnnotations();

}