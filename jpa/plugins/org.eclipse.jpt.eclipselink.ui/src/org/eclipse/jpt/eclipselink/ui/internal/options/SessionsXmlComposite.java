/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.options;

import org.eclipse.jpt.eclipselink.core.internal.context.options.Options;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;

/**
 *  SessionsXmlComposite
 */
public class SessionsXmlComposite extends AbstractPane<Options>
{
	/**
	 * Creates a new <code>SessionsXmlComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public SessionsXmlComposite(
								AbstractPane<? extends Options> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	private PropertyValueModel<String> buildDefaultSessionsXmlFileNameHolder() {
		return new PropertyAspectAdapter<Options, String>(this.getSubjectHolder(), Options.DEFAULT_SESSIONS_XML) {
			@Override
			protected String buildValue_() {
				return SessionsXmlComposite.this.defaultValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultSessionsXmlFileNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultSessionsXmlFileNameHolder()
		);
	}

	private WritablePropertyValueModel<String> buildSessionsXmlFileNameHolder() {
		return new PropertyAspectAdapter<Options, String>(this.getSubjectHolder(), Options.SESSIONS_XML_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getSessionsXml();
				if (name == null) {
					name = SessionsXmlComposite.this.defaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (defaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setSessionsXml(value);
			}
		};
	}

	private String defaultValue(Options subject) {
		String defaultValue = subject.getDefaultSessionsXml();

		if (defaultValue != null) {
			return NLS.bind(
				EclipseLinkUiMessages.PersistenceXmlOptionsTab_defaultWithOneParam,
				defaultValue
			);
		}
		else {
			return EclipseLinkUiMessages.PersistenceXmlOptionsTab_defaultEmpty;
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		CCombo combo = buildLabeledEditableCCombo(
			container,
			EclipseLinkUiMessages.PersistenceXmlOptionsTab_sessionsXml,
			this.buildDefaultSessionsXmlFileNameListHolder(),
			this.buildSessionsXmlFileNameHolder(),
			null		// EclipseLinkHelpContextIds.
		);
		SWTUtil.attachDefaultValueHandler(combo);
	}
}
