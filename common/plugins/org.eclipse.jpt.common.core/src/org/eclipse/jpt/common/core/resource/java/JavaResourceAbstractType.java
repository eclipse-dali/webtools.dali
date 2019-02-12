/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;

/**
 * Java source code or binary type.  This corresponds to a {@link AbstractTypeDeclaration}
 * (which is why the name is somewhat wonky.)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.0
 */
public interface JavaResourceAbstractType
		extends JavaResourceMember {
	
	/**
	 * Return the type binding for this type
	 */
	TypeBinding getTypeBinding();
		String TYPE_BINDING_PROPERTY = "typeBinding"; //$//$NON-NLS-1$
	
	/**
	 * Return the name of the type's "declaring type".
	 * Return <code>null</code> if the type is a top-level type.
	 */
	String getDeclaringTypeName();
		String DECLARING_TYPE_NAME_PROPERTY = "declaringTypeName"; //$NON-NLS-1$
	
	boolean isIn(IPackageFragment packageFragment);

	boolean isIn(IPackageFragmentRoot sourceFolder);


	/**
	 * Return the immediately nested types (classes or interfaces, not enums or annotations) (children).
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
