/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 *  WeavingPropertiesComposite
 */
public class WeavingPropertiesComposite extends Pane<Customization>
{
	public WeavingPropertiesComposite(Pane<? extends Customization> subjectHolder,
	                                       Composite container) {

		super(subjectHolder, container);
	}

	@Override
	protected void initializeLayout(Composite parent) {

		Composite weavingGroup = this.addTitledGroup(
			this.addSubPane(parent, 10),
			EclipseLinkUiMessages.PersistenceXmlCustomizationTab_weavingPropertiesGroupBox
		);

		// Weaving
		new WeavingComposite(this, weavingGroup);

		// Weaving Lazy
		new WeavingLazyComposite(this, weavingGroup);

		// Weaving Fetch Groups
		new WeavingFetchGroupsComposite(this, weavingGroup);

		// Weaving Internal
		new WeavingInternalComposite(this, weavingGroup);

		// Weaving Eager
		new WeavingEagerComposite(this, weavingGroup);

		// Weaving Change Tracking
		new WeavingChangeTrackingComposite(this, weavingGroup);

	}
	
}