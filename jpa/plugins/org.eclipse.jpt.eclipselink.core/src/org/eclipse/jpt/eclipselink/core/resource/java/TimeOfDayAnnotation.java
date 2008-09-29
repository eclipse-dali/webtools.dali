/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Resource model interface that represents the 
 * org.eclipse.persistence.annotations.TimeOfDay annotation
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
public interface TimeOfDayAnnotation extends JavaResourceNode
{
	
	String ANNOTATION_NAME = EclipseLinkJPA.TIME_OF_DAY;
	
	/**
	 * Corresponds to the hour element of the TimeOfDay annotation.
	 * Returns null if the hour valuePair does not exist in the annotation
	 */
	Integer getHour();
	
	/**
	 * Corresponds to the hour element of the TimeOfDay annotation.
	 * Set to null to remove the hour valuePair from the annotation
	 */
	void setHour(Integer hour);
		String HOUR_PROPERTY = "hourProperty"; //$NON-NLS-1$
		
	/**
	 * Corresponds to the minute element of the TimeOfDay annotation.
	 * Returns null if the minute valuePair does not exist in the annotation
	 */
	Integer getMinute();
	
	/**
	 * Corresponds to the minute element of the TimeOfDay annotation.
	 * Set to null to remove the minute valuePair from the annotation
	 */
	void setMinute(Integer minute);
		String MINUTE_PROPERTY = "minuteProperty"; //$NON-NLS-1$
		
	/**
	 * Corresponds to the second element of the TimeOfDay annotation.
	 * Returns null if the second valuePair does not exist in the annotation
	 */
	Integer getSecond();
	
	/**
	 * Corresponds to the second element of the TimeOfDay annotation.
	 * Set to null to remove the second valuePair from the annotation
	 */
	void setSecond(Integer second);
		String SECOND_PROPERTY = "secondProperty"; //$NON-NLS-1$

	
	/**
	 * Corresponds to the millisecond element of the TimeOfDay annotation.
	 * Returns null if the millisecond valuePair does not exist in the annotation
	 */
	Integer getMillisecond();
	
	/**
	 * Corresponds to the millisecond element of the TimeOfDay annotation.
	 * Set to null to remove the millisecond valuePair from the annotation
	 */
	void setMillisecond(Integer millisecond);
		String MILLISECOND_PROPERTY = "millisecondProperty"; //$NON-NLS-1$
		
	
	/**
	 * Return the {@link TextRange} for the hour element.  If the hour element 
	 * does not exist return the {@link TextRange} for the TimeOfDay annotation.
	 */
	TextRange getHourTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the minute element.  If the minute element 
	 * does not exist return the {@link TextRange} for the TimeOfDay annotation.
	 */
	TextRange getMinuteTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the second element.  If the second element 
	 * does not exist return the {@link TextRange} for the TimeOfDay annotation.
	 */
	TextRange getSecondTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the millisecond element.  If the millisecond element 
	 * does not exist return the {@link TextRange} for the TimeOfDay annotation.
	 */
	TextRange getMillisecondTextRange(CompilationUnit astRoot);
		

}
