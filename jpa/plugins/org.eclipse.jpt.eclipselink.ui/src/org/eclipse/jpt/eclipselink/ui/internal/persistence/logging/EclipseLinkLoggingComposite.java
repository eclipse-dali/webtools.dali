/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.logging;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * EclipseLinkLoggingComposite
 */
public class EclipseLinkLoggingComposite<T extends Logging>
	extends Pane<T>
{
	public EclipseLinkLoggingComposite(
					Pane<T> subjectHolder, 
					Composite container) {
		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		Section section = getWidgetFactory().createSection(parent, SWT.FLAT | ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setText(EclipseLinkUiMessages.PersistenceXmlLoggingTab_sectionTitle);
		section.setDescription(EclipseLinkUiMessages.PersistenceXmlLoggingTab_sectionDescription);
		Composite composite = getWidgetFactory().createComposite(section);
		composite.setLayout(new GridLayout(1, false));
		section.setClient(composite);
		this.updateGridData(composite);
		this.updateGridData(composite.getParent());
		
		// LoggingLevel:
		new LoggingLevelComposite(this, composite);

		// Boolean properties group
		this.logPropertiesComposite(composite);

		// LoggingFile:
		new LoggingFileLocationComposite(this, composite);
		
		// Logger:
		new LoggerComposite(this, composite);
		
		return;
	}
	
	protected void logPropertiesComposite(Composite parent) {

		// Timestamp:
		new TimestampComposite(this, parent);

		// Thread:
		new ThreadComposite(this, parent);

		// Session:
		new SessionComposite(this, parent);

		// Exceptions:
		new ExceptionsComposite(this, parent);
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
