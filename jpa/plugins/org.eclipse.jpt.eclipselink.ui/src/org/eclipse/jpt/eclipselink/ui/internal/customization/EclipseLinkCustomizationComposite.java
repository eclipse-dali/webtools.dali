/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.customization;

import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 *  PersistenceUnitCustomizationComposite
 */
public class EclipseLinkCustomizationComposite extends FormPane<Customization>
{
	public EclipseLinkCustomizationComposite(FormPane<Customization> subjectHolder,
	                                       Composite container) {

		super(subjectHolder, container);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		Section section = getWidgetFactory().createSection(parent, SWT.FLAT | ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setText(EclipseLinkUiMessages.PersistenceXmlCustomizationTab_sectionTitle);
		section.setDescription(EclipseLinkUiMessages.PersistenceXmlCustomizationTab_sectionDescription);
		section.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite composite = this.addSubPane(section);
		section.setClient(composite);

		// Default pane
		int groupBoxMargin = this.getGroupBoxMargin();

		Composite defaultPane = this.addSubPane(
			composite,
			0, groupBoxMargin, 0, groupBoxMargin
		);

		// Weaving
		new WeavingComposite(this, defaultPane);

		// Weaving Lazy
		new WeavingLazyComposite(this, defaultPane);

		// Weaving Fetch Groups
		new WeavingFetchGroupsComposite(this, defaultPane);

		// Weaving Change Tracking
		new WeavingChangeTrackingComposite(this, defaultPane);

		// Throw Exceptions
		new ThrowExceptionsComposite(this, defaultPane);

		// Session Customizer
		new SessionCustomizerComposite(this, defaultPane);

		// EntitiesList
		new EntityListComposite(this, composite);
	}
}