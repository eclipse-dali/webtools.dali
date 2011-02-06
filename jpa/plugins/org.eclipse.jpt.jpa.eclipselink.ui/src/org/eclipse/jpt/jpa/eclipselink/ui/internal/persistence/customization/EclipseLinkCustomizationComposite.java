/*******************************************************************************
* Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 *  PersistenceUnitCustomizationComposite
 */
public class EclipseLinkCustomizationComposite<T extends Customization> extends Pane<T>
{
	public EclipseLinkCustomizationComposite(Pane<T> subjectHolder,
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

		// Weaving Group
		new WeavingPropertiesComposite(this, defaultPane);

		// Validation Only
		new ValidationOnlyComposite(this, defaultPane);

		// Mapping Files Validate Schema
		new ValidateSchemaComposite(this, defaultPane);

		// Throw Exceptions
		new ThrowExceptionsComposite(this, defaultPane);

		// Exception Handler
		new ExceptionHandlerComposite(this, defaultPane);

		// Session Customizer
		new SessionCustomizersComposite(this, composite);

		this.buildEntityListComposite(composite);

		// Profiler:
		new ProfilerComposite(this, composite);
	}
	
	protected void buildEntityListComposite(Composite parent) {
		new EntityListComposite(this, parent); 
	}
}