/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

public interface Fetchable extends AttributeMapping
{
	FetchType getFetch();

	FetchType getDefaultFetch();
		String DEFAULT_FETCH_PROPERTY = "defaultFetchProperty";
		
	FetchType getSpecifiedFetch();
	void setSpecifiedFetch(FetchType newSpecifiedFetch);
		String SPECIFIED_FETCH_PROPERTY = "specifiedFetchProperty";
	
}
