/*******************************************************************************
* Copyright (c) 2008, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 *  ConnectionPropertiesComposite
 */
public class ConnectionPropertiesComposite<T extends Connection> 
	extends Pane<T>
{
	public ConnectionPropertiesComposite(
					Pane<T> parentComposite, 
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = addTitledGroup(
			container,
			EclipseLinkUiMessages.ConnectionPropertiesComposite_Database_GroupBox
		);

		// Data source
		new DataSourcePropertiesComposite<T>(this, container);
		// EclipseLink Connection Pool
		new JdbcPropertiesComposite<T>(this, container);
		// Exclusive Connections
		new JdbcExclusiveConnectionsPropertiesComposite<T>(this, container);
	}
}