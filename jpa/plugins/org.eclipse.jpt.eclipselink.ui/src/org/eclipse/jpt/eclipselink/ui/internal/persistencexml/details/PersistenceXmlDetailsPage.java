/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistencexml.details;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkJpaProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkProperties;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AbstractJpaDetailsPage;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * PersistenceXmlDetailsPage
 */
public class PersistenceXmlDetailsPage extends AbstractJpaDetailsPage<PersistenceUnit>
{
	private EclipseLinkProperties persistenceUnit;

	public PersistenceXmlDetailsPage(Composite parent, WidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}

	protected void initialize() {
		super.initialize();
		this.persistenceUnit = new EclipseLinkJpaProperties(this.subject());
	}

	@Override
	protected void initializeLayout(Composite composite) {
		composite.setLayout(new FillLayout());
	}
}
