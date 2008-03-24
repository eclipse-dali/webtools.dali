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
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.internal.resource.java.JpaCompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.node.Node;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaResourceNode extends Node
{
	void initialize(CompilationUnit astRoot);
	
	JavaResourceModel resourceModel();
	
	JpaCompilationUnit root();
	
	JpaAnnotationProvider annotationProvider();
	
	CommandExecutorProvider modifySharedDocumentCommandExecutorProvider();
	
	void updateFromJava(CompilationUnit astRoot);
	
	/**
	 * Use to resolve type information that could be dependent on other files being added/removed
	 */
	void resolveTypes(CompilationUnit astRoot);
	
	/**
	 * Return the ITextRange 
	 */
	TextRange textRange(CompilationUnit astRoot);
}
