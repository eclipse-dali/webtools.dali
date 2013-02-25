/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Context managed type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface ManagedType
	extends JpaContextModel
{

	/**
	 * Return the managed type's type.
	 * @see org.eclipse.jpt.jpa.core.context.orm.OrmManagedTypeDefinition#getContextType()
	 * @see org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition#getType()
	 */
	Class<? extends ManagedType> getType();

	
	// ********** name **********

	/**
	 * Return the managed type's [fully-qualified] name.
	 * The enclosing type separator is <code>'.'</code>,
	 * as opposed to <code>'$'</code>.
	 * @see #getSimpleName()
	 * @see #getTypeQualifiedName()
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
	Transformer<ManagedType, String> NAME_TRANSFORMER = new NameTransformer();
	class NameTransformer
		extends TransformerAdapter<ManagedType, String>
	{
		@Override
		public String transform(ManagedType mt) {
			return mt.getName();
		}
	}

	/**
	 * Return the managed type's simple name.
	 * @see #getName()
	 * @see #getTypeQualifiedName()
	 */
	String getSimpleName();

	/**
	 * Return the persistent type's type-qualified name; i.e. the type's
	 * name without its package qualification.
	 * @see #getName()
	 * @see #getSimpleName()
	 */
	String getTypeQualifiedName();

	/**
	 * Return the Java resource type.
	 */
	JavaResourceType getJavaResourceType();


	// ********** misc **********

	/**
	 * Return whether the managed type applies to the
	 * specified type name qualified with <code>'.'</code>.
	 */
	boolean isFor(String typeName);

	/**
	 * Return whether the managed type resolves to a Java class in the
	 * specified package fragment.
	 */
	boolean isIn(IPackageFragment packageFragment);

}
