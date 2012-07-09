/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options;

import java.util.Collection;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.FileChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Logging;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.LoggingLevel;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * EclipseLinkLoggingComposite
 */
public class EclipseLinkLoggingComposite<T extends Logging>
	extends Pane<T>
{
	public EclipseLinkLoggingComposite(
				PropertyValueModel<T> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// LoggingLevel:
		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_loggingLevelLabel);
		this.addLoggingLevelCombo(container);

		this.logPropertiesComposite(container);

		// LoggingFile:
		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_loggingFileLabel);
		this.addLogFileLocationComposite(container);
		
		// Logger:
		Hyperlink loggerHyperlink = this.addHyperlink(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_loggerLabel);
		new LoggerClassChooser(this, container, loggerHyperlink);
	}

	protected void logPropertiesComposite(Composite parent) {
		// Timestamp:
		TriStateCheckBox timestampCheckBox = this.addTriStateCheckBoxWithDefault(
			parent,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_timestampLabel,
			this.buildTimestampHolder(),
			this.buildTimestampStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_TIMESTAMP
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		timestampCheckBox.getCheckBox().setLayoutData(gridData);

		// Thread:
		TriStateCheckBox threadCheckBox = this.addTriStateCheckBoxWithDefault(
			parent,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_threadLabel,
			this.buildThreadHolder(),
			this.buildThreadStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_THREAD
		);
		threadCheckBox.getCheckBox().setLayoutData(gridData);

		// Session:
		TriStateCheckBox sessionCheckBox = this.addTriStateCheckBoxWithDefault(
			parent,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_sessionLabel,
			this.buildSessionHolder(),
			this.buildSessionStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_SESSION
		);
		sessionCheckBox.getCheckBox().setLayoutData(gridData);

		// Exceptions:
		TriStateCheckBox exceptionsCheckBox = this.addTriStateCheckBoxWithDefault(
			parent,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_exceptionsLabel,
			this.buildExceptionsHolder(),
			this.buildExceptionsStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_EXCEPTIONS
		);
		exceptionsCheckBox.getCheckBox().setLayoutData(gridData);
	}

	//************* logging level ************

	private EnumFormComboViewer<Logging, LoggingLevel> addLoggingLevelCombo(Composite container) {
		return new EnumFormComboViewer<Logging, LoggingLevel>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Logging.LEVEL_PROPERTY);
			}

			@Override
			protected LoggingLevel[] getChoices() {
				return LoggingLevel.values();
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
			
			@Override
			protected LoggingLevel getDefaultValue() {
				return this.getSubject().getDefaultLevel();
			}

			@Override
			protected String displayString(LoggingLevel value) {
				switch (value) {
					case all :
						return EclipseLinkUiMessages.LoggingLevelComposite_all;
					case config :
						return EclipseLinkUiMessages.LoggingLevelComposite_config;
					case fine :
						return EclipseLinkUiMessages.LoggingLevelComposite_fine;
					case finer :
						return EclipseLinkUiMessages.LoggingLevelComposite_finer;
					case finest :
						return EclipseLinkUiMessages.LoggingLevelComposite_finest;
					case info :
						return EclipseLinkUiMessages.LoggingLevelComposite_info;
					case off :
						return EclipseLinkUiMessages.LoggingLevelComposite_off;
					case severe :
						return EclipseLinkUiMessages.LoggingLevelComposite_severe;
					case warning :
						return EclipseLinkUiMessages.LoggingLevelComposite_warning;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected LoggingLevel getValue() {
				return this.getSubject().getLevel();
			}

			@Override
			protected void setValue(LoggingLevel value) {
				this.getSubject().setLevel(value);
			}
			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_LEVEL;
			}
		};
		
	}

	//************* logging level ************

	private FileChooserComboPane<Logging> addLogFileLocationComposite(Composite parent) {
		return new FileChooserComboPane<Logging>(this, parent) {
	
			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
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
						JptCommonUiMessages.DefaultWithOneParam,
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
			protected String getProjectPath() {
				return this.getSubject().getJpaProject().getProject().getLocation().toString();
			}
		};
	}


	//************* timestamp ************
	
	private ModifiablePropertyValueModel<Boolean> buildTimestampHolder() {
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
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
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


	//************* thread ************
	
	private ModifiablePropertyValueModel<Boolean> buildThreadHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(getSubjectHolder(), Logging.THREAD_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getThread();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setThread(value);
			}
		};
	}

	private PropertyValueModel<String> buildThreadStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultThreadHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlLoggingTab_threadLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlLoggingTab_threadLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultThreadHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(
			getSubjectHolder(),
			Logging.THREAD_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getThread() != null) {
					return null;
				}
				return this.subject.getDefaultThread();
			}
		};
	}


	//************* session ************

	private ModifiablePropertyValueModel<Boolean> buildSessionHolder() {
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


	//********** exceptions ************
	
	private ModifiablePropertyValueModel<Boolean> buildExceptionsHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(getSubjectHolder(), Logging.EXCEPTIONS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getExceptions();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setExceptions(value);
			}
		};
	}

	private PropertyValueModel<String> buildExceptionsStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultExceptionsHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlLoggingTab_exceptionsLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlLoggingTab_exceptionsLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultExceptionsHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(
			getSubjectHolder(),
			Logging.EXCEPTIONS_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getExceptions() != null) {
					return null;
				}
				return this.subject.getDefaultExceptions();
			}
		};
	}
}
