/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Corresponds to the JPA annotation
 * javax.persistence.GeneratedValue
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface GeneratedValueAnnotation
	extends Annotation
{
	String ANNOTATION_NAME = JPA.GENERATED_VALUE;

	/**
	 * Corresponds to the 'strategy' element of the GeneratedValue annotation.
	 * Return null if the element does not exist in Java.
	 */
	GenerationType getStrategy();
		String STRATEGY_PROPERTY = "strategy"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'strategy' element of the GeneratedValue annotation.
	 * Set to null to remove the element.
	 */
	void setStrategy(GenerationType strategy);

	/**
	 * Return the {@link TextRange} for the 'strategy' element. If the element 
	 * does not exist return the {@link TextRange} for the GeneratedValue annotation.
	 */
	TextRange getStrategyTextRange(CompilationUnit astRoot);


	/**
	 * Corresponds to the 'generator' element of the GeneratedValue annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getGenerator();
		String GENERATOR_PROPERTY = "generator"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'generator' element of the GeneratedValue annotation.
	 * Set to null to remove the element.
	 */
	void setGenerator(String generator);

	/**
	 * Return the {@link TextRange} for the 'generator' element. If the element 
	 * does not exist return the {@link TextRange} for the GeneratedValue annotation.
	 */
	TextRange getGeneratorTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified position touches the 'generator' element.
	 * Return false if the element does not exist.
	 */
	boolean generatorTouches(int pos, CompilationUnit astRoot);

}
