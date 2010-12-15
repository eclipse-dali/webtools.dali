/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jdt.core.IPackageFragment;

/**
 * Java source code or binary type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface AbstractJavaResourceType
	extends JavaResourceMember
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
	 * Return the name of the type's "declaring type".
	 * Return <code>null</code> if the type is a top-level type.
	 */
	String getDeclaringTypeName();
		String DECLARING_TYPE_NAME_PROPERTY = "declaringTypeName"; //$NON-NLS-1$

	/**
	 * Return whether the type is a member type.
	 */
	boolean isMemberType();
		String MEMBER_TYPE_PROPERTY = "memberType"; //$NON-NLS-1$
	
	/**
	 * Return whether the type is annotated with any annotations that determine whether and 
	 * how the type is persisted
	 */
	boolean isMapped();

	boolean isIn(IPackageFragment packageFragment);


	/**
	 * Return the immediately nested types (children).
	 */
	Iterable<JavaResourceType> getTypes();
		String TYPES_COLLECTION = "types"; //$NON-NLS-1$

	/**
	 * Return all the types; the type itself, its children, its grandchildren,
	 * etc.
	 */
	Iterable<JavaResourceType> getAllTypes();

	/**
	 * Return the immediately nested enums (children).
	 */
	Iterable<JavaResourceEnum> getEnums();
		String ENUMS_COLLECTION = "enums"; //$NON-NLS-1$

	/**
	 * Return all the enums; the enum itself, its children, its grandchildren,
	 * etc.
	 */
	Iterable<JavaResourceEnum> getAllEnums();

}
