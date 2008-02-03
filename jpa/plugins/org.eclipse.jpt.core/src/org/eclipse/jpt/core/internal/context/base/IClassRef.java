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

import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;

public interface IClassRef extends IJpaContextNode
{
	/**
	 * Return true if the IClassRef matches the fullyQualfiedTypeName
	 */
	boolean isFor(String fullyQualifiedTypeName);
	
	
	// **************** class name *********************************************
	
	/**
	 * String constant associated with changes to the class name
	 */
	final static String CLASS_NAME_PROPERTY = "className";
	
	/**
	 * Return the class name of the class ref.
	 */
	String getClassName();
	
	/**
	 * Set the class name of the class ref.
	 */
	void setClassName(String className);
	
	
	// **************** java persistent type ***********************************
	
	/**
	 * String constant associated with changes to the java persistent type
	 */
	final static String JAVA_PERSISTENT_TYPE_PROPERTY = "javaPersistentType";
	
	/**
	 * Return the JavaPersistentType that corresponds to this IClassRef.
	 * This can be null.
	 * This is not settable by users of this API.
	 */
	IJavaPersistentType getJavaPersistentType();
	
	
	// **************** updating ***********************************************
	
	void initialize(XmlJavaClassRef classRef);
	
	void update(XmlJavaClassRef classRef);
	
	// *************************************************************************
	
	ITextRange validationTextRange(); 
	
}
