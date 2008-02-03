/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;


public interface IQueryHint extends IJpaContextNode
{

	String getName();
	void setName(String value);
		String NAME_PROPERTY = "nameProperty";

	String getValue();
	void setValue(String value);
		String VALUE_PROPERTY = "valueProperty";
	
}
