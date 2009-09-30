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

import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 *  JdbcWriteConnectionPropertiesComposite
 */
@SuppressWarnings("nls")
public class JdbcWriteConnectionPropertiesComposite<T extends Connection> 
	extends Pane<T>
{
	public JdbcWriteConnectionPropertiesComposite(
							Pane<T> parentComposite, 
							Composite parent) {

		super(parentComposite, parent, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = this.addSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_writeConnectionsSectionTitle
		);

		GridData data = (GridData) container.getLayoutData();
		data.verticalAlignment = SWT.TOP;

		data = (GridData) getControl().getLayoutData();
		data.verticalAlignment = SWT.TOP;

		// This will add space to have the same layout than read connection pool
		Button space = this.getWidgetFactory().createCheckBox(container, "m");
		Point size = space.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		container = this.addSubPane(container, size.y + 5);
		space.dispose();

		// Write Connections Minimum
		new JdbcWriteConnectionsMinComposite<T>(this, container);

		// Write Connections Maximum
		new JdbcWriteConnectionsMaxComposite<T>(this, container);
	}
}
