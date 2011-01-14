/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public interface JavaContextNode
		extends JaxbContextNode {
	
	// **************** content assist ****************************************
	
	/**
	 * Return the Java code-completion proposals for the specified position in the source code.
	 */
	Iterable<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot);
	
	
	// ******************** validation ****************************************
	
	/**
	 * Adds to the list of current validation messages
	 */
	void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot);
	
	/**
	 * Return the text range for highlighting errors for this object
	 */
	TextRange getValidationTextRange(CompilationUnit astRoot);
}
