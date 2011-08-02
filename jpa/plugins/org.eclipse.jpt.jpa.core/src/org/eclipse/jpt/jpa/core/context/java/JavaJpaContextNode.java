/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java JPA context node.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface JavaJpaContextNode
	extends JpaContextNode
{
	/**
	 * Return the Java code-completion proposals for the specified position
	 * in the source code.
	 */
	Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot);


	// ********** validation **********

	/**
	 * Adds to the list of current validation messages
	 */
	void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot);

	TextRange getValidationTextRange(CompilationUnit astRoot);
}
