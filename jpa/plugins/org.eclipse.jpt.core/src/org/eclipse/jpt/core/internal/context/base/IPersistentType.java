/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.Iterator;
import java.util.ListIterator;


public interface IPersistentType extends IJpaContextNode
{
	String getName();
		String NAME_PROPERTY = "nameProperty";
	
	AccessType access();
		String ACCESS_PROPERTY = "accessProperty";
		
	ITypeMapping getMapping();
	String mappingKey();
	void setMappingKey(String key);
		String MAPPING_PROPERTY = "mappingProperty";

	boolean isMapped();
	
	/**
	 * Return the parent IPersistentType from the inheritance hierarchy.
	 * If the java inheritance parent is not a IPersistentType then continue
	 * up the hierarchy.  Return null if this persistentType is the root
	 * persistent type. 
	 */
	IPersistentType parentPersistentType();

	/**
	 * Return a read-only iterator of the contained IPersistentAttributes
	 */
	<T extends IPersistentAttribute> ListIterator<T> attributes();
	int attributesSize();
		String SPECIFIED_ATTRIBUTES_LIST = "specifiedAttributesList";
		String VIRTUAL_ATTRIBUTES_LIST = "virtualAttributesList";
	
	Iterator<String> attributeNames();

	/**
	 * Return a read-only iterator of the all the IPersistentAttributes
	 * in the hierarchy
	 */
	Iterator<IPersistentAttribute> allAttributes();

	Iterator<String> allAttributeNames();

	/**
	 * Return the attribute named <code>attributeName</code> if
	 * it exists locally on this type
	 */
	IPersistentAttribute attributeNamed(String attributeName);

//	/**
//	 * Resolve and return the attribute named <code>attributeName</code> if it
//	 * is distinct and exists within the context of this type
//	 */
//	IPersistentAttribute resolveAttribute(String attributeName);

	Iterator<IPersistentType> inheritanceHierarchy();

}
