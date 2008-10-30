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
 * Corresponds to a *CustomConverter resource model object
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
	 * Possibilities are below, NO_CONVERTER, CUSTOM_CONVERTER, 
	 * OBJECT_TYPE_CONVERTER, STRUCT_CONVERTER, TYPE_CONVERTER
	 */
	String getType();
		String NO_CONVERTER = "noConverter"; //$NON-NLS-1$
		String CUSTOM_CONVERTER = "customConverter"; //$NON-NLS-1$
		String OBJECT_TYPE_CONVERTER = "objectTypeConverter"; //$NON-NLS-1$
		String STRUCT_CONVERTER = "structConverter"; //$NON-NLS-1$
		String TYPE_CONVERTER = "typeConverter"; //$NON-NLS-1$
	
	String getName();	
	void setName(String name);
		String NAME_PROPERTY = "nameProperty"; //$NON-NLS-1$
	
	/**
	 * Return whether the converter definition overrides the definition of the 
	 * given converter
	 * (e.g. a converter defined in orm.xml overrides one defined in java).
	 */
	boolean overrides(EclipseLinkConverter converter);
	
	/**
	 * Return whether the converter is a duplicate of the given converter.
	 * A converter is not a duplicate of another converter if is the same exact converter,
	 * if it is a nameless converter (which is an error condition), or if it overrides 
	 * or is overridden by the other converter. 
	 */
	boolean duplicates(EclipseLinkConverter converter);
}
