/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableReference2_0;

/**
 * null Java cacheable
 */
public class NullJavaCacheable2_0
	extends AbstractJavaContextModel<JavaCacheableReference2_0>
	implements Cacheable2_0
{
	public NullJavaCacheable2_0(JavaCacheableReference2_0 parent) {
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

	protected JavaCacheableReference2_0 getCacheableReference() {
		return this.parent;
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getCacheableReference().getValidationTextRange();
	}
}
