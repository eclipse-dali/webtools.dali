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

import java.util.Iterator;

/**
 * Java source code persistent type
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaResourcePersistentType
	extends JavaResourcePersistentMember
{
	/**
	 * Return the unqualified (short) type name.
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Return the fully qualified type name.
	 */
	String getQualifiedName();
		String QUALIFIED_NAME_PROPERTY = "qualifiedName"; //$NON-NLS-1$

	/**
	 * Return the fully qualified name of the type's super class.
	 */
	String getSuperClassQualifiedName();
		String SUPER_CLASS_QUALIFIED_NAME_PROPERTY = "superClassQualifiedName"; //$NON-NLS-1$

	/**
	 * Return whether the type is abstract.
	 */
	boolean isAbstract();
		String ABSTRACT_PROPERTY = "abstract"; //$NON-NLS-1$

	/**
	 * Return tye type's access type ("field" or "property").
	 */
	AccessType getAccess();
		String ACCESS_PROPERTY = "access"; //$NON-NLS-1$

	/**
	 * Return whether the type has any attributes that have
	 * JPA annotations on them.
	 */
	boolean hasAnyAttributePersistenceAnnotations();


	// ********** types **********

	/**
	 * Return the immediately nested types (children).
	 */
	Iterator<JavaResourcePersistentType> types();
		String TYPES_COLLECTION = "types"; //$NON-NLS-1$

	/**
	 * Return all the types; the type itself, its children, its grandchildren,
	 * etc.
	 */
	Iterator<JavaResourcePersistentType> allTypes();

	/**
	 * Return the immediately nested persistable types.
	 */
	Iterator<JavaResourcePersistentType> persistableTypes();

	/**
	 * Return all the "persistable" types (the type itself, its children, its
	 * grandchildren, etc.) as defined by the JPA spec.
	 */
	Iterator<JavaResourcePersistentType> allPersistableTypes();


	// ********** fields **********

	/**
	 * Return the type's fields.
	 */
	Iterator<JavaResourcePersistentAttribute> fields();
		String FIELDS_COLLECTION = "fields"; //$NON-NLS-1$

	/**
	 * Return the type's persistable fields.
	 */
	Iterator<JavaResourcePersistentAttribute> persistableFields();

	/**
	 * A convenience method that returns the persistableFields that also
	 * have the Access annotation with a value of FIELD
	 */
	Iterator<JavaResourcePersistentAttribute> persistableFieldsWithSpecifiedFieldAccess();

	// ********** methods **********

	/**
	 * Return the type's methods. This returns *all* methods from the JDT Type
	 */
	Iterator<JavaResourcePersistentAttribute> methods();
		String METHODS_COLLECTION = "methods"; //$NON-NLS-1$

	/**
	 * Return the type's persistable properties.  This returns only the getter methods
	 * that match the JavaBeans criteria for JPA, hence the name properties instead of methods
	 */
	Iterator<JavaResourcePersistentAttribute> persistableProperties();

	/**
	 * A convenience method that returns the persistableProperties that also
	 * have the Access annotation with a value of PROPERTY
	 */
	Iterator<JavaResourcePersistentAttribute> persistablePropertiesWithSpecifiedPropertyAccess();

	// ********** attributes **********

	/**
	 * Return the type's persistable fields and properties.
	 */
	Iterator<JavaResourcePersistentAttribute> persistableAttributes();
	
	/**
	 * Return the persitable properties and/or fields given the non-null specified access type
	 */
	Iterator<JavaResourcePersistentAttribute> persistableAttributes(AccessType specifiedAccess);
	
}
