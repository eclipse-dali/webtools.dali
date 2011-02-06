/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * ReadConnectionsSharedComposite
 */
public class JdbcReadConnectionsSharedComposite<T extends Connection> 
	extends Pane<T>
{
	/**
	 * Creates a new <code>ReadConnectionsSharedComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public JdbcReadConnectionsSharedComposite(
								Pane<T> parentComposite,
								Composite parent) {

		super(parentComposite, parent);
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_readConnectionsSharedLabel,
			this.buildReadConnectionsSharedHolder(),
			this.buildReadConnectionsSharedStringHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildReadConnectionsSharedHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(getSubjectHolder(), Connection.READ_CONNECTIONS_SHARED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getReadConnectionsShared();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setReadConnectionsShared(value);
			}
		};
	}

	private PropertyValueModel<String> buildReadConnectionsSharedStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultReadConnectionsSharedHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlConnectionTab_readConnectionsSharedLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlConnectionTab_readConnectionsSharedLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultReadConnectionsSharedHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(
			getSubjectHolder(),
			Connection.READ_CONNECTIONS_SHARED_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getReadConnectionsShared() != null) {
					return null;
				}
				return this.subject.getDefaultReadConnectionsShared();
			}
		};
	}
}
