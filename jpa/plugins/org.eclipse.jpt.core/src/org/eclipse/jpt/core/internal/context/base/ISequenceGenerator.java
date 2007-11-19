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


public interface ISequenceGenerator extends IGenerator
{
	int DEFAULT_INITIAL_VALUE = 1;

	String getSequenceName();
	
	String getDefaultSequenceName();
		String DEFAULT_SEQUENCE_NAME_PROPERTY = "defaultSequenceNameProperty";
	String getSpecifiedSequenceName();
	void setSpecifiedSequenceName(String value);
		String SPECIFIED_SEQUENCE_NAME_PROPERTY = "specifiedSequenceNameProperty";
}
