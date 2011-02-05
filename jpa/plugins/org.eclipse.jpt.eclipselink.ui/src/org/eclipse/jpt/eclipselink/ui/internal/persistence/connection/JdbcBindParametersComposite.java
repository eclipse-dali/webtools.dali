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
 * JdbcBindParametersComposite
 */
public class JdbcBindParametersComposite extends Pane<Connection>
{
	/**
	 * Creates a new <code>JdbcBindParametersComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public JdbcBindParametersComposite(
					Pane<? extends Connection> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_bindParametersLabel,
			this.buildBindParametersHolder(),
			this.buildBindParametersStringHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildBindParametersHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(getSubjectHolder(), Connection.BIND_PARAMETERS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getBindParameters();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setBindParameters(value);
			}
		};
	}

	private PropertyValueModel<String> buildBindParametersStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultBindParametersHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlConnectionTab_bindParametersLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlConnectionTab_bindParametersLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultBindParametersHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(
			getSubjectHolder(),
			Connection.BIND_PARAMETERS_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getBindParameters() != null) {
					return null;
				}
				return this.subject.getDefaultBindParameters();
			}
		};
	}
}
