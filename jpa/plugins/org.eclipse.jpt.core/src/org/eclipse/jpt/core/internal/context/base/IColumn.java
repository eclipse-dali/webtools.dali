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
	int getLength();

	int getDefaultLength();
		int DEFAULT_LENGTH = 255;
		String DEFAULT_LENGTH_PROPERTY = "defaultLengthProperty";

	int getSpecifiedLength();
	void setSpecifiedLength(int newSpecifiedLength);
		String SPECIFIED_LENGTH_PROPERTY = "spcifiedLengthProperty";
		
	int getPrecision();

	int getDefaultPrecision();
		int DEFAULT_PRECISION = 0;
		String DEFAULT_PRECISION_PROPERTY = "defaultPrecisionProperty";

	int getSpecifiedPrecision();
	void setSpecifiedPrecision(int newSpecifiedPrecision);
		String SPECIFIED_PRECISION_PROPERTY = "spcifiedPrecisionProperty";

	
	int getScale();

	int getDefaultScale();
		int DEFAULT_SCALE = 0;
		String DEFAULT_SCALE_PROPERTY = "defaultScaleProperty";

	int getSpecifiedScale();
	void setSpecifiedScale(int newSpecifiedScale);
		String SPECIFIED_SCALE_PROPERTY = "spcifiedScaleProperty";

	/**
	 * Return whether the column is found on the datasource
	 */
	boolean isResolved();
	
	/**
	 * interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides)
	 */
	interface Owner extends INamedColumn.Owner
	{
		/**
		 * Return the name of the persistent attribute that contains the column.
		 */
		String attributeName();
	}
}
