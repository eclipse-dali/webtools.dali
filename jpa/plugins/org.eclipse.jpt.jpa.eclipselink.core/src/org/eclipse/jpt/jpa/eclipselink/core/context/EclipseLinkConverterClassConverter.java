/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

/**
 * Corresponds to a *ConverterClassConverter resource model object
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 2.4
 */

public interface EclipseLinkConverterClassConverter
	extends EclipseLinkConverter
{
	String getConverterClass();	
		String CONVERTER_CLASS_PROPERTY = "converterClass"; //$NON-NLS-1$
	void setConverterClass(String converterClass);

	String getFullyQualifiedConverterClass();	
		String FULLY_QUALIFIED_CONVERTER_CLASS_PROPERTY = "fullyQualifiedConverterClass"; //$NON-NLS-1$
}
