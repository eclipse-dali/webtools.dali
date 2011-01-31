/*******************************************************************************
* Copyright (c) 2008, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.options;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 *  SessionsXmlComposite
 */
public class SessionsXmlComposite extends Pane<Options>
{
	/**
	 * Creates a new <code>SessionsXmlComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public SessionsXmlComposite(
								Pane<? extends Options> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	private PropertyValueModel<String> buildDefaultSessionsXmlFileNameHolder() {
		return new PropertyAspectAdapter<Options, String>(this.getSubjectHolder(), Options.DEFAULT_SESSIONS_XML) {
			@Override
			protected String buildValue_() {
				return SessionsXmlComposite.this.getDefaultValue(subject);
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
					name = SessionsXmlComposite.this.getDefaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setSessionsXml(value);
			}
		};
	}

	private String getDefaultValue(Options subject) {
		String defaultValue = subject.getDefaultSessionsXml();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DefaultWithOneParam,
				defaultValue
			);
		}
		return JptCommonUiMessages.DefaultEmpty;
	}

	@Override
	protected void initializeLayout(Composite container) {

		Combo combo = addLabeledEditableCombo(
			container,
			EclipseLinkUiMessages.PersistenceXmlOptionsTab_sessionsXml,
			this.buildDefaultSessionsXmlFileNameListHolder(),
			this.buildSessionsXmlFileNameHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS_SESSIONS_XML
		);
		SWTUtil.attachDefaultValueHandler(combo);
	}
}
