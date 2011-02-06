/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Read-only column
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlyColumn
	extends ReadOnlyBaseColumn
{
	// ********** length **********

	/**
	 * Return the specified length if present, otherwise return the default length.
	 */
	int getLength();
	Integer getSpecifiedLength();
		String SPECIFIED_LENGTH_PROPERTY = "specifiedLength"; //$NON-NLS-1$
	int getDefaultLength();
		String DEFAULT_LENGTH_PROPERTY = "defaultLength"; //$NON-NLS-1$
	int DEFAULT_LENGTH = 255;
		

	// ********** precision **********

	/**
	 * Return the specified precision if present, otherwise return the default precision.
	 */
	int getPrecision();
	Integer getSpecifiedPrecision();
		String SPECIFIED_PRECISION_PROPERTY = "specifiedPrecision"; //$NON-NLS-1$
	int getDefaultPrecision();
		String DEFAULT_PRECISION_PROPERTY = "defaultPrecision"; //$NON-NLS-1$
	int DEFAULT_PRECISION = 0;

	
	// ********** scale **********

	/**
	 * Return the specified scale if present, otherwise return the default scale.
	 */
	int getScale();
	Integer getSpecifiedScale();
		String SPECIFIED_SCALE_PROPERTY = "specifiedScale"; //$NON-NLS-1$
	int getDefaultScale();
		String DEFAULT_SCALE_PROPERTY = "defaultScale"; //$NON-NLS-1$
	int DEFAULT_SCALE = 0;
}
