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

import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * JdbcWriteConnectionsMaxComposite
 */
public class JdbcWriteConnectionsMaxComposite<T extends Connection> 
	extends Pane<T>
{
	/**
	 * Creates a new <code>JdbcWriteConnectionsMaxComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public JdbcWriteConnectionsMaxComposite(
								Pane<T> parentComposite,
	                            Composite parent) {

		super(parentComposite, parent);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		addWriteConnectionsMaxCombo(container);
	}
	
	private void addWriteConnectionsMaxCombo(Composite container) {
		new IntegerCombo<Connection>(this, container) {
			
			@Override
			protected String getLabelText() {
				return EclipseLinkUiMessages.PersistenceXmlConnectionTab_writeConnectionsMaxLabel;
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
						return this.subject.getDefaultWriteConnectionsMax();
					}
				};
			}
			
			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder(), Connection.WRITE_CONNECTIONS_MAX_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getWriteConnectionsMax();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setWriteConnectionsMax(value);
					}
				};
			}
		};
	}
}