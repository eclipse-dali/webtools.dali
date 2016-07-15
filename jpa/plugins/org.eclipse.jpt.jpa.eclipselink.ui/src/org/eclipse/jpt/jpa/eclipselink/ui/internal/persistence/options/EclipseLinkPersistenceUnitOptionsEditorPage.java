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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.swt.widgets.ComboTools;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlEnumValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOptions;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkSchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkTargetDatabase;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkTargetServer;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.BooleanStringTransformer;
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
	private PropertyValueModel<EclipseLinkOptions> optionsModel;

	// ********** constructor **********
	
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

		this.buildOptionsSection(container);
		
		this.buildSchemaGenerationSection(container);
		
		this.buildLoggingSection(container);

		this.buildMiscellaneousSection(container);
	}
	
	// ********** Logging **********

	protected Section buildLoggingSection(Composite container) {
		Section loggingSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		loggingSection.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_SECTION_TITLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		loggingSection.setLayoutData(gridData);
		Control loggingComposite = this.initializeLoggingSection(loggingSection);
		loggingSection.setClient(loggingComposite);
		return loggingSection;
	}

	@SuppressWarnings("unused")
	protected Control initializeLoggingSection(Section section) {			
		return new EclipseLinkLoggingComposite<EclipseLinkLogging>(this, this.buildLoggingModel(), section).getControl();
	}

	private PropertyValueModel<EclipseLinkLogging> buildLoggingModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), EclipseLinkPersistenceUnit.LOGGING_TRANSFORMER);
	}
	
	// ********** EclipseLink SchemaGeneration **********

	protected Section buildSchemaGenerationSection(Composite container) {
		Section schemaGenerationSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		schemaGenerationSection.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_ECLIPSELINK_SCHEMA_GENERATION_TAB_SECTION_TITLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		schemaGenerationSection.setLayoutData(gridData);
		Control schemaGenerationComposite = this.initializeSchemaGenerationSection(schemaGenerationSection);
		schemaGenerationSection.setClient(schemaGenerationComposite);
		return schemaGenerationSection;
	}

	protected Control initializeSchemaGenerationSection(Section section) {
		return new EclipseLinkPersistenceXmlSchemaGenerationComposite(this, this.buildSchemaGenerationModel(), section).getControl();
	}

	private PropertyValueModel<EclipseLinkSchemaGeneration> buildSchemaGenerationModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), EclipseLinkPersistenceUnit.ECLIPSELINK_SCHEMA_GENERATION_TRANSFORMER);
	}
	
	// ********** Miscellaneous **********

	protected Section buildMiscellaneousSection(Composite container) {
		Section miscellaneousSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		miscellaneousSection.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_MISCELLANEOUS_SECTION_TITLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		miscellaneousSection.setLayoutData(gridData);
		Control miscellaneousComposite = this.initializeMiscellaneousSection(miscellaneousSection);
		miscellaneousSection.setClient(miscellaneousComposite);
		return miscellaneousSection;
	}

	protected Control initializeMiscellaneousSection(Section section) {			
		Composite container = this.addSubPane(section);
		this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_TEMPORAL_MUTABLE_LABEL,
			this.buildTemporalMutableModel(),
			this.buildTemporalMutableStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS
		);

		return container;
	}
	
	// ********** Options **********

	protected Section buildOptionsSection(Composite container) {
		Section sessionOptionsSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		sessionOptionsSection.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_SESSION_SECTION_TITLE);
		Control sessionOptionsComposite = this.initializeSessionOptionsSection(sessionOptionsSection);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		sessionOptionsSection.setLayoutData(gridData);
		sessionOptionsSection.setClient(sessionOptionsComposite);
		return sessionOptionsSection;
	}

	private Control initializeSessionOptionsSection(Section section) {
		this.optionsModel = this.buildOptionsModel();
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;
		Composite container = this.addPane(section, layout);

		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_SESSION_NAME);
		Combo sessionNameCombo = this.addEditableCombo(
			container,
			this.buildDefaultSessionNameListModel(),
			this.buildSessionNameModel(),
			TransformerTools.<String>objectToStringTransformer(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS_SESSION_NAME
		);
		ComboTools.handleDefaultValue(sessionNameCombo);


		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_SESSIONS_XML);
		Combo sessionsXmlCombo = this.addEditableCombo(
			container,
			this.buildDefaultSessionsXmlFileNameListModel(),
			this.buildSessionsXmlFileNameModel(),
			TransformerTools.<String>objectToStringTransformer(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS_SESSIONS_XML);
		ComboTools.handleDefaultValue(sessionsXmlCombo);


		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_TARGET_DATABASE_LABEL);
		Combo targetDatabaseCombo = this.addEditableCombo(
			container,
			this.buildTargetDatabaseListModel(),
			this.buildTargetDatabaseModel(),
			this.buildTargetDatabaseLabelTransformer(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS_TARGET_DATABASE
		);
		ComboTools.handleDefaultValue(targetDatabaseCombo);


		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_TARGET_SERVER_LABEL);
		Combo targetServerCombo = this.addEditableCombo(
			container,
			this.buildTargetServerListModel(),
			this.buildTargetServerModel(),
			this.buildTargetServerConverter(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS_TARGET_SERVER
		);
		ComboTools.handleDefaultValue(targetServerCombo);

		Hyperlink eventListenerLink = addHyperlink(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_EVENT_LISTENER_LABEL);
		this.initializeEventListenerClassChooser(container, eventListenerLink);

		TriStateCheckBox includeDescriptorQueriesCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_INCLUDE_DESCRIPTOR_QUERIES_LABEL,
			this.buildIncludeDescriptorQueriesModel(),
			this.buildIncludeDescriptorQueriesStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		includeDescriptorQueriesCheckBox.getCheckBox().setLayoutData(gridData);

		return container;
	}

	private PropertyValueModel<EclipseLinkOptions> buildOptionsModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), EclipseLinkPersistenceUnit.ECLIPSELINK_OPTIONS_TRANSFORMER);
	}

	//******** session name *********

	private PropertyValueModel<String> buildDefaultSessionNameModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, String>(this.optionsModel, EclipseLinkOptions.DEFAULT_SESSION_NAME) {
			@Override
			protected String buildValue_() {
				return EclipseLinkPersistenceUnitOptionsEditorPage.this.getSessionNameDefaultValue(this.subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultSessionNameListModel() {
		return new PropertyListValueModelAdapter<>(
			this.buildDefaultSessionNameModel()
		);
	}

	private ModifiablePropertyValueModel<String> buildSessionNameModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, String>(this.optionsModel, EclipseLinkOptions.SESSION_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = this.subject.getSessionName();
				if (name == null) {
					name = EclipseLinkPersistenceUnitOptionsEditorPage.this.getSessionNameDefaultValue(this.subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getSessionNameDefaultValue(this.subject).equals(value)) {
					value = null;
				}
				this.subject.setSessionName(value);
			}
		};
	}

	String getSessionNameDefaultValue(EclipseLinkOptions subject) {
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

	private PropertyValueModel<String> buildDefaultSessionsXmlFileNameModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, String>(this.optionsModel, EclipseLinkOptions.DEFAULT_SESSIONS_XML) {
			@Override
			protected String buildValue_() {
				return EclipseLinkPersistenceUnitOptionsEditorPage.this.getSessionsXmlDefaultValue(this.subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultSessionsXmlFileNameListModel() {
		return new PropertyListValueModelAdapter<>(
			this.buildDefaultSessionsXmlFileNameModel()
		);
	}

	private ModifiablePropertyValueModel<String> buildSessionsXmlFileNameModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, String>(this.optionsModel, EclipseLinkOptions.SESSIONS_XML_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = this.subject.getSessionsXml();
				if (name == null) {
					name = EclipseLinkPersistenceUnitOptionsEditorPage.this.getSessionsXmlDefaultValue(this.subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getSessionsXmlDefaultValue(this.subject).equals(value)) {
					value = null;
				}
				this.subject.setSessionsXml(value);
			}
		};
	}

	String getSessionsXmlDefaultValue(EclipseLinkOptions subject) {
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

	private PropertyValueModel<String> buildDefaultTargetDatabaseModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, String>(this.optionsModel, EclipseLinkOptions.DEFAULT_TARGET_DATABASE) {
			@Override
			protected String buildValue_() {
				return EclipseLinkPersistenceUnitOptionsEditorPage.this.getTargetDatabaseDefaultValue(this.subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultTargetDatabaseListModel() {
		return new PropertyListValueModelAdapter<>(
			this.buildDefaultTargetDatabaseModel()
		);
	}

	String buildTargetDatabaseDisplayString(String targetDatabaseName) {
		switch (EclipseLinkTargetDatabase.valueOf(targetDatabaseName)) {
			case attunity :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_ATTUNITY;
			case auto :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_AUTO;
			case cloudscape :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_CLOUDSCAPE;
			case database :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_DATABASE;
			case db2 :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_DB2;
			case db2mainframe :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_DB2_MAINFRAME;
			case dbase :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_DBASE;
			case derby :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_DERBY;
			case hsql :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_HSQL;
			case informix :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_INFORMIX;
			case informix11 :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_INFORMIX11;
			case javadb :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_JAVADB;
			case maxdb :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_MAXDB;
			case mysql :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_MYSQL;
			case oracle :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_ORACLE;
			case oracle10 :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_ORACLE10;
			case oracle11 :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_ORACLE11;
			case oracle8 :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_ORACLE8;
			case oracle9 :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_ORACLE9;
			case pointbase :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_POINTBASE;
			case postgresql :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_POSTGRESQL;
			case sqlanywhere :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_SQLANYWHERE;
			case sqlserver :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_SQLSERVER;
			case sybase :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_SYBASE;
			case symfoware :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_SYMFOWARE;
			case timesten :
				return JptJpaEclipseLinkUiMessages.TARGET_DATABASE_COMPOSITE_TIMESTEN;
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

	private Transformer<String, String> buildTargetDatabaseLabelTransformer() {
		return new TargetDatabaseLabelTransformer();
	}

	class TargetDatabaseLabelTransformer
		extends TransformerAdapter<String, String>
	{
		@Override
		public String transform(String value) {
			try {
				return buildTargetDatabaseDisplayString(value);
			} catch (RuntimeException ex) {
				// value is not a target database
				return value;
			}
		}
	}

	private ModifiablePropertyValueModel<String> buildTargetDatabaseModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, String>(this.optionsModel, EclipseLinkOptions.TARGET_DATABASE_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = this.subject.getTargetDatabase();
				if (name == null) {
					name = EclipseLinkPersistenceUnitOptionsEditorPage.this.getTargetDatabaseDefaultValue(this.subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getTargetDatabaseDefaultValue(this.subject).equals(value)) {
					value = null;
				}
				this.subject.setTargetDatabase(value);
			}
		};
	}

	private ListValueModel<String> buildTargetDatabaseListModel() {
		ArrayList<ListValueModel<String>> models = new ArrayList<>(2);
		models.add(buildDefaultTargetDatabaseListModel());
		models.add(buildTargetDatabasesListModel());
		return CompositeListValueModel.forModels(models);
	}

	private Iterator<String> buildTargetDatabases() {
		return IteratorTools.transform(IteratorTools.iterator(EclipseLinkTargetDatabase.values()), PersistenceXmlEnumValue.ENUM_NAME_TRANSFORMER);
	}

	private CollectionValueModel<String> buildTargetDatabasesCollectionModel() {
		return new SimpleCollectionValueModel<>(
			CollectionTools.hashBag(buildTargetDatabases())
		);
	}

	private ListValueModel<String> buildTargetDatabasesListModel() {
		return new SortedListValueModelAdapter<>(
			this.buildTargetDatabasesCollectionModel(),
			this.buildTargetDatabaseComparator()
		);
	}

	String getTargetDatabaseDefaultValue(EclipseLinkOptions subject) {
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

	private PropertyValueModel<String> buildDefaultTargetServerModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, String>(this.optionsModel, EclipseLinkOptions.DEFAULT_TARGET_SERVER) {
			@Override
			protected String buildValue_() {
				return EclipseLinkPersistenceUnitOptionsEditorPage.this.getTargetServerDefaultValue(this.subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultTargetServerListModel() {
		return new PropertyListValueModelAdapter<>(
			this.buildDefaultTargetServerModel()
		);
	}

	String buildTargetServerDisplayString(String targetServerName) {
		switch (EclipseLinkTargetServer.valueOf(targetServerName)) {
			case jboss :
				return JptJpaEclipseLinkUiMessages.TARGET_SERVER_COMPOSITE_JBOSS;
			case netweaver_7_1 :
				return JptJpaEclipseLinkUiMessages.TARGET_SERVER_COMPOSITE_NETWEAVER_7_1;
			case none :
				return JptJpaEclipseLinkUiMessages.TARGET_SERVER_COMPOSITE_NONE;
			case oc4j :
				return JptJpaEclipseLinkUiMessages.TARGET_SERVER_COMPOSITE_OC4J;
			case sunas9 :
				return JptJpaEclipseLinkUiMessages.TARGET_SERVER_COMPOSITE_SUNAS9;
			case weblogic :
				return JptJpaEclipseLinkUiMessages.TARGET_SERVER_COMPOSITE_WEBLOGIC;
			case weblogic_10 :
				return JptJpaEclipseLinkUiMessages.TARGET_SERVER_COMPOSITE_WEBLOGIC_10;
			case weblogic_9 :
				return JptJpaEclipseLinkUiMessages.TARGET_SERVER_COMPOSITE_WEBLOGIC_9;
			case websphere :
				return JptJpaEclipseLinkUiMessages.TARGET_SERVER_COMPOSITE_WEBSPHERE;
			case websphere_6_1 :
				return JptJpaEclipseLinkUiMessages.TARGET_SERVER_COMPOSITE_WEBSPHERE_6_1;
			case websphere_7 :
				return JptJpaEclipseLinkUiMessages.TARGET_SERVER_COMPOSITE_WEBSPHERE_7;
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
		return new TargetServerLabelTransformer();
	}

	class TargetServerLabelTransformer
		extends TransformerAdapter<String, String>
	{
		@Override
		public String transform(String value) {
			try {
				return buildTargetServerDisplayString(value);
			} catch (RuntimeException ex) {
				// the value is not a target server
				return value;
			}
		}
	}

	private ModifiablePropertyValueModel<String> buildTargetServerModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, String>(this.optionsModel, EclipseLinkOptions.TARGET_SERVER_PROPERTY) {
			@Override
			protected String buildValue_() {
				String name = this.subject.getTargetServer();
				if (name == null) {
					name = EclipseLinkPersistenceUnitOptionsEditorPage.this.getTargetServerDefaultValue(this.subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {
				if (getTargetServerDefaultValue(this.subject).equals(value)) {
					value = null;
				}
				this.subject.setTargetServer(value);
			}
		};
	}

	private ListValueModel<String> buildTargetServerListModel() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<>(2);
		holders.add(buildDefaultTargetServerListModel());
		holders.add(buildTargetServersListModel());
		return CompositeListValueModel.forModels(holders);
	}

	private Iterator<String> buildTargetServers() {
		return IteratorTools.transform(IteratorTools.iterator(EclipseLinkTargetServer.values()), PersistenceXmlEnumValue.ENUM_NAME_TRANSFORMER);
	}

	private CollectionValueModel<String> buildTargetServersCollectionModel() {
		return new SimpleCollectionValueModel<>(
			CollectionTools.hashBag(buildTargetServers())
		);
	}

	private ListValueModel<String> buildTargetServersListModel() {
		return new SortedListValueModelAdapter<>(
			buildTargetServersCollectionModel(),
			buildTargetServerComparator()
		);
	}

	String getTargetServerDefaultValue(EclipseLinkOptions subject) {
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

	private ClassChooserPane<EclipseLinkOptions> initializeEventListenerClassChooser(Composite container, Hyperlink hyperlink) {
		return new ClassChooserPane<EclipseLinkOptions>(this, this.optionsModel, container, hyperlink) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextModel() {
				return new PropertyAspectAdapterXXXX<EclipseLinkOptions, String>(
							this.getSubjectHolder(), EclipseLinkOptions.SESSION_EVENT_LISTENER_PROPERTY) {
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
				return EclipseLinkOptions.ECLIPSELINK_EVENT_LISTENER_CLASS_NAME;
			}
		};
	}


	//******** include descriptor queries *********
	
	private ModifiablePropertyValueModel<Boolean> buildIncludeDescriptorQueriesModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, Boolean>(this.optionsModel, EclipseLinkOptions.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY) {
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

	private PropertyValueModel<String> buildIncludeDescriptorQueriesStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultIncludeDescriptorQueriesModel(), INCLUDE_DESCRIPTOR_QUERIES_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> INCLUDE_DESCRIPTOR_QUERIES_TRANSFORMER = new BooleanStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_INCLUDE_DESCRIPTOR_QUERIES_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_INCLUDE_DESCRIPTOR_QUERIES_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultIncludeDescriptorQueriesModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, Boolean>(
			this.optionsModel,
			EclipseLinkOptions.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY)
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

	protected ModifiablePropertyValueModel<Boolean> buildTemporalMutableModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, Boolean>(this.optionsModel, EclipseLinkOptions.TEMPORAL_MUTABLE_PROPERTY) {
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

	protected PropertyValueModel<String> buildTemporalMutableStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultTemporalMutableModel(), TEMPORAL_MUTABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> TEMPORAL_MUTABLE_TRANSFORMER = new BooleanStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_TEMPORAL_MUTABLE_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_OPTIONS_TAB_TEMPORAL_MUTABLE_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultTemporalMutableModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkOptions, Boolean>(
			this.optionsModel,
			EclipseLinkOptions.TEMPORAL_MUTABLE_PROPERTY)
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
