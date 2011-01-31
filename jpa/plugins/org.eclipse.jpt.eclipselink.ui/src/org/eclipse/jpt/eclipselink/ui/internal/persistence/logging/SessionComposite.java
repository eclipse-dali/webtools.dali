/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.logging;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * SessionComposite
 */
public class SessionComposite extends Pane<Logging>
{
	/**
	 * Creates a new <code>SessionComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public SessionComposite(
					Pane<? extends Logging> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_sessionLabel,
			this.buildSessionHolder(),
			this.buildSessionStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_SESSION
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildSessionHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(getSubjectHolder(), Logging.SESSION_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSession();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSession(value);
			}
		};
	}

	private PropertyValueModel<String> buildSessionStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultSessionHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlLoggingTab_sessionLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlLoggingTab_sessionLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultSessionHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(
			getSubjectHolder(),
			Logging.SESSION_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSession() != null) {
					return null;
				}
				return this.subject.getDefaultSession();
			}
		};
	}

}
