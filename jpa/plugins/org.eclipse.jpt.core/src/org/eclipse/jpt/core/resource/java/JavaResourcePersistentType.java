/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.jdtutility.Member;

public interface JavaResourcePersistentType extends JavaResourcePersistentMember
{	
	/**
	 * Return only the persistable nestedTypes
	 */
	Iterator<JavaResourcePersistentType> nestedTypes();
		String NESTED_TYPES_COLLECTION = "nestedTypesCollection";

	/**
	 * Return only the persistable attributes, those that respond true to
	 * {@link JavaResourcePersistentAttribute#isPersistable()}
	 * This returns fields and properties
	 * @return
	 */
	Iterator<JavaResourcePersistentAttribute> attributes();
		String ATTRIBUTES_COLLECTION = "attributesCollection";
	
	/**
	 * Return only the persistable fields, those that respond true to
	 * {@link JavaResourcePersistentAttribute#isPersistable()}
	 * This returns filters out all properties and only returns fields
	 * @return
	 */
	Iterator<JavaResourcePersistentAttribute> fields();

	/**
	 * Return only the persistable fields, those that respond true to
	 * {@link JavaResourcePersistentAttribute#isPersistable()}
	 * This returns filters out all fields and only returns properties
	 * @return
	 */
	Iterator<JavaResourcePersistentAttribute> properties();
	
	JavaResourcePersistentType javaPersistentTypeResource(String fullyQualifiedTypeName);

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
		
	Member getMember();
	
	/**
	 * Return whether any attributes in this persistentType have
	 * JPA annotations on them.
	 */
	boolean hasAnyAttributeAnnotations();
}
