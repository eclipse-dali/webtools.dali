/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * JPA generated value
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 */
public interface GeneratedValue
	extends JpaContextModel
{
	GenerationType getStrategy();
	GenerationType getSpecifiedStrategy();
	void setSpecifiedStrategy(GenerationType strategy);
		String SPECIFIED_STRATEGY_PROPERTY = "specifiedStrategy"; //$NON-NLS-1$
	GenerationType getDefaultStrategy();
		String DEFAULT_STRATEGY_PROPERTY = "defaultStrategy"; //$NON-NLS-1$
		GenerationType DEFAULT_STRATEGY = GenerationType.AUTO;
	
	String getGenerator();
	String getSpecifiedGenerator();
	void setSpecifiedGenerator(String generator);
		String SPECIFIED_GENERATOR_PROPERTY = "specifiedGenerator"; //$NON-NLS-1$
	String getDefaultGenerator();
		String DEFAULT_GENERATOR_PROPERTY = "defaultGenerator"; //$NON-NLS-1$

	/**
	 * Return the (best guess) text location of the generator.
	 */
	TextRange getGeneratorTextRange();
}
