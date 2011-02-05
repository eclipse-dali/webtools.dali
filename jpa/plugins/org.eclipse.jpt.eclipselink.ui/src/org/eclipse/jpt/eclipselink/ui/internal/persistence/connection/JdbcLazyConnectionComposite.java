/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * JdbcLazyConnectionComposite
 */
public class JdbcLazyConnectionComposite<T extends Connection> 
	extends Pane<T>
{
	/**
	 * Creates a new <code>JdbcLazyConnectionComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public JdbcLazyConnectionComposite(
								Pane<T> parentComposite,
								Composite parent) {

		super(parentComposite, parent);
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_lazyConnectionLabel,
			this.buildLazyConnectionHolder(),
			this.buildLazyConnectionStringHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildLazyConnectionHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(this.getSubjectHolder(), Connection.LAZY_CONNECTION_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getLazyConnection();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setLazyConnection(value);
			}
		};
	}

	private PropertyValueModel<String> buildLazyConnectionStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(this.buildDefaultLazyConnectionHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlConnectionTab_lazyConnectionLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlConnectionTab_lazyConnectionLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultLazyConnectionHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(
			this.getSubjectHolder(),
			Connection.LAZY_CONNECTION_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getLazyConnection() != null) {
					return null;
				}
				return this.subject.getDefaultLazyConnection();
			}
		};
	}
}
