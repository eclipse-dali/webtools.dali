/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options;

import java.util.Collection;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.FileChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLoggingLevel;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelStringTransformer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class EclipseLinkLoggingComposite<T extends EclipseLinkLogging>
	extends Pane<T>
{
	public EclipseLinkLoggingComposite(
			Pane<?> parent,
			PropertyValueModel<T> subjectModel,
			Composite parentComposite) {
		super(parent, subjectModel, parentComposite);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	@SuppressWarnings("unused")
	protected void initializeLayout(Composite container) {
		// LoggingLevel:
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(container);

		this.logPropertiesComposite(container);

		// LoggingFile:
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_LOGGING_FILE_LABEL);
		this.addLogFileLocationComposite(container);
		
		// Logger:
		Hyperlink loggerHyperlink = this.addHyperlink(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_LOGGER_LABEL);
		new EclipseLinkLoggerClassChooser(this, container, loggerHyperlink);
	}

	protected void logPropertiesComposite(Composite parent) {
		// Timestamp:
		TriStateCheckBox timestampCheckBox = this.addTriStateCheckBoxWithDefault(
			parent,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_TIMESTAMP_LABEL,
			this.buildTimestampModel(),
			this.buildTimestampStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_TIMESTAMP
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		timestampCheckBox.getCheckBox().setLayoutData(gridData);

		// Thread:
		TriStateCheckBox threadCheckBox = this.addTriStateCheckBoxWithDefault(
			parent,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_THREAD_LABEL,
			this.buildThreadModel(),
			this.buildThreadStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_THREAD
		);
		threadCheckBox.getCheckBox().setLayoutData(gridData);

		// Session:
		TriStateCheckBox sessionCheckBox = this.addTriStateCheckBoxWithDefault(
			parent,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_SESSION_LABEL,
			this.buildSessionModel(),
			this.buildSessionStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_SESSION
		);
		sessionCheckBox.getCheckBox().setLayoutData(gridData);

		// Exceptions:
		TriStateCheckBox exceptionsCheckBox = this.addTriStateCheckBoxWithDefault(
			parent,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_EXCEPTIONS_LABEL,
			this.buildExceptionsModel(),
			this.buildExceptionsStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_EXCEPTIONS
		);
		exceptionsCheckBox.getCheckBox().setLayoutData(gridData);
	}

	//************* logging level ************

	private EnumFormComboViewer<EclipseLinkLogging, EclipseLinkLoggingLevel> addLoggingLevelCombo(Composite container) {
		return new EnumFormComboViewer<EclipseLinkLogging, EclipseLinkLoggingLevel>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkLogging.LEVEL_PROPERTY);
			}

			@Override
			protected EclipseLinkLoggingLevel[] getChoices() {
				return EclipseLinkLoggingLevel.values();
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
			
			@Override
			protected EclipseLinkLoggingLevel getDefaultValue() {
				return this.getSubject().getDefaultLevel();
			}

			@Override
			protected String displayString(EclipseLinkLoggingLevel value) {
				switch (value) {
					case all :
						return JptJpaEclipseLinkUiMessages.LOGGING_LEVEL_COMPOSITE_ALL;
					case config :
						return JptJpaEclipseLinkUiMessages.LOGGING_LEVEL_COMPOSITE_CONFIG;
					case fine :
						return JptJpaEclipseLinkUiMessages.LOGGING_LEVEL_COMPOSITE_FINE;
					case finer :
						return JptJpaEclipseLinkUiMessages.LOGGING_LEVEL_COMPOSITE_FINER;
					case finest :
						return JptJpaEclipseLinkUiMessages.LOGGING_LEVEL_COMPOSITE_FINEST;
					case info :
						return JptJpaEclipseLinkUiMessages.LOGGING_LEVEL_COMPOSITE_INFO;
					case off :
						return JptJpaEclipseLinkUiMessages.LOGGING_LEVEL_COMPOSITE_OFF;
					case severe :
						return JptJpaEclipseLinkUiMessages.LOGGING_LEVEL_COMPOSITE_SEVERE;
					case warning :
						return JptJpaEclipseLinkUiMessages.LOGGING_LEVEL_COMPOSITE_WARNING;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected EclipseLinkLoggingLevel getValue() {
				return this.getSubject().getLevel();
			}

			@Override
			protected void setValue(EclipseLinkLoggingLevel value) {
				this.getSubject().setLevel(value);
			}
			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_LEVEL;
			}
		};
		
	}

	//************* logging level ************

	private FileChooserComboPane<EclipseLinkLogging> addLogFileLocationComposite(Composite parent) {
		return new FileChooserComboPane<EclipseLinkLogging>(this, parent) {
	
			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapterXXXX<EclipseLinkLogging, String>(
										getSubjectHolder(), EclipseLinkLogging.LOG_FILE_LOCATION_PROPERTY) {
					@Override
					protected String buildValue_() {
	
						String name = this.subject.getLogFileLocation();
						if (name == null) {
							name = defaultValue(this.subject);
						}
						return name;
					}
	
					@Override
					protected void setValue_(String value) {
	
						if (defaultValue(this.subject).equals(value)) {
							value = null;
						}
						this.subject.setLogFileLocation(value);
					}
				};
			}
	
			String defaultValue(EclipseLinkLogging subject) {
				String defaultValue = subject.getDefaultLogFileLocation();
				return (defaultValue != null) ? NLS.bind(JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM, defaultValue) : this.getDefaultString();
			}
	
			@Override
			protected String getDefaultString() {
				return JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_DEFAULT_STDOUT;
			}
	
			@Override
			protected String getDialogTitle() {
				return JptJpaEclipseLinkUiMessages.LOGGING_FILE_LOCATION_COMPOSITE_DIALOG_TITLE;
			}
	
			@Override
			protected String getProjectPath() {
				return this.getSubject().getJpaProject().getProject().getLocation().toString();
			}
		};
	}


	//************* timestamp ************
	
	private ModifiablePropertyValueModel<Boolean> buildTimestampModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkLogging, Boolean>(getSubjectHolder(), EclipseLinkLogging.TIMESTAMP_PROPERTY) {
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

	private PropertyValueModel<String> buildTimestampStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultTimestampModel(), TIMESTAMP_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> TIMESTAMP_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_TIMESTAMP_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_TIMESTAMP_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultTimestampModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkLogging, Boolean>(
			getSubjectHolder(),
			EclipseLinkLogging.TIMESTAMP_PROPERTY)
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
	
	private ModifiablePropertyValueModel<Boolean> buildThreadModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkLogging, Boolean>(getSubjectHolder(), EclipseLinkLogging.THREAD_PROPERTY) {
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

	private PropertyValueModel<String> buildThreadStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultThreadModel(), THREAD_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> THREAD_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_THREAD_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_THREAD_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultThreadModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkLogging, Boolean>(
			getSubjectHolder(),
			EclipseLinkLogging.THREAD_PROPERTY)
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

	private ModifiablePropertyValueModel<Boolean> buildSessionModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkLogging, Boolean>(getSubjectHolder(), EclipseLinkLogging.SESSION_PROPERTY) {
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

	private PropertyValueModel<String> buildSessionStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultSessionModel(), SESSION_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> SESSION_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_SESSION_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_SESSION_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultSessionModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkLogging, Boolean>(
			getSubjectHolder(),
			EclipseLinkLogging.SESSION_PROPERTY)
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
	
	private ModifiablePropertyValueModel<Boolean> buildExceptionsModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkLogging, Boolean>(getSubjectHolder(), EclipseLinkLogging.EXCEPTIONS_PROPERTY) {
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

	private PropertyValueModel<String> buildExceptionsStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultExceptionsModel(), EXCEPTIONS_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> EXCEPTIONS_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_EXCEPTIONS_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_EXCEPTIONS_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultExceptionsModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkLogging, Boolean>(
			getSubjectHolder(),
			EclipseLinkLogging.EXCEPTIONS_PROPERTY)
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
