/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
 * Corresponds to the javax.persistence.GeneratedValue annotation
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface GeneratedValueAnnotation extends JavaResourceNode
{	
	String ANNOTATION_NAME = JPA.GENERATED_VALUE;

	/**
	 * Corresponds to the strategy element of the GeneratedValue annotation.
	 * Returns null if the strategy element does not exist in java.
	 */
	GenerationType getStrategy();
	
	/**
	 * Corresponds to the strategy element of the GeneratedValue annotation.
	 * Set to null to remove the strategy element.
	 */
	void setStrategy(GenerationType strategy);
		String STRATEGY_PROPERTY = "strategyProperty";
		
	/**
	 * Corresponds to the generator element of the GeneratedValue annotation.
	 * Returns null if the generator element does not exist in java.
	 */
	String getGenerator();
	
	/**
	 * Corresponds to the generator element of the GeneratedValue annotation.
	 * Set to null to remove the generator element.
	 */
	void setGenerator(String generator);
		String GENERATOR_PROPERTY = "generatorProperty";
	
	
	/**
	 * Return the ITextRange for the generator element.  If the generator element 
	 * does not exist return the ITextRange for the Enumerated annotation.
	 */
	TextRange generatorTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the strategy element.  If the strategy element 
	 * does not exist return the ITextRange for the Enumerated annotation.
	 */
	TextRange strategyTextRange(CompilationUnit astRoot);

}
