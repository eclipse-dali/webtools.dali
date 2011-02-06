/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedColumn;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;

public class JavaNamedColumnTextRangeResolver
	implements NamedColumnTextRangeResolver
{
	protected final JavaNamedColumn javaNamedColumn;

	protected final CompilationUnit astRoot;

	public JavaNamedColumnTextRangeResolver(JavaNamedColumn javaNamedColumn, CompilationUnit astRoot) {
		this.javaNamedColumn = javaNamedColumn;
		this.astRoot = astRoot;
	}

	protected JavaNamedColumn getColumn() {
		return this.javaNamedColumn;
	}

	public TextRange getNameTextRange() {
		return this.javaNamedColumn.getNameTextRange(this.astRoot);
	}
}
