/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.Annotation;

/**
 * Corresponds to the EclipseLink annotation
 * <code>org.eclipse.persistence.annotations.TimeOfDay</code>
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
public interface EclipseLinkTimeOfDayAnnotation
	extends Annotation
{
	String ANNOTATION_NAME = EclipseLink.TIME_OF_DAY;


	// ********** hour **********

	/**
	 * Corresponds to the 'hour' element of the TimeOfDay annotation.
	 * Return null if the element does not exist in the annotation.
	 */
	Integer getHour();
		String HOUR_PROPERTY = "hour"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'hour' element of the TimeOfDay annotation.
	 * Set to null to remove the element from the annotation
	 */
	void setHour(Integer hour);

	/**
	 * Return the {@link TextRange} for the 'hour' element. If the element 
	 * does not exist return the {@link TextRange} for the TimeOfDay annotation.
	 */
	TextRange getHourTextRange(CompilationUnit astRoot);


	// ********** minute **********

	/**
	 * Corresponds to the 'minute' element of the TimeOfDay annotation.
	 * Return null if the element does not exist in the annotation.
	 */
	Integer getMinute();
		String MINUTE_PROPERTY = "minute"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'minute' element of the TimeOfDay annotation.
	 * Set to null to remove the element from the annotation
	 */
	void setMinute(Integer minute);

	/**
	 * Return the {@link TextRange} for the 'minute' element. If the element 
	 * does not exist return the {@link TextRange} for the TimeOfDay annotation.
	 */
	TextRange getMinuteTextRange(CompilationUnit astRoot);


	// ********** second **********

	/**
	 * Corresponds to the 'second' element of the TimeOfDay annotation.
	 * Return null if the element does not exist in the annotation.
	 */
	Integer getSecond();
		String SECOND_PROPERTY = "second"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'second' element of the TimeOfDay annotation.
	 * Set to null to remove the element from the annotation
	 */
	void setSecond(Integer second);

	/**
	 * Return the {@link TextRange} for the 'second' element. If the element 
	 * does not exist return the {@link TextRange} for the TimeOfDay annotation.
	 */
	TextRange getSecondTextRange(CompilationUnit astRoot);


	// ********** millisecond **********

	/**
	 * Corresponds to the 'millisecond' element of the TimeOfDay annotation.
	 * Return null if the element does not exist in the annotation.
	 */
	Integer getMillisecond();
		String MILLISECOND_PROPERTY = "millisecond"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'millisecond' element of the TimeOfDay annotation.
	 * Set to null to remove the element from the annotation
	 */
	void setMillisecond(Integer millisecond);

	/**
	 * Return the {@link TextRange} for the 'millisecond' element. If the element 
	 * does not exist return the {@link TextRange} for the TimeOfDay annotation.
	 */
	TextRange getMillisecondTextRange(CompilationUnit astRoot);
}
