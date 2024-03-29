/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * JPA enumerated/map key enumerated converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.1
 */
public interface BaseEnumeratedConverter
	extends Converter
{
	Class<BaseEnumeratedConverter> getConverterType();

	EnumType getEnumType();

	EnumType getSpecifiedEnumType();
	void setSpecifiedEnumType(EnumType enumType);
		String SPECIFIED_ENUM_TYPE_PROPERTY = "specifiedEnumType"; //$NON-NLS-1$
	
	EnumType getDefaultEnumType();
		String DEFAULT_ENUM_TYPE_PROPERTY = "defaultEnumType"; //$NON-NLS-1$
		EnumType DEFAULT_ENUM_TYPE = EnumType.ORDINAL;
}
