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

public interface IColumn extends IAbstractColumn
{
	Integer getLength();

	Integer getDefaultLength();
	Integer DEFAULT_LENGTH = Integer.valueOf(255);
		String DEFAULT_LENGTH_PROPERTY = "defaultLengthProperty";

	Integer getSpecifiedLength();
	void setSpecifiedLength(Integer newSpecifiedLength);
		String SPECIFIED_LENGTH_PROPERTY = "spcifiedLengthProperty";
		
	Integer getPrecision();

	Integer getDefaultPrecision();
		Integer DEFAULT_PRECISION = Integer.valueOf(0);
		String DEFAULT_PRECISION_PROPERTY = "defaultPrecisionProperty";

	Integer getSpecifiedPrecision();
	void setSpecifiedPrecision(Integer newSpecifiedPrecision);
		String SPECIFIED_PRECISION_PROPERTY = "spcifiedPrecisionProperty";

	
	Integer getScale();

	Integer getDefaultScale();
		Integer DEFAULT_SCALE = Integer.valueOf(0);
		String DEFAULT_SCALE_PROPERTY = "defaultScaleProperty";

	Integer getSpecifiedScale();
	void setSpecifiedScale(Integer newSpecifiedScale);
		String SPECIFIED_SCALE_PROPERTY = "spcifiedScaleProperty";

	/**
	 * Return whether the column is found on the datasource
	 */
	boolean isResolved();
	
}
