/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;

public interface IClassRef extends IJpaContextNode
{
	void update(XmlJavaClassRef classRef);
	
	/**
	 * Return the JavaPersistentType that corresponds to this IClassRef.  
	 * This can be null.
	 */
	IJavaPersistentType getJavaPersistentType();
		String JAVA_PERSISTENT_TYPE_PROPERTY = "javaPersistentTypeProperty";
	
	/**
	 * Return true if the IClassRef matches the fullyQualfiedTypeName
	 */
	boolean isFor(String fullyQualifiedTypeName);
	
}
