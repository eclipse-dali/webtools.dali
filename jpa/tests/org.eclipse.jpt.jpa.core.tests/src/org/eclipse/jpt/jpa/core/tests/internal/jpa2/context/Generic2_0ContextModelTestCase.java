/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context;

import org.eclipse.jpt.jpa.core.internal.jpa2.GenericJpaPlatformFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

public abstract class Generic2_0ContextModelTestCase
	extends ContextModelTestCase
{
	protected Generic2_0ContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected String getJpaPlatformID() {
		return GenericJpaPlatformFactory2_0.ID;
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject2_0.FACET_VERSION_STRING;
	}
}
