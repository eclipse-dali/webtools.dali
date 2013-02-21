/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context;

import org.eclipse.jpt.jpa.core.internal.jpa2_1.Generic2_1JpaPlatformFactory;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaProject2_1;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

public abstract class Generic2_1ContextModelTestCase
	extends ContextModelTestCase
{
	protected Generic2_1ContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected String getJpaPlatformID() {
		return Generic2_1JpaPlatformFactory.ID;
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject2_1.FACET_VERSION_STRING;
	}
}
