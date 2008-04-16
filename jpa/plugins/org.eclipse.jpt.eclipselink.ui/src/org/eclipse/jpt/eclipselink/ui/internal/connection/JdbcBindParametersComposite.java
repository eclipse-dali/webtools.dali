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
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * JdbcBindParametersComposite
 */
public class JdbcBindParametersComposite extends AbstractPane<Connection>
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
					AbstractPane<? extends Connection> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<Boolean> buildBindParametersHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(getSubjectHolder(), Connection.BIND_PARAMETERS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getBindParameters();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setBindParameters(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildBindParametersStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildBindParametersHolder()) {
			@Override
			protected String transform(Boolean value) {
				if ((subject() != null) && (value == null)) {
					Boolean defaultValue = subject().getDefaultBindParameters();
					if (defaultValue != null) {
						String defaultStringValue = defaultValue ? EclipseLinkUiMessages.Boolean_True : EclipseLinkUiMessages.Boolean_False;
						return NLS.bind(EclipseLinkUiMessages.PersistenceXmlConnectionTab_bindParametersLabelDefault, defaultStringValue);
					}
				}
				return EclipseLinkUiMessages.PersistenceXmlConnectionTab_bindParametersLabel;
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.buildTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_bindParametersLabel,
			this.buildBindParametersHolder(),
			this.buildBindParametersStringHolder(),
			null
//			EclipseLinkHelpContextIds.
		);
	}
}
