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

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;

public interface IJavaPersistentType extends IPersistentType, IJavaJpaContextNode
{
	IJavaTypeMapping getMapping();
	
	@SuppressWarnings("unchecked")
	ListIterator<IJavaPersistentAttribute> attributes();
	
	IJavaPersistentAttribute attributeNamed(String attributeName);
	
	void initializeFromResource(JavaPersistentTypeResource persistentTypeResource);

	void update(JavaPersistentTypeResource persistentTypeResource);
	
	/**
	 * Return whether any attribute in this persistent type contains a mapping annotation
	 * @return
	 */
	boolean hasAnyAttributeMappingAnnotations();

}