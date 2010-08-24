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
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.core.utility.TextRange;

public class JavaPersistentAttributeTextRangeResolver
	implements PersistentAttributeTextRangeResolver
{
	private JavaPersistentAttribute javaPersistentAttribute;

	private CompilationUnit astRoot;

	public JavaPersistentAttributeTextRangeResolver(JavaPersistentAttribute javaPersistentAttribute, CompilationUnit astRoot) {
		this.javaPersistentAttribute = javaPersistentAttribute;
		this.astRoot = astRoot;
	}

	public TextRange getAttributeTextRange() {
		return this.javaPersistentAttribute.getValidationTextRange(this.astRoot);
	}
}
