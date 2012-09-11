/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;


public interface JavaContextNode
		extends JaxbContextNode {
	
	// **************** content assist ****************************************
	
	/**
	 * Return the Java code-completion proposals for the specified position in the source code.
	 */
	Iterable<String> getCompletionProposals(int pos);
}
