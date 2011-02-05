/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.logging;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.logging.Logging2_0;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 *  ConnectionComposite
 */
public class ConnectionComposite extends Pane<Logging2_0>
{
	/**
	 * Creates a new <code>ConnectionComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public ConnectionComposite(
					Pane<? extends Logging2_0> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLabel,
			this.buildConnectionHolder(),
			this.buildConnectionStringHolder(),
			null
//			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_CONNECTION	// TODO
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildConnectionHolder() {
		return new PropertyAspectAdapter<Logging2_0, Boolean>(getSubjectHolder(), Logging2_0.CONNECTION_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getConnection();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setConnection(value);
			}
		};
	}

	private PropertyValueModel<String> buildConnectionStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(this.buildDefaultConnectionHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultConnectionHolder() {
		return new PropertyAspectAdapter<Logging2_0, Boolean>(
			getSubjectHolder(),
			Logging2_0.CONNECTION_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getConnection() != null) {
					return null;
				}
				return this.subject.getDefaultConnection();
			}
		};
	}

}
