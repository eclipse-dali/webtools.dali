/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;


public interface Column extends AbstractColumn
{
	String ANNOTATION_NAME = JPA.COLUMN;

	/**
	 * Corresponds to the length element of the javax.persistence.Column annotation.
	 * Returns null if the length valuePair does not exist in the annotation
	 */
	Integer getLength();
	
	/**
	 * Corresponds to the length element of the javax.persistence.Column annotation.
	 * Set to null to remove the length valuePair from the annotation
	 */
	void setLength(Integer length);
		String LENGTH_PROPERTY = "lengthProperty";
	
	/**
	 * Corresponds to the precision element of the javax.persistence.Column annotation.
	 * Returns null if the precision valuePair does not exist in the annotation
	 */
	Integer getPrecision();
	
	/**
	 * Corresponds to the precision element of the javax.persistence.Column annotation.
	 * Set to null to remove the precision valuePair from the annotation
	 */
	void setPrecision(Integer precision);
		String PRECISION_PROPERTY = "precisionProperty";
	
	/**
	 * Corresponds to the scale element of the javax.persistence.Column annotation.
	 * Returns null if the scale valuePair does not exist in the annotation
	 */
	Integer getScale();
	
	/**
	 * Corresponds to the scale element of the javax.persistence.Column annotation.
	 * Set to null to remove the scale valuePair from the annotation
	 */
	void setScale(Integer scale);
		String SCALE_PROPERTY = "scaleProperty";

	/**
	 * Return the ITextRange for the length element. If length element
	 * does not exist return the ITextRange for the Column annotation.
	 */
	ITextRange lengthTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the precision element. If precision element
	 * does not exist return the ITextRange for the Column annotation.
	 */
	ITextRange precisionTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the ITextRange for the scale element. If scale element
	 * does not exist return the ITextRange for the Column annotation.
	 */
	ITextRange scaleTextRange(CompilationUnit astRoot);

}
