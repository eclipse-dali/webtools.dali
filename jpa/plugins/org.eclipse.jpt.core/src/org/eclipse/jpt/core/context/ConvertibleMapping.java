/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */

public interface ConvertibleMapping
{
	
	/**
	 * Return the specified converter, return a converter of type Converter.NO_CONVERTER
	 * instead of null if no converter is specified.
	 */
	Converter getSpecifiedConverter();
	
	/**
	 * Set the specified converter type, adding the converter to the resource model
	 * and removing the old converter, if any, from the resource model.  JPA 1.0
	 * support Enumerated, Lob, and Temporal as converter types for basic mapppings.
	 * {@link Converter#ENUMERATED_CONVERTER}
	 * {@link Converter#LOB_CONVERTER}
	 * {@link Converter#TEMPORAL_CONVERTER}
	 * {@link Converter#NO_CONVERTER}
	 */
	void setSpecifiedConverter(String converterType);
		String SPECIFIED_CONVERTER_PROPERTY = "specifiedConverter"; //$NON-NLS-1$
	

}
