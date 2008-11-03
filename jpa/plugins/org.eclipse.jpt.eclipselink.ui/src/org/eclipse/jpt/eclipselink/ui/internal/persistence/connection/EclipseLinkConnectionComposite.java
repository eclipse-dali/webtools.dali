/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.widgets.Composite;

/**
 * EclipseLinkConnectionComposite
 */
public class EclipseLinkConnectionComposite
	extends FormPane<Connection>
{
	public EclipseLinkConnectionComposite(
					FormPane<Connection> subjectHolder,
					Composite container) {
		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = getGroupBoxMargin() * 2;

		container = addSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_sectionTitle,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_sectionDescription
		);

		Composite subPane = addSubPane(
			container,
			0, groupBoxMargin, 10, groupBoxMargin
		);

		new TransactionTypeComposite(this, subPane);

		new BatchWritingComposite(this, subPane);

		new CacheStatementsPropertiesComposite(this, subPane);

		new NativeSqlComposite(this, subPane);

		new ConnectionPropertiesComposite(this, container);
	}
}