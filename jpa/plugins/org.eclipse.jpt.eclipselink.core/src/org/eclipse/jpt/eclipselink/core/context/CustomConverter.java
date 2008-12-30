/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context;

/**
 * Corresponds to a CustomConverter resource model object
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
public interface CustomConverter extends EclipseLinkConverter
{
		
	String getConverterClass();	
	void setConverterClass(String converterClass);
		String CONVERTER_CLASS_PROPERTY = "converterClassProperty"; //$NON-NLS-1$
		String ECLIPSELINK_CONVERTER_CLASS_NAME = "org.eclipse.persistence.mappings.converters.Converter"; //$NON-NLS-1$

}
