/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.jdtutility.Member;


//TODO how do we handle:
//  @Basic
//  private String foo, bar;

public interface JavaPersistentAttributeResource extends JavaPersistentResource
{
	String getName();
	
	Member getMember();
	
	boolean isForField();
	
	boolean isForProperty();
	
	boolean typeIsBasic();
		String TYPE_IS_BASIC_PROPERTY = "typeIsBasicProperty";
	
	/**
	 * Return true if the attribute type is a container:
	 * java.util.Collection
	 * java.util.Set
	 * java.util.List
	 * java.util.Map
	 * @return
	 */
	boolean typeIsContainer();
		String TYPE_IS_CONTAINER_PROPERTY = "typeIsContainerProperty";

	/**
	 * Returns the resolved qualfied type name for the attribute
	 */
	String getQualifiedTypeName();
		String QUALFIED_TYPE_NAME_PROPERTY = "qualfiedTypeNameProperty";
	
	/**
	 * Returns the resolved qualfied type name for the attribute
	 * if it as valid as a target entity type.  i.e. not an array.
	 * see typeIsContainer() to be used with this
	 */
	String getQualifiedReferenceEntityTypeName();
		String QUALFIED_REFERENCE_ENTITY_TYPE_NAME_PROPERTY = "qualfiedReferenceEntityTypeNameProperty";

	/**
	 * Returns the fully qualified type parameter for use as a target entity
	 */
	String getQualifiedReferenceEntityElementTypeName();
		String QUALFIED_REFERENCE_ENTITY_ELEMENT_TYPE_NAME_PROPERTY = "qualfiedReferenceEntityElementTypeNameProperty";
		
	/**
	 * Return true if this attribute has any mapping or non-mapping annotations
	 * (of course only persistence related annotations)
	 */
	boolean hasAnyAnnotation();
}
