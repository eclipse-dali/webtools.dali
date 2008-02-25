/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;


public interface BasicMapping extends AttributeMapping, ColumnMapping, Fetchable, Nullable
{
	FetchType DEFAULT_FETCH_TYPE = FetchType.EAGER;

	boolean isLob();

	void setLob(boolean value);
		String LOB_PROPERTY = "lobProperty";
	
	EnumType getEnumerated();
	
	EnumType getDefaultEnumerated();
		String DEFAULT_ENUMERATED_PROPERTY = "defaultEnumeratedProperty";
		EnumType DEFAULT_ENUMERATED = EnumType.ORDINAL;

	EnumType getSpecifiedEnumerated();
	void setSpecifiedEnumerated(EnumType newSpecifiedEnumerated);
		String SPECIFIED_ENUMERATED_PROPERTY = "specifiedEnumeratedProperty";

}
