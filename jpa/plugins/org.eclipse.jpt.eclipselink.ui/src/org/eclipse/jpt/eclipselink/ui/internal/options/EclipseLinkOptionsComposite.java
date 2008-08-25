/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.options;

import org.eclipse.jpt.eclipselink.core.internal.context.options.Options;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * EclipseLinkOptionsComposite
 */
public class EclipseLinkOptionsComposite
	extends FormPane<Options>
{
	public EclipseLinkOptionsComposite(
					FormPane<Options> subjectHolder, 
					Composite container) {
		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		Section section = getWidgetFactory().createSection(parent, SWT.FLAT | ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setText(EclipseLinkUiMessages.PersistenceXmlOptionsTab_sectionTitle);
		section.setDescription(EclipseLinkUiMessages.PersistenceXmlOptionsTab_sectionDescription);
		Composite composite = getWidgetFactory().createComposite(section);
		composite.setLayout(new GridLayout(1, false));
		section.setClient(composite);
		this.updateGridData(composite);
		this.updateGridData(composite.getParent());

		new SessionNameComposite(this, composite);

		new SessionsXmlComposite(this, composite);
		
		new TargetDatabaseComposite(this, composite);
		
		new TargetServerComposite(this, composite);
		
		new EventListenerComposite(this, composite);
		
		new IncludeDescriptorQueriesComposite(this, composite);
		
		return;
	}

	private void updateGridData(Composite container) {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		container.setLayoutData(gridData);
	}
}
