/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaJaxbContextNode
		extends AbstractJaxbContextNode {
	
	// **************** constructor *******************************************
	
	protected AbstractJavaJaxbContextNode(JaxbNode parent) {
		super(parent);
	}
	
	
	/**
	 * Adds to the list of current validation messages.
	 * 
	 * All subclass implementations {@link #validate(List, CompilationUnit))} 
	 * should be preceded by a "super" call to this method
	 */
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
	}
	
	
	public abstract TextRange getValidationTextRange(CompilationUnit astRoot);
}
