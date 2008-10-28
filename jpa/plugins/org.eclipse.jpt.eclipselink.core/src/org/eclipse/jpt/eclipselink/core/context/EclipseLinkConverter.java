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

import org.eclipse.jpt.core.context.JpaContextNode;

/**
 * Corresponds to a *Converter resource model object
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
public interface EclipseLinkConverter extends JpaContextNode
{
	/**
	 * Return a string that represents the type of converter.
	 * Possibilities are below, NO_CONVERTER, CONVERTER, 
	 * OBJECT_TYPE_CONVERTER, STRUCT_CONVERTER, TYPE_CONVERTER
	 */
	String getType();
	
	String CONVERTER = "converter"; //$NON-NLS-1$
	String NO_CONVERTER = "noConverter"; //$NON-NLS-1$
	String OBJECT_TYPE_CONVERTER = "objectTypeConverter"; //$NON-NLS-1$
	String STRUCT_CONVERTER = "structConverter"; //$NON-NLS-1$
	String TYPE_CONVERTER = "typeConverter"; //$NON-NLS-1$
	
	String getName();	
	void setName(String name);
		String NAME_PROPERTY = "nameProperty"; //$NON-NLS-1$
}
