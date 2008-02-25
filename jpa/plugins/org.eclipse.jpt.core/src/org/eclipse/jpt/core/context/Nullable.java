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
 * @author kamoore
 */
public interface Nullable extends AttributeMapping
{
	Boolean getOptional();
	
	Boolean getDefaultOptional();
		String DEFAULT_OPTIONAL_PROPERTY = "defaultOptionalProperty";
		Boolean DEFAULT_OPTIONAL = Boolean.TRUE;
	
	Boolean getSpecifiedOptional();
	void setSpecifiedOptional(Boolean newSpecifiedOptional);
		String SPECIFIED_OPTIONAL_PROPERTY = "specifiedOptionalProperty";
	
}
