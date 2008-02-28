/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Column extends AbstractColumn
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
