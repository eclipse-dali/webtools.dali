/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableHolder2_0;

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
	

	// ********** misc **********

	@Override
	public JavaCacheableHolder2_0 getParent() {
		return (JavaCacheableHolder2_0) super.getParent();
	}

	protected JavaCacheableHolder2_0 getCacheableHolder() {
		return this.getParent();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getCacheableHolder().getValidationTextRange();
	}
}
