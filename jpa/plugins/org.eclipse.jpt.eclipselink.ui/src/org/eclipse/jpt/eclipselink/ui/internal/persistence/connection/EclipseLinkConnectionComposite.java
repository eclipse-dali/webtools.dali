/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 * EclipseLinkConnectionComposite
 */
public class EclipseLinkConnectionComposite<T extends Connection>
	extends Pane<T>
{
	public EclipseLinkConnectionComposite(
					Pane<T> subjectHolder,
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

		new TransactionTypeComposite<T>(this, subPane);

		new BatchWritingComposite<T>(this, subPane);

		new CacheStatementsPropertiesComposite<T>(this, subPane);

		new NativeSqlComposite<T>(this, subPane);

		new ConnectionPropertiesComposite<T>(this, container);
	}
}