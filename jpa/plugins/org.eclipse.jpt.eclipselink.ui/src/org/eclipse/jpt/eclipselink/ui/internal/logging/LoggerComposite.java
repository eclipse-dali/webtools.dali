/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.logging;

import org.eclipse.jpt.eclipselink.core.internal.context.logging.Logging;
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
 *  LoggerComposite
 */
public class LoggerComposite extends AbstractPane<Logging>
{
	/**
	 * Creates a new <code>LoggerComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public LoggerComposite(
								AbstractPane<? extends Logging> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	private PropertyValueModel<String> buildDefaultLoggerHolder() {
		return new PropertyAspectAdapter<Logging, String>(this.getSubjectHolder(), Logging.DEFAULT_LOGGER) {
			@Override
			protected String buildValue_() {
				return LoggerComposite.this.defaultValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultLoggerListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultLoggerHolder()
		);
	}

	private WritablePropertyValueModel<String> buildLoggerHolder() {
		return new PropertyAspectAdapter<Logging, String>(this.getSubjectHolder(), Logging.LOGGER_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getLogger();
				if (name == null) {
					name = LoggerComposite.this.defaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (defaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setLogger(value);
			}
		};
	}

	private String defaultValue(Logging subject) {
		String defaultValue = subject.getDefaultLogger();

		if (defaultValue != null) {
			return NLS.bind(
				EclipseLinkUiMessages.PersistenceXmlLoggingTab_defaultWithOneParam,
				defaultValue
			);
		}
		else {
			return EclipseLinkUiMessages.PersistenceXmlLoggingTab_defaultEmpty;
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		CCombo combo = buildLabeledEditableCCombo(
			container,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_loggerLabel,
			this.buildDefaultLoggerListHolder(),
			this.buildLoggerHolder(),
			null		// EclipseLinkHelpContextIds.LOGGER_NAME
		);
		combo.add(EclipseLinkUiMessages.LoggerComposite_default_logger, 1);
		combo.add(EclipseLinkUiMessages.LoggerComposite_java_logger, 2);
		combo.add(EclipseLinkUiMessages.LoggerComposite_server_logger, 3);
		
		SWTUtil.attachDefaultValueHandler(combo);
	}
}
