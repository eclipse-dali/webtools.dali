/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.logging;

import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.logging.Logging2_0;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.logging.EclipseLinkLoggingComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLinkLogging2_0Composite
 */
public class EclipseLinkLogging2_0Composite extends EclipseLinkLoggingComposite<Logging2_0>
{

	public EclipseLinkLogging2_0Composite(
									Pane<Logging2_0> subjectHolder, 
									Composite container) {
		super(subjectHolder, container);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		super.initializeLayout(parent);

		Composite categoryLoggingSection = this.addCollapsibleSubSection(
			this.addSubPane(parent, 0, 16),
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_categoryLoggingLevelSectionTitle,
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE)
		);

		this.addCategoryLoggingLevelComposite(categoryLoggingSection);
	}
	
	protected void addCategoryLoggingLevelComposite(Composite parent) {
		new EclipseLinkCategoryLoggingLevelComposite(this, parent);
	}

	@Override
	protected void logPropertiesComposite(Composite parent) {
		super.logPropertiesComposite(parent);
		
		// Connection:
		new ConnectionComposite(this, parent);
	}
}
