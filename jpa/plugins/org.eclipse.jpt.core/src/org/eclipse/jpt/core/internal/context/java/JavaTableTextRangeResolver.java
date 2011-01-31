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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;

public class JavaTableTextRangeResolver
	implements TableTextRangeResolver
{
	protected final JavaTable javaTable;

	protected final CompilationUnit astRoot;

	public JavaTableTextRangeResolver(JavaTable javaTable, CompilationUnit astRoot) {
		this.javaTable = javaTable;
		this.astRoot = astRoot;
	}

	protected JavaTable getTable() {
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
