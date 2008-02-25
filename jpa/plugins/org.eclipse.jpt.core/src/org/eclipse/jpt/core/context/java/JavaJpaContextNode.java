/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.utility.internal.Filter;

public interface JavaJpaContextNode extends JpaContextNode
{
	TextRange validationTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the Java code-completion proposals for the specified position
	 * in the source code.
	 */
	Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot);

}
