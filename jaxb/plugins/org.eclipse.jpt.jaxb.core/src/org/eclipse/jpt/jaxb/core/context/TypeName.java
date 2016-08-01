/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Represents the name of a java type
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
public interface TypeName {
	
	/**
	 * Return the name of the type's package.  Empty string if none.
	 */
	String getPackageName();
	
	/**
	 * Return the name of the type without any package or type qualifiers
	 */
	String getSimpleName();
	
	/**
	 * Returns the type-qualified name of this type, including qualification for any 
	 * enclosing types, but not including package qualification.
	 */
	String getTypeQualifiedName();
	Transformer<TypeName, String> TYPE_QUALIFIED_NAME_TRANSFORMER = new TypeQualifiedNameTransformer();
	class TypeQualifiedNameTransformer
		extends TransformerAdapter<TypeName, String>
	{
		@Override
		public String transform(TypeName model) {
			return model.getTypeQualifiedName();
		}
	}
	
	/**
	 * Returns the fully qualified name of this type, including qualification for any 
	 * enclosing types and packages.
	 */
	String getFullyQualifiedName();
	Transformer<TypeName, String> FULLY_QUALIFIED_NAME_TRANSFORMER = new FullyQualifiedNameTransformer();
	class FullyQualifiedNameTransformer
		extends TransformerAdapter<TypeName, String>
	{
		@Override
		public String transform(TypeName model) {
			return model.getFullyQualifiedName();
		}
	}
}
