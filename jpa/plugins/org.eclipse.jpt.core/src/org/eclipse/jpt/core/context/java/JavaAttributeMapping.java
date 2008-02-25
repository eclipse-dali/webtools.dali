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

import java.util.Iterator;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;


public interface JavaAttributeMapping extends AttributeMapping, JavaJpaContextNode
{
	JavaPersistentAttribute persistentAttribute();
	
	void initializeFromResource(JavaResourcePersistentAttribute persistentAttributeResource);

	void update(JavaResourcePersistentAttribute persistentAttributeResource);
	
	String annotationName();
	
	/**
	 * Return all fully qualfied annotation names that are supported with this mapping type.
	 * This includes all possible annotations, not just the ones that currently exist on the attribute.
	 */
	Iterator<String> correspondingAnnotationNames();

}
