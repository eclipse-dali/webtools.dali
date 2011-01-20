/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context;

import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;

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
 * @version 2.1
 * @since 2.1
 */
public interface EclipseLinkConverter
	extends JpaContextNode
{

	String ECLIPSELINK_CONVERTER_CLASS_NAME = "org.eclipse.persistence.mappings.converters.Converter"; //$NON-NLS-1$

	/**
	 * Return the converter's type.
	 */
	Class<? extends EclipseLinkConverter> getType();
		@SuppressWarnings("unchecked")
		Iterable<Class<? extends EclipseLinkConverter>> TYPES = new ArrayIterable<Class<? extends EclipseLinkConverter>>(
			EclipseLinkCustomConverter.class,
			EclipseLinkObjectTypeConverter.class,
			EclipseLinkStructConverter.class,
			EclipseLinkTypeConverter.class
		);
		

	String getName();	
	void setName(String name);
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	/**
	 * Return the character to be used for browsing or creating the converter
	 * class {@link org.eclipse.jdt.core.IType IType}.
	 * @see org.eclipse.jdt.core.IType#getFullyQualifiedName(char)
	 */
	char getEnclosingTypeSeparator();

	/**
	 * Return whether the converter "overrides" the specified converter
	 * (e.g. a converter defined in <code>orm.xml</code> overrides one
	 * defined in Java).
	 */
	boolean overrides(EclipseLinkConverter converter);
	
	/**
	 * Return whether the converter is a duplicate of the specified converter.
	 * A converter is not a duplicate of another converter if is the exact
	 * same converter, if it is a nameless converter (which is an error
	 * condition), or if it overrides or is overridden by the other converter.
	 */
	boolean duplicates(EclipseLinkConverter converter);
}
