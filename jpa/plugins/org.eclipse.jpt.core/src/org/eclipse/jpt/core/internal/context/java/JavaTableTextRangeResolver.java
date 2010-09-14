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
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaBaseTable;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.utility.TextRange;

public class JavaTableTextRangeResolver
	implements TableTextRangeResolver
{
	protected final JavaBaseTable javaTable;

	protected final CompilationUnit astRoot;

	public JavaTableTextRangeResolver(JavaBaseTable javaTable, CompilationUnit astRoot) {
		this.javaTable = javaTable;
		this.astRoot = astRoot;
	}

	protected JavaBaseTable getTable() {
		return this.javaTable;
	}

	public TextRange getNameTextRange() {
		return this.javaTable.getNameTextRange(this.astRoot);
	}

	public TextRange getCatalogTextRange() {
		return this.javaTable.getCatalogTextRange(this.astRoot);
	}

	public TextRange getSchemaTextRange() {
		return this.javaTable.getSchemaTextRange(this.astRoot);
	}
}
