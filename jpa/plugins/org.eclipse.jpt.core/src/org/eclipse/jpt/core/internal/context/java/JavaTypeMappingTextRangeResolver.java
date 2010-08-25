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
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.internal.context.TypeMappingTextRangeResolver;
import org.eclipse.jpt.core.utility.TextRange;

public class JavaTypeMappingTextRangeResolver
	implements TypeMappingTextRangeResolver
{
	private JavaTypeMapping typeMapping;

	private CompilationUnit astRoot;

	public JavaTypeMappingTextRangeResolver(JavaTypeMapping typeMapping, CompilationUnit astRoot) {
		this.typeMapping = typeMapping;
		this.astRoot = astRoot;
	}

	public TextRange getTypeMappingTextRange() {
		return this.typeMapping.getValidationTextRange(this.astRoot);
	}
}
