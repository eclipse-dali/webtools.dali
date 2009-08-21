/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.utility.internal.StringTools;

public abstract class AbstractJavaTypeMappingProvider
	implements JavaTypeMappingProvider
{
	protected AbstractJavaTypeMappingProvider() {
		super();
	}
	
	
	/**
	 * Default implementation.  Override if the mapping provider needs to do more analysis.
	 */
	public boolean test(JavaPersistentType persistentType) {
		return persistentType.getResourcePersistentType().getAnnotation(getAnnotationName()) 
			!= null;
	}
	
	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getAnnotationName());
	}
}
