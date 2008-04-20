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
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  JdbcConnectionPropertiesComposite
 */
public class JdbcConnectionPropertiesComposite extends AbstractPane<Connection>
{
	public JdbcConnectionPropertiesComposite(AbstractPane<Connection> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Driver
		new JdbcDriverComposite(this, container);

		// Url
		this.buildLabeledText(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_urlLabel,
			buildUrlHolder()
		);

		// User
		this.buildLabeledText(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_userLabel,
			buildUserHolder()
		);

		// Password
		this.buildLabeledPasswordText(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_passwordLabel,
			buildPasswordHolder()
		);

		// Bind Parameters
		new JdbcBindParametersComposite(this, container);


	}

	private WritablePropertyValueModel<String> buildUrlHolder() {
		return new PropertyAspectAdapter<Connection, String>(getSubjectHolder(), Connection.URL_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getUrl();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setUrl(value);
			}
		};
	}

	private WritablePropertyValueModel<String> buildUserHolder() {
		return new PropertyAspectAdapter<Connection, String>(getSubjectHolder(), Connection.USER_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getUser();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setUser(value);
			}
		};
	}

	private WritablePropertyValueModel<String> buildPasswordHolder() {
		return new PropertyAspectAdapter<Connection, String>(getSubjectHolder(), Connection.PASSWORD_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getPassword();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setPassword(value);
			}
		};
	}

}