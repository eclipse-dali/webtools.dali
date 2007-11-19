/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;


public interface IGeneratedValue extends IJpaContextNode
{

	GenerationType getStrategy();
	GenerationType getDefaultStrategy();
	GenerationType getSpecifiedStrategy();
	void setSpecifiedStrategy(GenerationType value);
		String SPECIFIED_STRATEGY_PROPERTY = "specifiedStrategyProperty";
		String DEFAULT_STRATEGY_PROPERTY = "defaultStrategyProperty";
	
	String getGenerator();
	String getDefaultGenerator();
		GenerationType DEFAULT_STRATEGY = GenerationType.AUTO;
	String getSpecifiedGenerator();
	void setSpecifiedGenerator(String value);
		String SPECIFIED_GENERATOR_PROPERTY = "specifiedGeneratorProperty";
		String DEFAULT_GENERATOR_PROPERTY = "defaultGeneratorProperty";

	/**
	 * Return the (best guess) text location of the generator.
	 */
	ITextRange generatorTextRange(CompilationUnit astRoot);
}
