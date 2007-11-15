/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

public interface IBasicMapping extends IAttributeMapping, IColumnMapping
{
	FetchType getFetch();

	FetchType getDefaultFetch();
		String DEFAULT_FETCH_PROPERTY = "defaultFetchProperty";
		FetchType DEFAULT_FETCH_TYPE = FetchType.EAGER;
		
	FetchType getSpecifiedFetch();
	void setSpecifiedFetch(FetchType newSpecifiedFetch);
		String SPECIFIED_FETCH_PROPERTY = "specifiedFetchProperty";


	Boolean getOptional();
	
	Boolean getDefaultOptional();
		String DEFAULT_OPTIONAL_PROPERTY = "defaultOptionalProperty";
		Boolean DEFAULT_OPTIONAL = Boolean.TRUE;
	
	Boolean getSpecifiedOptional();
	void setSpecifiedOptional(Boolean newSpecifiedOptional);
		String SPECIFIED_OPTIONAL_PROPERTY = "specifiedOptionalProperty";

//	IColumn getColumn();

	boolean isLob();

	void setLob(boolean value);
		String LOB_PROPERTY = "lobProperty";

//	TemporalType getTemporal();
//
//	void setTemporal(TemporalType value);

	
	EnumType getEnumerated();
	
	EnumType getDefaultEnumerated();
		String DEFAULT_ENUMERATED_PROPERTY = "defaultEnumeratedProperty";
		EnumType DEFAULT_ENUMERATED = EnumType.ORDINAL;

	EnumType getSpecifiedEnumerated();
	void setSpecifiedEnumerated(EnumType newSpecifiedEnumerated);
		String SPECIFIED_ENUMERATED_PROPERTY = "specifiedEnumeratedProperty";

}
