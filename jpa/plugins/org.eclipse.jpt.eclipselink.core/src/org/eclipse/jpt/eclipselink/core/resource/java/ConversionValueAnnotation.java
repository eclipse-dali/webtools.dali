/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Corresponds to the EclipseLink annotation
 * org.eclipse.persistence.annotations.ConversionValue
 * 
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
	extends Annotation
{
	String ANNOTATION_NAME = EclipseLinkJPA.CONVERSION_VALUE;

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
	TextRange getDataValueTextRange(CompilationUnit astRoot);


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
	TextRange getObjectValueTextRange(CompilationUnit astRoot);

}
