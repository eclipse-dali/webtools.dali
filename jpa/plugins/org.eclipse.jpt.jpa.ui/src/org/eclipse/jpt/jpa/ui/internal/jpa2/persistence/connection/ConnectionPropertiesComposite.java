/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.connection;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.JptUiPersistence2_0Messages;
import org.eclipse.swt.widgets.Composite;

/**
 *  ConnectionPropertiesComposite
 */
public class ConnectionPropertiesComposite extends Pane<JpaConnection2_0>
{
	public ConnectionPropertiesComposite(Pane<JpaConnection2_0> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = addTitledGroup(
			container,
			JptUiPersistence2_0Messages.ConnectionPropertiesComposite_Database_GroupBox
		);

		new DataSourcePropertiesComposite(this, container);
		new JdbcPropertiesComposite(this, container);
	}
}
