/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.java.JavaCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaCacheableHolder2_0;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * null Java cacheable
 */
public class NullJavaCacheable2_0
	extends AbstractJavaJpaContextNode
	implements JavaCacheable2_0
{
	public NullJavaCacheable2_0(JavaCacheableHolder2_0 parent) {
		super(parent);
	}
	

	// ********** cacheable **********

	public boolean isCacheable() {
		return false;
	}
	
	public Boolean getSpecifiedCacheable() {
		return null;
	}
	
	public void setSpecifiedCacheable(Boolean cacheable) {
		throw new UnsupportedOperationException();
	}
	
	public boolean isDefaultCacheable() {
		return false;
	}
	

	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}
}
