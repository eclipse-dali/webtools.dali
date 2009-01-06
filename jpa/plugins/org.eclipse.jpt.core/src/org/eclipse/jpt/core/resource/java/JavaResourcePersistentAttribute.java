/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
//TODO determine how we handle:
//  @Basic
//  private String foo, bar;
public interface JavaResourcePersistentAttribute
	extends JavaResourcePersistentMember
{
	String getName();
	
	boolean isForField();
	
	boolean isForProperty();
	
	boolean typeIsBasic();
		String TYPE_IS_BASIC_PROPERTY = "typeIsBasic"; //$NON-NLS-1$
	
	boolean isFinal();
		String FINAL_PROPERTY = "final"; //$NON-NLS-1$
	
	boolean isPublic();
		String PUBLIC_PROPERTY = "public"; //$NON-NLS-1$
	
	boolean typeIsSerializable();
		String TYPE_IS_SERIALIZABLE_PROPERTY = "typeIsSerializable"; //$NON-NLS-1$
		
	boolean typeIsDateOrCalendar();
		String TYPE_IS_DATE_OR_CALENDAR_PROPERTY = "typeIsDateOrCalendar"; //$NON-NLS-1$
	
	/**
	 * Return whether the attribute type is a container:
	 *     java.util.Collection
	 *     java.util.Set
	 *     java.util.List
	 *     java.util.Map
	 */
	boolean typeIsContainer();
		String TYPE_IS_CONTAINER_PROPERTY = "typeIsContainer"; //$NON-NLS-1$

	/**
	 * Return whether the attribute type is an interface
	 */
	boolean typeIsInterface();
		String TYPE_IS_INTERFACE_PROPERTY = "typeIsInterface"; //$NON-NLS-1$
	
	/**
	 * Return whether the attribute type is org.eclipse.persistence.indirection.ValueHolderInterface
	 */
	boolean typeIsValueHolder();
		String TYPE_IS_VALUE_HOLDER_PROPERTY = "typeIsValueHolder"; //$NON-NLS-1$
	
	/**
	 * Return the resolved qualified type name for the attribute.
	 */
	String getQualifiedTypeName();
		String QUALIFIED_TYPE_NAME_PROPERTY = "qualifiedTypeName"; //$NON-NLS-1$
	
	/**
	 * Return the resolved qualified type name for the attribute
	 * if it as valid as a target entity type; i.e. not an array.
	 * @see #typeIsContainer()
	 */
	String getQualifiedReferenceEntityTypeName();
		String QUALIFIED_REFERENCE_ENTITY_TYPE_NAME_PROPERTY = "qualifiedReferenceEntityTypeName"; //$NON-NLS-1$

	/**
	 * Return the fully qualified type parameter for use as a target entity.
	 */
	String getQualifiedReferenceEntityElementTypeName();
		String QUALIFIED_REFERENCE_ENTITY_ELEMENT_TYPE_NAME_PROPERTY = "qualifiedReferenceEntityElementTypeName"; //$NON-NLS-1$
		
	/**
	 * Return whether the attribute has any mapping or non-mapping annotations
	 * (of course only persistence-related annotations).
	 */
	boolean hasAnyAnnotations();

}
