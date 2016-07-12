/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;

/**
 * EclipseLink converter:<ul>
 * <li>Type ({@link EclipseLinkTypeConverter})
 * <li>Object Type ({@link EclipseLinkObjectTypeConverter})
 * <li>Struct ({@link EclipseLinkStructConverter})
 * <li>Custom ({@link EclipseLinkCustomConverter})
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.1
 */
public interface EclipseLinkConverter
	extends JpaNamedContextModel
{
	/**
	 * Return the converter's type.
	 */
	Class<? extends EclipseLinkConverter> getConverterType();

	/**
	 * A transformer that casts an {@link EclipseLinkConverter} to a specified type
	 * if it is an instance of that type; otherwise, it returns <code>null</code>.
	 */
	class ConverterTransformer<C extends EclipseLinkConverter>
		extends TransformerAdapter<EclipseLinkConverter, C>
	{
		private final Class<C> clazz;
		public ConverterTransformer(Class<C> clazz) {
			super();
			if (clazz == null) {
				throw new NullPointerException();
			}
			this.clazz = clazz;
		}
		@Override
		@SuppressWarnings("unchecked")
		public C transform(EclipseLinkConverter converter) {
			return (converter.getConverterType() == this.clazz) ? (C) converter : null;
		}
	}

	/**
	 * Return whether the specified converter has the same state as this
	 * converter but is <em>not</em> this converter.
	 * @see #getConverterType()
	 */
	boolean isEquivalentTo(EclipseLinkConverter converter);

	/**
	 * Return the character to be used for browsing or creating the converter
	 * class {@link org.eclipse.jdt.core.IType IType}.
	 * @see org.eclipse.jdt.core.IType#getFullyQualifiedName(char)
	 */
	char getEnclosingTypeSeparator();


	// ********** validation **********

	/**
	 * Return whether this query should be validated and have validation messages displayed
	 */
	boolean supportsValidationMessages();

	TextRange getNameTextRange();
}
