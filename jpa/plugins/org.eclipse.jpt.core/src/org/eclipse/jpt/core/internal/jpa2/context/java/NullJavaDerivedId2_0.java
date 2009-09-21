/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.utility.TextRange;

public class NullJavaDerivedId2_0
	extends AbstractJavaJpaContextNode
	implements JavaDerivedId2_0
{
	
	public NullJavaDerivedId2_0(JavaSingleRelationshipMapping2_0 parent) {
		super(parent);
	}
		
	public boolean getValue() {
		return false;
	}
	
	public void setValue(boolean newValue) {
		throw new UnsupportedOperationException();
	}

	
	public void initialize() {
		//no-op
	}
	
	public void update() {
		//no-op
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}
}
