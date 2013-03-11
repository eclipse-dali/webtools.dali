/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the EclipseLink annotation
 * <code>org.eclipse.persistence.annotations.ConversionValue</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface ConversionValueAnnotation
	extends NestableAnnotation
{
	String ANNOTATION_NAME = EclipseLink.CONVERSION_VALUE;

	/**
	 * Corresponds to the 'dataValue' element of the ConversionValue annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getDataValue();
		String DATA_VALUE_PROPERTY = "dataValue"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'dataValue' element of the ConversionValue annotation.
	 * Set to null to remove the element.
	 */
	void setDataValue(String dataValue);

	/**
	 * Return the {@link TextRange} for the 'dataValue' element. If the element 
	 * does not exist return the {@link TextRange} for the ConversionValue annotation.
	 */
	TextRange getDataValueTextRange();


	/**
	 * Corresponds to the 'objectValue' element of the ConversionValue annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getObjectValue();
		String OBJECT_VALUE_PROPERTY = "objectValue"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'objectValue' element of the ConversionValue annotation.
	 * Set to null to remove the element.
	 */
	void setObjectValue(String objectValue);

	/**
	 * Return the {@link TextRange} for the 'objectValue' element. If the element 
	 * does not exist return the {@link TextRange} for the ConversionValue annotation.
	 */
	TextRange getObjectValueTextRange();
}
