/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.logging;

import org.eclipse.jpt.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * TimestampComposite
 */
public class TimestampComposite extends FormPane<Logging>
{
	/**
	 * Creates a new <code>TimestampComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public TimestampComposite(
					FormPane<? extends Logging> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_timestampLabel,
			this.buildTimestampHolder(),
			this.buildTimestampStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_TIMESTAMP
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildTimestampHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(getSubjectHolder(), Logging.TIMESTAMP_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getTimestamp();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setTimestamp(value);
			}
		};
	}

	private PropertyValueModel<String> buildTimestampStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultTimestampHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? EclipseLinkUiMessages.Boolean_True : EclipseLinkUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlLoggingTab_timestampLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlLoggingTab_timestampLabel;
			}
		};
	}
	private PropertyValueModel<Boolean> buildDefaultTimestampHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(
			getSubjectHolder(),
			Logging.TIMESTAMP_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getTimestamp() != null) {
					return null;
				}
				return this.subject.getDefaultTimestamp();
			}
		};
	}
}
