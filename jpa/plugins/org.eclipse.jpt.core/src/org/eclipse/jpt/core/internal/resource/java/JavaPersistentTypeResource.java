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

import java.util.Iterator;

public interface JavaPersistentTypeResource extends JavaPersistentResource
{	
	/**
	 * Return only the persistable nestedTypes
	 */
	Iterator<JavaPersistentTypeResource> nestedTypes();
		String NESTED_TYPES_COLLECTION = "nestedTypesCollection";

	/**
	 * Return only the persistable attributes, those that respond true to
	 * {@link JavaPersistentAttributeResource#isPersistable()}
	 * This returns fields and properties
	 * @return
	 */
	Iterator<JavaPersistentAttributeResource> attributes();
		String ATTRIBUTES_COLLECTION = "attributesCollection";
	
	/**
	 * Return only the persistable fields, those that respond true to
	 * {@link JavaPersistentAttributeResource#isPersistable()}
	 * This returns filters out all properties and only returns fields
	 * @return
	 */
	Iterator<JavaPersistentAttributeResource> fields();

	/**
	 * Return only the persistable fields, those that respond true to
	 * {@link JavaPersistentAttributeResource#isPersistable()}
	 * This returns filters out all fields and only returns properties
	 * @return
	 */
	Iterator<JavaPersistentAttributeResource> properties();
	
	JavaPersistentTypeResource javaPersistentTypeResource(String fullyQualifiedTypeName);

	/**
	 * Return the fully qualified type name
	 */
	String getQualifiedName();
		String QUALIFIED_NAME_PROPERTY = "qualifiedNameProperty";

	/**
	 * Return the fully unqualified type name
	 */
	String getName();
		String NAME_PROPERTY = "nameProperty";

	String getSuperClassQualifiedName();
		String SUPER_CLASS_QUALIFIED_NAME_PROPERTY = "superClassQualifiedNameProperty";
	
	AccessType getAccess();
		String ACCESS_PROPERTY = "accessProperty";
		
	boolean isAbstract();
		String ABSTRACT_PROPERTY = "abstractProperty";
	
	/**
	 * Return whether any attributes in this persistentType have
	 * JPA annotations on them.
	 */
	boolean hasAnyAttributeAnnotations();
}
