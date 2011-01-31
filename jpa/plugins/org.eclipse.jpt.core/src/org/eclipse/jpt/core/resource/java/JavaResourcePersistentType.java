/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IPackageFragment;

/**
 * Java source code or binary persistent type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
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
	 * Return the package name.
	 */
	String getPackageName();
		String PACKAGE_NAME_PROPERTY = "packageName"; //$NON-NLS-1$

	/**
	 * Return the fully qualified name of the type's superclass.
	 */
	String getSuperclassQualifiedName();
		String SUPERCLASS_QUALIFIED_NAME_PROPERTY = "superclassQualifiedName"; //$NON-NLS-1$

	/**
	 * Return the name of the type's "declaring type".
	 * Return <code>null</code> if the type is a top-level type.
	 */
	String getDeclaringTypeName();
		String DECLARING_TYPE_NAME_PROPERTY = "declaringTypeName"; //$NON-NLS-1$
	
	/**
	 * Return whether the type is abstract.
	 */
	boolean isAbstract();
		String ABSTRACT_PROPERTY = "abstract"; //$NON-NLS-1$

	/**
	 * Return whether the type is a member type.
	 */
	boolean isMemberType();
		String MEMBER_TYPE_PROPERTY = "memberType"; //$NON-NLS-1$

	/**
	 * Return whether the type is static.
	 */
	boolean isStatic();
		String STATIC_PROPERTY = "static"; //$NON-NLS-1$

	/**
	 * Return whether the type has a no-arg constructor (private, protected, or public)
	 */
	boolean hasNoArgConstructor();
		String NO_ARG_CONSTRUCTOR_PROPERTY = "noArgConstructor"; //$NON-NLS-1$

	/**
	 * Return whether the type has a private no-arg constructor
	 */
	boolean hasPrivateNoArgConstructor();
		String PRIVATE_NO_ARG_CONSTRUCTOR_PROPERTY = "privateNoArgConstructor"; //$NON-NLS-1$
	
	/**
	 * Return whether the type is annotated with any annotations that determine whether and 
	 * how the type is persisted
	 */
	boolean isMapped();
	
	/**
	 * Return whether the type has any attributes that have JPA annotations
	 * on them (which can be used to infer the type's access type).
	 */
	boolean hasAnyAnnotatedAttributes();

	boolean isIn(IPackageFragment packageFragment);


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
	 * Return the persistable properties and/or fields given the non-null specified access type
	 */
	Iterator<JavaResourcePersistentAttribute> persistableAttributes(AccessType specifiedAccess);
	
	class Tools {
		// ********** Access type **********

		/**
		 * Return the access type currently implied by the specified Java source
		 * code or class file:<ul>
		 * <li>if any fields are annotated =>
		 *     {@link AccessType#FIELD FIELD}
		 * <li>if only properties are annotated =>
		 *     {@link AccessType#PROPERTY PROPERTY}
		 * <li>if neither are annotated =>
		 *     <code>null</code>
		 *     
		 * </ul>
		 */
		public static AccessType buildAccess(JavaResourcePersistentType jrpType) {
			for (Iterator<JavaResourcePersistentAttribute> stream = jrpType.persistableFields(); stream.hasNext(); ) {
				if (stream.next().isAnnotated()) {
					// any field is annotated => FIELD
					return AccessType.FIELD;
				}
			}

			for (Iterator<JavaResourcePersistentAttribute> stream = jrpType.persistableProperties(); stream.hasNext(); ) {
				if (stream.next().isAnnotated()) {
					// none of the fields are annotated and a getter is annotated => PROPERTY
					return AccessType.PROPERTY;
				}
			}

			// nothing is annotated
			return null;
		}

	}
	
}
