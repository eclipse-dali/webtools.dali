/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ClassRef extends XmlContextNode, JpaStructureNode
{
	/**
	 * Return true if the IClassRef matches the fullyQualfiedTypeName
	 */
	boolean isFor(String fullyQualifiedTypeName);
	
	/**
	 * Return whether this mapping file ref is represented by an entry in the
	 * persistence.xml (false) or if it is instead virtual
	 */
	boolean isVirtual();
	
	
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
	JavaPersistentType getJavaPersistentType();
	
	
	// **************** update **************************************
	
	/**
	 * Update the ClassRef context model object to match the XmlJavaClassRef 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(XmlJavaClassRef classRef);
	
	/**
	 * Update the ClassRef context model object to match the className. This is used
	 * for impliedClassRefs in the PersistenceUnit.
	 * see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(String className);
	
	
	// *************************************************************************
	
	/**
	 * Return whether the text representation of this persistence unit contains
	 * the given text offset
	 */
	boolean containsOffset(int textOffset);
	
}
