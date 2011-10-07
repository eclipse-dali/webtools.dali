/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence.customization;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization.EclipseLinkCustomizationComposite;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLinkCustomization2_0Composite
 */
public class EclipseLinkCustomization2_0Composite extends EclipseLinkCustomizationComposite<Customization>
{
	public EclipseLinkCustomization2_0Composite(
		Pane<Customization> subjectHolder, 
		Composite container) {
		super(subjectHolder, container);
	}

	@Override
	protected void buildEntityListComposite(Composite parent) {
		// do nothing
	}
}
