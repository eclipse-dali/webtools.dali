/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;

public interface JavaAttributeOverride extends AttributeOverride, JavaJpaContextNode, JavaColumn.Owner
{
	JavaColumn getColumn();
	
	void initializeFromResource(AttributeOverrideAnnotation attributeOverride);
	
	void update(AttributeOverrideAnnotation attributeOverride);
	
}
