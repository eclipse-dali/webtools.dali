/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
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
	 * This is used to:<ul>
	 * <li>select persistent (or converter) types from any list of types that
	 *     contains both
	 * <li>match a managed type with its managed type definition
	 * <li>match a managed type with its managed type UI definition
	 * </ul>
	 * @see org.eclipse.jpt.jpa.core.context.orm.OrmManagedTypeDefinition#getContextManagedTypeType()
	 * @see org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition#getManagedTypeType()
	 */
	Class<? extends ManagedType> getManagedTypeType();

	
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
		String SIMPLE_NAME_PROPERTY = "simpleName"; //$NON-NLS-1$
	Transformer<ManagedType, String> SIMPLE_NAME_TRANSFORMER = new SimpleNameTransformer();
	class SimpleNameTransformer
		extends TransformerAdapter<ManagedType, String>
	{
		@Override
		public String transform(ManagedType mt) {
			return mt.getSimpleName();
		}
	}

	/**
	 * Return the persistent type's type-qualified name; i.e. the type's
	 * name without its package qualification.
	 * @see #getName()
	 * @see #getSimpleName()
	 */
	String getTypeQualifiedName();
		String TYPE_QUALIFIED_NAME_PROPERTY = "typeQualifiedName"; //$NON-NLS-1$
	Transformer<ManagedType, String> TYPE_QUALIFIED_NAME_TRANSFORMER = new TypeQualifiedNameTransformer();
	class TypeQualifiedNameTransformer
		extends TransformerAdapter<ManagedType, String>
	{
		@Override
		public String transform(ManagedType mt) {
			return mt.getTypeQualifiedName();
		}
	}

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
