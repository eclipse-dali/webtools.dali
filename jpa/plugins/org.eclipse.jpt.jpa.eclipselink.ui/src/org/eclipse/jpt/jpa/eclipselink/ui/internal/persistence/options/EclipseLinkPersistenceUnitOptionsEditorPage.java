/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.StringObjectTransformer;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlEnumValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Logging;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Options;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.SchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.TargetDatabase;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.TargetServer;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import com.ibm.icu.text.Collator;

public class EclipseLinkPersistenceUnitOptionsEditorPage
	extends Pane<PersistenceUnit>
{
	private PropertyValueModel<Options> optionsHolder;

	public EclipseLinkPersistenceUnitOptionsEditorPage(
			PropertyValueModel<PersistenceUnit> persistenceUnitModel,
            Composite parentComposite,
            WidgetFactory widgetFactory,
            ResourceManager resourceManager) {
		super(persistenceUnitModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addSubPane(parent, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		Section sessionOptionsSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		sessionOptionsSection.setText(EclipseLinkUiMessages.PersistenceXmlOptionsTab_sessionSectionTitle);
		Control sessionOptionsComposite = this.initializeSessionOptionsSection(sessionOptionsSection);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		sessionOptionsSection.setLayoutData(gridData);
		sessionOptionsSection.setClient(sessionOptionsComposite);

		Section schemaGenerationSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		schemaGenerationSection.setText(EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_sectionTitle);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		schemaGenerationSection.setLayoutData(gridData);
		Control schemaGenerationComposite = this.initializeSchemaGenerationSection(schemaGenerationSection);
		schemaGenerationSection.setClient(schemaGenerationComposite);

		Section loggingSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		loggingSection.setText(EclipseLinkUiMessages.PersistenceXmlLoggingTab_sectionTitle);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		loggingSection.setLayoutData(gridData);
		Control loggingComposite = this.initializeLoggingSection(loggingSection);
		loggingSection.setClient(loggingComposite);

		Section miscellaneousSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		miscellaneousSection.setText(EclipseLinkUiMessages.PersistenceXmlOptionsTab_miscellaneousSectionTitle);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		miscellaneousSection.setLayoutData(gridData);
		Control miscellaneousComposite = this.initializeMiscellaneousSection(miscellaneousSection);
		miscellaneousSection.setClient(miscellaneousComposite);
	}

	private Control initializeSessionOptionsSection(Section section) {
		this.optionsHolder = this.buildOptionsHolder();
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;
		Composite container = this.addPane(section, layout);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlOptionsTab_sessionName);
		Combo sessionNameCombo = addEditableCombo(
			container,
			this.buildDefaultSessionNameListHolder(),
			this.buildSessionNameHolder(),
			StringObjectTransformer.<String>instance(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS_SESSION_NAME
		);
		SWTUtil.attachDefaultValueHandler(sessionNameCombo);


		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlOptionsTab_sessionsXml);
		Combo sessionsXmlCombo = addEditableCombo(
			container,
			this.buildDefaultSessionsXmlFileNameListHolder(),
			this.buildSessionsXmlFileNameHolder(),
			StringObjectTransformer.<String>instance(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS_SESSIONS_XML);
		SWTUtil.attachDefaultValueHandler(sessionsXmlCombo);


		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlOptionsTab_targetDatabaseLabel);
		Combo targetDatabaseCombo = addEditableCombo(
			container,
			this.buildTargetDatabaseListHolder(),
			this.buildTargetDatabaseHolder(),
			this.buildTargetDatabaseConverter(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS_TARGET_DATABASE
		);
		SWTUtil.attachDefaultValueHandler(targetDatabaseCombo);


		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlOptionsTab_targetServerLabel);
		Combo targetServerCombo = addEditableCombo(
			container,
			this.buildTargetServerListHolder(),
			this.buildTargetServerHolder(),
			this.buildTargetServerConverter(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS_TARGET_SERVER
		);
		SWTUtil.attachDefaultValueHandler(targetServerCombo);

		Hyperlink eventListenerLink = addHyperlink(container, EclipseLinkUiMessages.PersistenceXmlOptionsTab_eventListenerLabel);
		this.initializeEventListenerClassChooser(container, eventListenerLink);

		TriStateCheckBox includeDescriptorQueriesCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlOptionsTab_includeDescriptorQueriesLabel,
			this.buildIncludeDescriptorQueriesHolder(),
			this.buildIncludeDescriptorQueriesStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		includeDescriptorQueriesCheckBox.getCheckBox().setLayoutData(gridData);

		return container;
	}

	protected Control initializeLoggingSection(Section section) {			
		return new EclipseLinkLoggingComposite<Logging>(this, this.buildLoggingHolder(), section).getControl();
	}

	protected Control initializeSchemaGenerationSection(Section section) {
		return new PersistenceXmlSchemaGenerationComposite(this, this.buildSchemaGenerationHolder(), section).getControl();
	}

	protected Control initializeMiscellaneousSection(Section section) {			
		Composite container = this.addSubPane(section);
		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlOptionsTab_temporalMutableLabel,
			this.buildTemporalMutableHolder(),
			this.buildTemporalMutableStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS
		);

		return container;
	}

	private PropertyValueModel<SchemaGeneration> buildSchemaGenerationHolder() {
		return new TransformationPropertyValueModel<PersistenceUnit, SchemaGeneration>(getSubjectHolder()) {
			@Override
			protected SchemaGeneration transform_(PersistenceUnit value) {
				return ((EclipseLinkPersistenceUnit) value).getSchemaGeneration();
			}
		};
	}

	private PropertyValueModel<Logging> buildLoggingHolder() {
		return new TransformationPropertyValueModel<PersistenceUnit, Logging>(getSubjectHolder()) {
			@Override
			protected Logging transform_(PersistenceUnit value) {
				return ((EclipseLinkPersistenceUnit) value).getLogging();
			}
		};
	}

	private PropertyValueModel<Options> buildOptionsHolder() {
		return new TransformationPropertyValueModel<PersistenceUnit, Options>(getSubjectHolder()) {
			@Override
			protected Options transform_(PersistenceUnit value) {

				return ((EclipseLinkPersistenceUnit)value).getOptions();
			}
		};
	}


	//******** session name *********

	private PropertyValueModel<String> buildDefaultSessionNameHolder() {
		return new PropertyAspectAdapter<Options, String>(this.optionsHolder, Options.DEFAULT_SESSION_NAME) {
			@Override
			protected String buildValue_() {
				return EclipseLinkPersistenceUnitOptionsEditorPage.this.getSessionNameDefaultValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultSessionNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultSessionNameHolder()
		);
	}

	private ModifiablePropertyValueModel<String> buildSessionNameHolder() {
		return new PropertyAspectAdapter<Options, String>(this.optionsHolder, Options.SESSION_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getSessionName();
				if (name == null) {
					name = EclipseLinkPersistenceUnitOptionsEditorPage.this.getSessionNameDefaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getSessionNameDefaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setSessionName(value);
			}
		};
	}

	private String getSessionNameDefaultValue(Options subject) {
		String defaultValue = subject.getDefaultSessionName();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
				defaultValue
			);
		}
		return JptCommonUiMessages.DEFAULT_EMPTY;
	}


	//******** sessions xml *********

	private PropertyValueModel<String> buildDefaultSessionsXmlFileNameHolder() {
		return new PropertyAspectAdapter<Options, String>(this.optionsHolder, Options.DEFAULT_SESSIONS_XML) {
			@Override
			protected String buildValue_() {
				return EclipseLinkPersistenceUnitOptionsEditorPage.this.getSessionsXmlDefaultValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultSessionsXmlFileNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultSessionsXmlFileNameHolder()
		);
	}

	private ModifiablePropertyValueModel<String> buildSessionsXmlFileNameHolder() {
		return new PropertyAspectAdapter<Options, String>(this.optionsHolder, Options.SESSIONS_XML_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getSessionsXml();
				if (name == null) {
					name = EclipseLinkPersistenceUnitOptionsEditorPage.this.getSessionsXmlDefaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getSessionsXmlDefaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setSessionsXml(value);
			}
		};
	}

	private String getSessionsXmlDefaultValue(Options subject) {
		String defaultValue = subject.getDefaultSessionsXml();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
				defaultValue
			);
		}
		return JptCommonUiMessages.DEFAULT_EMPTY;
	}


	//******** target database *********

	private PropertyValueModel<String> buildDefaultTargetDatabaseHolder() {
		return new PropertyAspectAdapter<Options, String>(this.optionsHolder, Options.DEFAULT_TARGET_DATABASE) {
			@Override
			protected String buildValue_() {
				return EclipseLinkPersistenceUnitOptionsEditorPage.this.getTargetDatabaseDefaultValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultTargetDatabaseListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultTargetDatabaseHolder()
		);
	}

	private String buildTargetDatabaseDisplayString(String targetDatabaseName) {
		switch (TargetDatabase.valueOf(targetDatabaseName)) {
			case attunity :
				return EclipseLinkUiMessages.TargetDatabaseComposite_attunity;
			case auto :
				return EclipseLinkUiMessages.TargetDatabaseComposite_auto;
			case cloudscape :
				return EclipseLinkUiMessages.TargetDatabaseComposite_cloudscape;
			case database :
				return EclipseLinkUiMessages.TargetDatabaseComposite_database;
			case db2 :
				return EclipseLinkUiMessages.TargetDatabaseComposite_db2;
			case db2mainframe :
				return EclipseLinkUiMessages.TargetDatabaseComposite_db2mainframe;
			case dbase :
				return EclipseLinkUiMessages.TargetDatabaseComposite_dbase;
			case derby :
				return EclipseLinkUiMessages.TargetDatabaseComposite_derby;
			case hsql :
				return EclipseLinkUiMessages.TargetDatabaseComposite_hsql;
			case informix :
				return EclipseLinkUiMessages.TargetDatabaseComposite_informix;
			case javadb :
				return EclipseLinkUiMessages.TargetDatabaseComposite_javadb;
			case maxdb :
				return EclipseLinkUiMessages.TargetDatabaseComposite_maxdb;
			case mysql :
				return EclipseLinkUiMessages.TargetDatabaseComposite_mysql;
			case oracle :
				return EclipseLinkUiMessages.TargetDatabaseComposite_oracle;
			case oracle10 :
				return EclipseLinkUiMessages.TargetDatabaseComposite_oracle10;
			case oracle11 :
				return EclipseLinkUiMessages.TargetDatabaseComposite_oracle11;
			case oracle8 :
				return EclipseLinkUiMessages.TargetDatabaseComposite_oracle8;
			case oracle9 :
				return EclipseLinkUiMessages.TargetDatabaseComposite_oracle9;
			case pointbase :
				return EclipseLinkUiMessages.TargetDatabaseComposite_pointbase;
			case postgresql :
				return EclipseLinkUiMessages.TargetDatabaseComposite_postgresql;
			case sqlanywhere :
				return EclipseLinkUiMessages.TargetDatabaseComposite_sqlanywhere;
			case sqlserver :
				return EclipseLinkUiMessages.TargetDatabaseComposite_sqlserver;
			case sybase :
				return EclipseLinkUiMessages.TargetDatabaseComposite_sybase;
			case symfoware :
				return EclipseLinkUiMessages.TargetDatabaseComposite_symfoware;
			case timesten :
				return EclipseLinkUiMessages.TargetDatabaseComposite_timesten;
			default :
				throw new IllegalStateException();
		}
	}

	private Comparator<String> buildTargetDatabaseComparator() {
		return new Comparator<String>() {
			public int compare(String targetDatabase1, String targetDatabase2) {
				targetDatabase1 = buildTargetDatabaseDisplayString(targetDatabase1);
				targetDatabase2 = buildTargetDatabaseDisplayString(targetDatabase2);
				return Collator.getInstance().compare(targetDatabase1, targetDatabase2);
			}
		};
	}

	private Transformer<String, String> buildTargetDatabaseConverter() {
		return new TransformerAdapter<String, String>() {
			@Override
			public String transform(String value) {
				try {
					TargetDatabase.valueOf(value);
					value = buildTargetDatabaseDisplayString(value);
				}
				catch (Exception e) {
					// Ignore since the value is not a TargetDatabase
				}
				return value;
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildTargetDatabaseHolder() {
		return new PropertyAspectAdapter<Options, String>(this.optionsHolder, Options.TARGET_DATABASE_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getTargetDatabase();
				if (name == null) {
					name = EclipseLinkPersistenceUnitOptionsEditorPage.this.getTargetDatabaseDefaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getTargetDatabaseDefaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setTargetDatabase(value);
			}
		};
	}

	private ListValueModel<String> buildTargetDatabaseListHolder() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<ListValueModel<String>>(2);
		holders.add(buildDefaultTargetDatabaseListHolder());
		holders.add(buildTargetDatabasesListHolder());
		return CompositeListValueModel.forModels(holders);
	}

	private Iterator<String> buildTargetDatabases() {
		return IteratorTools.transform(IteratorTools.iterator(TargetDatabase.values()), PersistenceXmlEnumValue.ENUM_NAME_TRANSFORMER);
	}

	private CollectionValueModel<String> buildTargetDatabasesCollectionHolder() {
		return new SimpleCollectionValueModel<String>(
			CollectionTools.collection(buildTargetDatabases())
		);
	}

	private ListValueModel<String> buildTargetDatabasesListHolder() {
		return new SortedListValueModelAdapter<String>(
			buildTargetDatabasesCollectionHolder(),
			buildTargetDatabaseComparator()
		);
	}

	private String getTargetDatabaseDefaultValue(Options subject) {
		String defaultValue = subject.getDefaultTargetDatabase();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
				defaultValue
			);
		}
		return JptCommonUiMessages.DEFAULT_EMPTY;
	}


	//******** target server *********

	private PropertyValueModel<String> buildDefaultTargetServerHolder() {
		return new PropertyAspectAdapter<Options, String>(this.optionsHolder, Options.DEFAULT_TARGET_SERVER) {
			@Override
			protected String buildValue_() {
				return EclipseLinkPersistenceUnitOptionsEditorPage.this.getTargetServerDefaultValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultTargetServerListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultTargetServerHolder()
		);
	}

	private String buildTargetServerDisplayString(String targetServerName) {
		switch (TargetServer.valueOf(targetServerName)) {
			case jboss :
				return EclipseLinkUiMessages.TargetServerComposite_jboss;
			case netweaver_7_1 :
				return EclipseLinkUiMessages.TargetServerComposite_netweaver_7_1;
			case none :
				return EclipseLinkUiMessages.TargetServerComposite_none;
			case oc4j :
				return EclipseLinkUiMessages.TargetServerComposite_oc4j;
			case sunas9 :
				return EclipseLinkUiMessages.TargetServerComposite_sunas9;
			case weblogic :
				return EclipseLinkUiMessages.TargetServerComposite_weblogic;
			case weblogic_10 :
				return EclipseLinkUiMessages.TargetServerComposite_weblogic_10;
			case weblogic_9 :
				return EclipseLinkUiMessages.TargetServerComposite_weblogic_9;
			case websphere :
				return EclipseLinkUiMessages.TargetServerComposite_websphere;
			case websphere_6_1 :
				return EclipseLinkUiMessages.TargetServerComposite_websphere_6_1;
			case websphere_7 :
				return EclipseLinkUiMessages.TargetServerComposite_websphere_7;
			default :
				throw new IllegalStateException();
		}
	}

	private Comparator<String> buildTargetServerComparator() {
		return new Comparator<String>() {
			public int compare(String targetServer1, String targetServer2) {
				targetServer1 = buildTargetServerDisplayString(targetServer1);
				targetServer2 = buildTargetServerDisplayString(targetServer2);
				return Collator.getInstance().compare(targetServer1, targetServer2);
			}
		};
	}

	private Transformer<String, String> buildTargetServerConverter() {
		return new TransformerAdapter<String, String>() {
			@Override
			public String transform(String value) {
				try {
					TargetServer.valueOf(value);
					value = buildTargetServerDisplayString(value);
				}
				catch (Exception e) {
					// Ignore since the value is not a TargetServer
				}
				return value;
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildTargetServerHolder() {
		return new PropertyAspectAdapter<Options, String>(this.optionsHolder, Options.TARGET_SERVER_PROPERTY) {
			@Override
			protected String buildValue_() {
				String name = subject.getTargetServer();
				if (name == null) {
					name = EclipseLinkPersistenceUnitOptionsEditorPage.this.getTargetServerDefaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {
				if (getTargetServerDefaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setTargetServer(value);
			}
		};
	}

	private ListValueModel<String> buildTargetServerListHolder() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<ListValueModel<String>>(2);
		holders.add(buildDefaultTargetServerListHolder());
		holders.add(buildTargetServersListHolder());
		return CompositeListValueModel.forModels(holders);
	}

	private Iterator<String> buildTargetServers() {
		return IteratorTools.transform(IteratorTools.iterator(TargetServer.values()), PersistenceXmlEnumValue.ENUM_NAME_TRANSFORMER);
	}

	private CollectionValueModel<String> buildTargetServersCollectionHolder() {
		return new SimpleCollectionValueModel<String>(
			CollectionTools.collection(buildTargetServers())
		);
	}

	private ListValueModel<String> buildTargetServersListHolder() {
		return new SortedListValueModelAdapter<String>(
			buildTargetServersCollectionHolder(),
			buildTargetServerComparator()
		);
	}

	private String getTargetServerDefaultValue(Options subject) {
		String defaultValue = subject.getDefaultTargetServer();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
				defaultValue
			);
		}
		return JptCommonUiMessages.DEFAULT_EMPTY;
	}


	//********event listener *********

	private ClassChooserPane<Options> initializeEventListenerClassChooser(Composite container, Hyperlink hyperlink) {
		return new ClassChooserPane<Options>(this, this.optionsHolder, container, hyperlink) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<Options, String>(
							this.getSubjectHolder(), Options.SESSION_EVENT_LISTENER_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getEventListener();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}
						this.subject.setEventListener(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return this.getSubject().getEventListener();
			}

			@Override
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}

			@Override
			protected void setClassName(String className) {
				this.getSubject().setEventListener(className);
			}

			@Override
			protected String getSuperInterfaceName() {
				return Options.ECLIPSELINK_EVENT_LISTENER_CLASS_NAME;
			}
		};
	}


	//******** include descriptor queries *********
	
	private ModifiablePropertyValueModel<Boolean> buildIncludeDescriptorQueriesHolder() {
		return new PropertyAspectAdapter<Options, Boolean>(this.optionsHolder, Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getIncludeDescriptorQueries();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setIncludeDescriptorQueries(value);
			}
		};
	}

	private PropertyValueModel<String> buildIncludeDescriptorQueriesStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultIncludeDescriptorQueriesHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlOptionsTab_includeDescriptorQueriesLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlOptionsTab_includeDescriptorQueriesLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultIncludeDescriptorQueriesHolder() {
		return new PropertyAspectAdapter<Options, Boolean>(
			this.optionsHolder,
			Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getIncludeDescriptorQueries() != null) {
					return null;
				}
				return this.subject.getDefaultIncludeDescriptorQueries();
			}
		};
	}


	//******** temporal mutable *********

	protected ModifiablePropertyValueModel<Boolean> buildTemporalMutableHolder() {
		return new PropertyAspectAdapter<Options, Boolean>(this.optionsHolder, Options.TEMPORAL_MUTABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getTemporalMutable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setTemporalMutable(value);
			}
		};
	}

	protected PropertyValueModel<String> buildTemporalMutableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultTemporalMutableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlOptionsTab_temporalMutableLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlOptionsTab_temporalMutableLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultTemporalMutableHolder() {
		return new PropertyAspectAdapter<Options, Boolean>(
			this.optionsHolder,
			Options.TEMPORAL_MUTABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getTemporalMutable() != null) {
					return null;
				}
				return this.subject.getDefaultTemporalMutable();
			}
		};
	}
}