/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.platform.Generic2_0JpaAnnotationProvider;

public class Java2_0ResourceModelTestCase extends JavaResourceModelTestCase
{

	public Java2_0ResourceModelTestCase(String name) {
		super(name);
	}

	@Override
	protected JpaAnnotationProvider buildAnnotationProvider() {
		return Generic2_0JpaAnnotationProvider.instance();
	}
}
