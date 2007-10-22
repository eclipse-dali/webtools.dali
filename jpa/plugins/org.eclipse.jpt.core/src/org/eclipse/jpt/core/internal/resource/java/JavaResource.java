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
import org.eclipse.jpt.core.internal.IJpaNodeModel;
import org.eclipse.jpt.core.internal.ITextRange;

public interface JavaResource extends IJpaNodeModel
{
	
	void updateFromJava(CompilationUnit astRoot);


	/**
	 * Return the ITextRange 
	 */
	ITextRange textRange(CompilationUnit astRoot);
}
