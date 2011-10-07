/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Connection;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.widgets.Composite;

/**
 * JdbcReadConnectionsMinComposite
 */
public class JdbcReadConnectionsMinComposite<T extends Connection> 
	extends Pane<T>
{
	/**
	 * Creates a new <code>JdbcReadConnectionsMinComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public JdbcReadConnectionsMinComposite(
							Pane<T> parentComposite,
							Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		addReadConnectionsMinCombo(container);
	}
	
	private void addReadConnectionsMinCombo(Composite container) {
		new IntegerCombo<Connection>(this, container) {
			
			@Override
			protected String getLabelText() {
				return EclipseLinkUiMessages.PersistenceXmlConnectionTab_readConnectionsMinLabel;
			}
		
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}
			
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultReadConnectionsMin();
					}
				};
			}
			
			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder(), Connection.READ_CONNECTIONS_MIN_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getReadConnectionsMin();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setReadConnectionsMin(value);
					}
				};
			}
		};
	}
}