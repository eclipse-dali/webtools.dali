/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.tests.internal.resource.java;

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.tests.internal.resource.java.JavaResourceModelTestCase;
import org.eclipse.jpt2_0.core.internal.platform.Generic2_0JpaAnnotationDefinitionProvider;

public class Generic2_0JavaResourceModelTestCase extends JavaResourceModelTestCase
{	
	
	public Generic2_0JavaResourceModelTestCase(String name) {
		super(name);
	}

	@Override
	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new GenericJpaAnnotationProvider(
			GenericJpaAnnotationDefinitionProvider.instance(),
			Generic2_0JpaAnnotationDefinitionProvider.instance());
	}
}
