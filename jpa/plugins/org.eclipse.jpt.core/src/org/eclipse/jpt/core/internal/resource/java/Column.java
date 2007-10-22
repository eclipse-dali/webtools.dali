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

	/**
	 * Corresponds to the length element of the javax.persistence.Column annotation.
	 * Returns -1 if the length valuePair does not exist in the annotation
	 */
	int getLength();
	
	/**
	 * Corresponds to the length element of the javax.persistence.Column annotation.
	 * Set to -1 to remove the length valuePair from the annotation
	 */
	void setLength(int length);
	
	/**
	 * Corresponds to the precision element of the javax.persistence.Column annotation.
	 * Returns -1 if the precision valuePair does not exist in the annotation
	 */
	int getPrecision();
	
	/**
	 * Corresponds to the precision element of the javax.persistence.Column annotation.
	 * Set to -1 to remove the precision valuePair from the annotation
	 */
	void setPrecision(int precision);
	
	/**
	 * Corresponds to the scale element of the javax.persistence.Column annotation.
	 * Returns -1 if the scale valuePair does not exist in the annotation
	 */
	int getScale();
	
	/**
	 * Corresponds to the scale element of the javax.persistence.Column annotation.
	 * Set to -1 to remove the scale valuePair from the annotation
	 */
	void setScale(int scale);

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
