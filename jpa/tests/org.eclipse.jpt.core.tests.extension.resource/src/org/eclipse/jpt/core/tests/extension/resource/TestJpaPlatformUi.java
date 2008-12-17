/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.ui.internal.platform.generic.GenericNavigatorProvider;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;

public class TestJpaPlatformUi extends BaseJpaPlatformUi
{
	public TestJpaPlatformUi() {
		super();
	}

	public JpaNavigatorProvider buildNavigatorProvider() {
		return new GenericNavigatorProvider();
	}

	@Override
	protected JpaUiFactory buildJpaUiFactory() {
		return new TestJpaUiFactory();
	}

	public void generateDDL(JpaProject project, IStructuredSelection selection) {
		throw new UnsupportedOperationException();
	}

}
