/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;

/**
 * Corresponds to the javax.persistence.Inheritance annotation
 */
public interface Inheritance extends JavaResourceNode
{
	String ANNOTATION_NAME = JPA.INHERITANCE;

	InheritanceType getStrategy();
	
	void setStrategy(InheritanceType strategy);
		String STRATEGY_PROPERTY = "strategyProperty";
		
	/**
	 * Return the ITextRange for the strategy element.  If the strategy element 
	 * does not exist return the ITextRange for the Inheritance annotation.
	 */
	TextRange strategyTextRange(CompilationUnit astRoot);

}
