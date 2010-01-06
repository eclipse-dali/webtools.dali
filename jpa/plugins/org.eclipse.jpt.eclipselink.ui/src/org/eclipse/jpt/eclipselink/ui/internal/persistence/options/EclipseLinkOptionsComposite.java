/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.options;

import org.eclipse.jpt.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * EclipseLinkOptionsComposite
 */
public class EclipseLinkOptionsComposite<T extends Options> extends Pane<T>
{
	public EclipseLinkOptionsComposite(
					Pane<T> subjectHolder, 
					Composite container) {

		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		this.initializeSessionOptionsPane(parent);
		this.initializeMiscellaneousPane(parent);
	}
	
	private void initializeSessionOptionsPane(Composite parent) {
		Composite composite = this.addSection(parent, 
				EclipseLinkUiMessages.PersistenceXmlOptionsTab_sessionSectionTitle,
				EclipseLinkUiMessages.PersistenceXmlOptionsTab_sessionSectionDescription);
		
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
	
	protected Composite initializeMiscellaneousPane(Composite container) {	
		
		this.updateGridData(container);
		this.updateGridData(container.getParent());
		
		Composite composite = this.addSection(container, 
				EclipseLinkUiMessages.PersistenceXmlOptionsTab_miscellaneousSectionTitle,
				EclipseLinkUiMessages.PersistenceXmlOptionsTab_miscellaneousSectionDescription);
		
		this.updateGridData(composite);
		this.updateGridData(composite.getParent());

		new TemporalMutableComposite(this, composite);
		
		return composite;
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
