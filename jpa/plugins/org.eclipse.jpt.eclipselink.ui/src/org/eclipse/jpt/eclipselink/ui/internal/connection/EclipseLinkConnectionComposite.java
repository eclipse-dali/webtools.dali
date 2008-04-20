/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.connection;

import org.eclipse.jpt.eclipselink.core.internal.context.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.swt.widgets.Composite;

/**
 * EclipseLinkConnectionComposite
 */
public class EclipseLinkConnectionComposite
	extends AbstractFormPane<Connection>
{
	public EclipseLinkConnectionComposite(
					AbstractFormPane<Connection> subjectHolder,
					Composite container) {
		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = buildSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_sectionTitle,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_sectionDescription
		);

		new TransactionTypeComposite(this, container);

		new BatchWritingComposite(this, container);

		new CacheStatementsPropertiesComposite(this, container);

		new NativeSqlComposite(this, container);

		new ConnectionPropertiesComposite(this, container);
	}
}