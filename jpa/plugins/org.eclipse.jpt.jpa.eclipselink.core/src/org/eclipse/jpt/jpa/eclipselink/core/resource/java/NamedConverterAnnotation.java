/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Common protocol among:<code><ul>
 * <li>org.eclipse.persistence.annotations.Converter
 * <li>org.eclipse.persistence.annotations.StructConverter
 * <li>org.eclipse.persistence.annotations.TypeConverter
 * <li>org.eclipse.persistence.annotations.ObjectTypeConverter
 * </ul></code>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 2.1
 */
public interface NamedConverterAnnotation
	extends NestableAnnotation
{
	/**
	 * Corresponds to the 'name' element of the *Converter annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'name' element of the *Converter annotation.
	 * Set to null to remove the element.
	 */
	void setName(String value);

	/**
	 * Return the {@link TextRange} for the 'name' element. If the element 
	 * does not exist return the {@link TextRange} for the CustomConverter annotation.
	 */
	TextRange getNameTextRange();
}
