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
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 *  JdbcReadConnectionPropertiesComposite
 */
public class JdbcReadConnectionPropertiesComposite extends AbstractPane<Connection>
{
	public JdbcReadConnectionPropertiesComposite(AbstractPane<Connection> parentComposite, Composite parent) {

		super(parentComposite, parent, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = this.buildSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_readConnectionsSectionTitle
		);

		GridData data = (GridData) container.getLayoutData();
		data.verticalAlignment = SWT.TOP;

		data = (GridData) getControl().getLayoutData();
		data.verticalAlignment = SWT.TOP;

		// Read Connections Shared
		new JdbcReadConnectionsSharedComposite(this, container);

		// Read Connections Minimum
		new JdbcReadConnectionsMinComposite(this, container);

		// Read Connections Maximum
		new JdbcReadConnectionsMaxComposite(this, container);
	}
}