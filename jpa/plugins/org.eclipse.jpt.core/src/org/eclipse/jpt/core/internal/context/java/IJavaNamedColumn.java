/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentResource;


public interface IJavaNamedColumn extends INamedColumn, IJavaJpaContextNode
{
	void initializeFromResource(JavaPersistentResource persistentResource);
	
	void update(JavaPersistentResource persistentResource);
}