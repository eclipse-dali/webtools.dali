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


public interface IPersistentType extends IJpaContextNode, IJpaStructureNode
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
	 * up the hierarchy(the JPA spec allows non-persistent types to be part of the hierarchy.)  
	 * Return null if this persistentType is the root persistent type. 
	 * Example:
	 * <pre>
	 * &#64;Entity
	 * public abstract class Model {}
	 * <a>
	 * public abstract class Animal extends Model {}
	 * <a>
	 * &#64;Entity
	 * public class Cat extends Animal {}
	 * </pre> 
	 * 
	 * If this is the Cat JavaPersistentType then parentPersistentType is the Model JavaPersistentType
	 * The parentPersistentType could be found in java or xml.
	 */
	IPersistentType parentPersistentType();

	/**
	 * Return a read-only iterator of the contained IPersistentAttributes
	 */
	<T extends IPersistentAttribute> ListIterator<T> attributes();
	
	/**
	 * Return the size of IPersistentAttributes list
	 * @return
	 */
	int attributesSize();
		String SPECIFIED_ATTRIBUTES_LIST = "specifiedAttributesList";
	
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

	/**
	 * Resolve and return the attribute named <code>attributeName</code> if it
	 * is distinct and exists within the context of this type
	 */
	IPersistentAttribute resolveAttribute(String attributeName);

	Iterator<IPersistentType> inheritanceHierarchy();
	
	

}
