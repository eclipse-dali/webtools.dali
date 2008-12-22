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
 * This interface is used for mappings that support the optional element.
 * From the JPA spec:
 * 		Whether the value of the field or property may be null. This is a hint 
 * 		and is disregarded for primitive types; it may be used in schema generation.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Nullable extends AttributeMapping
{
	boolean isOptional();
	
	boolean isDefaultOptional();
		String DEFAULT_OPTIONAL_PROPERTY = "defaultOptional"; //$NON-NLS-1$
		boolean DEFAULT_OPTIONAL = true;
	
	Boolean getSpecifiedOptional();
	void setSpecifiedOptional(Boolean newSpecifiedOptional);
		String SPECIFIED_OPTIONAL_PROPERTY = "specifiedOptional"; //$NON-NLS-1$
	
}
