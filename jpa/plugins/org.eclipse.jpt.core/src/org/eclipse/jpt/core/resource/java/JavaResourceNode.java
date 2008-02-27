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
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.internal.resource.java.JpaCompilationUnitResource;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.node.Node;

public interface JavaResourceNode extends Node
{
	void initialize(CompilationUnit astRoot);
	
	JavaResourceModel resourceModel();
	
	JpaCompilationUnitResource root();
	
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
