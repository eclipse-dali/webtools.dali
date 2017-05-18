/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheableReference2_0;

/**
 * null <code>orm.xml</code> cacheable
 */
public class NullOrmCacheable2_0
	extends AbstractOrmXmlContextModel<OrmCacheableReference2_0>
	implements Cacheable2_0
{
	public NullOrmCacheable2_0(OrmCacheableReference2_0 parent) {
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

	public boolean getDefaultCacheable() {
		return false;
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.parent.getValidationTextRange();
	}
}
