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
import org.eclipse.jpt.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * JdbcReadConnectionsMaxComposite
 */
public class JdbcReadConnectionsMaxComposite extends Pane<Connection>
{
	/**
	 * Creates a new <code>JdbcReadConnectionsMaxComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public JdbcReadConnectionsMaxComposite(Pane<Connection> parentComposite,
	                                 Composite parent) {

		super(parentComposite, parent);
	}


	@Override
	protected void initializeLayout(Composite container) {
		addReadConnectionsMaxCombo(container);
	}
	
	private void addReadConnectionsMaxCombo(Composite container) {
		new IntegerCombo<Connection>(this, container) {
			
			@Override
			protected String getLabelText() {
				return EclipseLinkUiMessages.PersistenceXmlConnectionTab_readConnectionsMaxLabel;
			}
		
			@Override
			protected String getHelpId() {
				return null;
			}
			
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultReadConnectionsMax();
					}
				};
			}
			
			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder(), Connection.READ_CONNECTIONS_MAX_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getReadConnectionsMax();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setReadConnectionsMax(value);
					}
				};
			}
		};
	}
}