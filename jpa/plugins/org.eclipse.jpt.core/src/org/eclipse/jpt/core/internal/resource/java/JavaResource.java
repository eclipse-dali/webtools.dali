/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.node.Node;

public interface JavaResource extends Node
{
	void initialize(CompilationUnit astRoot);
	
	JavaResourceModel resourceModel();
	
	JpaCompilationUnitResource root();
	
	IJpaAnnotationProvider annotationProvider();
	
	CommandExecutorProvider modifySharedDocumentCommandExecutorProvider();
	
	void updateFromJava(CompilationUnit astRoot);
	
	/**
	 * Use to resolve type information that could be dependent on other files being added/removed
	 */
	void resolveTypes(CompilationUnit astRoot);
	
	/**
	 * Return the ITextRange 
	 */
	ITextRange textRange(CompilationUnit astRoot);
}
