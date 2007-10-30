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

import org.eclipse.jpt.core.internal.IMappingKeys;


public class JavaNullTypeMapping extends JavaTypeMapping
{
	public JavaNullTypeMapping(IJavaPersistentType parent) {
		super(parent);
	}

	public String annotationName() {
		return null;
	}
	
	public String getKey() {
		return IMappingKeys.NULL_TYPE_MAPPING_KEY;
	}
	
	public boolean isMapped() {
		return false;
	}
}
