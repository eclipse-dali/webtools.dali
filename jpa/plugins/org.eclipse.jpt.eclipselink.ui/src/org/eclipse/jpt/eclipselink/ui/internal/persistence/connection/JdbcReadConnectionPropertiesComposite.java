/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 *  JdbcReadConnectionPropertiesComposite
 */
public class JdbcReadConnectionPropertiesComposite<T extends Connection> 
	extends Pane<T>
{
	public JdbcReadConnectionPropertiesComposite(
					Pane<T> parentComposite, 
					Composite parent) {

		super(parentComposite, parent, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = this.addSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_readConnectionsSectionTitle
		);

		GridData data = (GridData) container.getLayoutData();
		data.verticalAlignment = SWT.TOP;

		data = (GridData) getControl().getLayoutData();
		data.verticalAlignment = SWT.TOP;

		// Read Connections Shared
		new JdbcReadConnectionsSharedComposite<T>(this, container);

		// Read Connections Minimum
		new JdbcReadConnectionsMinComposite<T>(this, container);

		// Read Connections Maximum
		new JdbcReadConnectionsMaxComposite<T>(this, container);
	}
}