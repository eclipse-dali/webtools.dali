/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.options;

import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.options.EclipseLinkOptionsComposite;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLinkOptions2_0Composite
 */
public class EclipseLinkOptions2_0Composite extends EclipseLinkOptionsComposite<Options2_0>
{
	public EclipseLinkOptions2_0Composite(
			FormPane<Options2_0> subjectHolder, 
			Composite container) {
		super(subjectHolder, container);
	}

	@Override
	protected Composite initializeMiscellaneousPane(Composite container) {	
		Composite composite = super.initializeMiscellaneousPane(container);

		new LockingConfigurationComposite(this, composite);
		new QueryConfigurationComposite(this, composite);
		new ValidationConfigurationComposite(this, composite);
		return composite;
	}

}
