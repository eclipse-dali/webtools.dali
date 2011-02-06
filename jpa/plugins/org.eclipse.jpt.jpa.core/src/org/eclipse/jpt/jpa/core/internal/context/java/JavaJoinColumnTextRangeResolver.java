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
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;

public class JavaJoinColumnTextRangeResolver
	extends JavaNamedColumnTextRangeResolver
	implements JoinColumnTextRangeResolver
{
	public JavaJoinColumnTextRangeResolver(JavaJoinColumn column, CompilationUnit astRoot) {
		super(column, astRoot);
	}

	@Override
	protected JavaJoinColumn getColumn() {
		return (JavaJoinColumn) super.getColumn();
	}

	public TextRange getTableTextRange() {
		return this.getColumn().getTableTextRange(this.astRoot);
	}

	public TextRange getReferencedColumnNameTextRange() {
		return this.getColumn().getReferencedColumnNameTextRange(this.astRoot);
	}
}
