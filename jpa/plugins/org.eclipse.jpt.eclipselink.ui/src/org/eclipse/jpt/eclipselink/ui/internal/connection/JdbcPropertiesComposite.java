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
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 *  JdbcPropertiesComposite
 */
public class JdbcPropertiesComposite extends AbstractPane<Connection>
{
	public JdbcPropertiesComposite(AbstractPane<Connection> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		new JdbcConnectionPropertiesComposite(this, container);

		container = buildPane(container, new GridLayout(2, true));

		new JdbcReadConnectionPropertiesComposite(this, container);
		new JdbcWriteConnectionPropertiesComposite(this, container);
	}
}