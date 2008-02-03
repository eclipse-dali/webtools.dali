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

import java.util.Iterator;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;


public interface IJavaAttributeMapping extends IAttributeMapping, IJavaJpaContextNode
{
	IJavaPersistentAttribute persistentAttribute();
	
	void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource);

	void update(JavaPersistentAttributeResource persistentAttributeResource);
	
	String annotationName();
	
	/**
	 * Return all fully qualfied annotation names that are supported with this mapping type.
	 * This includes all possible annotations, not just the ones that currently exist on the attribute.
	 */
	Iterator<String> correspondingAnnotationNames();

}
