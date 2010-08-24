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
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.internal.context.EntityTextRangeResolver;
import org.eclipse.jpt.core.utility.TextRange;

public class JavaEntityTextRangeResolver
	implements EntityTextRangeResolver
{
	private JavaEntity entity;
	
	private CompilationUnit astRoot;
	
	
	public JavaEntityTextRangeResolver(JavaEntity entity, CompilationUnit astRoot) {
		this.entity = entity;
		this.astRoot = astRoot;
	}
	
	
	public TextRange getTypeMappingTextRange() {
		return this.entity.getValidationTextRange(this.astRoot);
	}
	
	public TextRange getIdClassTextRange() {
		return this.entity.getIdClassReference().getValidationTextRange(this.astRoot);
	}
	
	public TextRange getAttributeMappingTextRange(String attributeName) {
		return this.entity.getPersistentType().
				getAttributeNamed(attributeName).getMapping().getValidationTextRange(this.astRoot);
	}
}
