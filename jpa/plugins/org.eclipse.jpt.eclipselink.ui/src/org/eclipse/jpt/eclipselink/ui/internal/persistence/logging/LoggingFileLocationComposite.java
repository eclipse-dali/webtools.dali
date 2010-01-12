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

import org.eclipse.jpt.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.FileChooserComboPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 *  LoggingFileLocationComposite 
 */
public class LoggingFileLocationComposite extends Pane<Logging>
{
	public LoggingFileLocationComposite(Pane<? extends Logging> parentPane,
	                                      Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		
		new FileChooserComboPane<Logging>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<Logging, String>(
										getSubjectHolder(), Logging.LOG_FILE_LOCATION_PROPERTY) {
					@Override
					protected String buildValue_() {

						String name = subject.getLogFileLocation();
						if (name == null) {
							name = defaultValue(subject);
						}
						return name;
					}

					@Override
					protected void setValue_(String value) {

						if (defaultValue(subject).equals(value)) {
							value = null;
						}
						subject.setLogFileLocation(value);
					}
				};
			}

			private String defaultValue(Logging subject) {
				String defaultValue = subject.getDefaultLogFileLocation();

				if (defaultValue != null) {
					return NLS.bind(
						EclipseLinkUiMessages.PersistenceXmlLoggingTab_defaultWithOneParam,
						defaultValue
					);
				}
				else {
					return this.getDefaultString();
				}
			}

			@Override
			protected String getDefaultString() {
				return EclipseLinkUiMessages.PersistenceXmlLoggingTab_defaultStdout;
			}

			@Override
			protected String getDialogTitle() {
				return EclipseLinkUiMessages.LoggingFileLocationComposite_dialogTitle;
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiMessages.PersistenceXmlLoggingTab_loggingFileLabel;
			}

			@Override
			protected String getProjectPath() {
				return this.getSubject().getJpaProject().getProject().getLocation().toString();
			}
		};
	}
}
