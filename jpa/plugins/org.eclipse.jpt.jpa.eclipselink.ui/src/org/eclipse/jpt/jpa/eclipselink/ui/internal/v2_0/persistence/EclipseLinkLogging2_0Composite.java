/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Logging2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.LoggingLevel;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options.EclipseLinkLoggingComposite;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class EclipseLinkLogging2_0Composite
	extends EclipseLinkLoggingComposite<Logging2_0>
{
	public EclipseLinkLogging2_0Composite(
			Pane<?> parent,
			PropertyValueModel<Logging2_0> subjectModel,
			Composite parentComposite) {
		super(parent, subjectModel, parentComposite);
	}

	@Override
	protected void initializeLayout(Composite container) {
		super.initializeLayout(container);

		Composite loggingLevelComposite = this.addCategoryLoggingLevelSection(container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		loggingLevelComposite.setLayoutData(gridData);
	}

	protected Section addCategoryLoggingLevelSection(Composite container) {
		final Section loggingSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TWISTIE);
		loggingSection.setText(EclipseLinkUiMessages.PersistenceXmlLoggingTab_categoryLoggingLevelSectionTitle);

		loggingSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && loggingSection.getClient() == null) {
					loggingSection.setClient(createCategoryLoggingLevelClient(loggingSection));
				}
			}
		});

		return loggingSection;
	}

	protected Composite createCategoryLoggingLevelClient(Section loggingSection) {
		Composite loggingClient = this.getWidgetFactory().createComposite(loggingSection);
		loggingClient.setLayout(new GridLayout(2, false));

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_cacheLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.CACHE_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.CONNECTION_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_dmsLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.DMS_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_ejbLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.EJB_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_ejb_or_metadataLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.EJB_OR_METADATA_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_eventLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.EVENT_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_jpa_metamodelLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.JPA_METAMODEL_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_propagationLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.PROPAGATION_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_propertiesLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.PROPERTIES_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_queryLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.QUERY_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_sequencingLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.SEQUENCING_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_serverLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.SERVER_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_sqlLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.SQL_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_transactionLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.TRANSACTION_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_weaverLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.WEAVER_CATEGORY_LOGGING_PROPERTY);

		return loggingClient;
	}

	@Override
	protected void logPropertiesComposite(Composite container) {
		super.logPropertiesComposite(container);
		// Connection:
		TriStateCheckBox connectionCheckBox = 
			this.addTriStateCheckBoxWithDefault(
				container,
				EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLabel,
				this.buildConnectionHolder(),
				this.buildConnectionStringHolder(),
				null
//				EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_CONNECTION	// TODO
			);

		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		connectionCheckBox.getCheckBox().setLayoutData(gridData);
	}
	
	private ModifiablePropertyValueModel<Boolean> buildConnectionHolder() {
		return new PropertyAspectAdapter<Logging2_0, Boolean>(getSubjectHolder(), Logging2_0.CONNECTION_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getConnection();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setConnection(value);
			}
		};
	}

	private PropertyValueModel<String> buildConnectionStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(this.buildDefaultConnectionHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLabel;
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultConnectionHolder() {
		return new PropertyAspectAdapter<Logging2_0, Boolean>(
			getSubjectHolder(),
			Logging2_0.CONNECTION_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getConnection() != null) {
					return null;
				}
				return this.subject.getDefaultConnection();
			}
		};
	}
	private static final String DEFAULT_PROPERTY = Logging2_0.CATEGORIES_DEFAULT_LOGGING_PROPERTY;

	protected EnumFormComboViewer<Logging2_0, LoggingLevel> addLoggingLevelCombo(Composite container, final String category) {
		return new EnumFormComboViewer<Logging2_0, LoggingLevel>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(DEFAULT_PROPERTY);
				propertyNames.add(Logging2_0.SQL_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.TRANSACTION_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.EVENT_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.CONNECTION_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.QUERY_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.CACHE_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.PROPAGATION_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.SEQUENCING_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.EJB_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.DMS_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.EJB_OR_METADATA_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.JPA_METAMODEL_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.WEAVER_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.PROPERTIES_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.SERVER_CATEGORY_LOGGING_PROPERTY);
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
				return this.getSubject().getCategoriesDefaultLevel();
			}

			@Override
			protected String displayString(LoggingLevel value) {
				switch (value) {
					case all :
						return EclipseLinkUiMessages.EclipseLinkCategoryLoggingLevelComposite_all;
					case config :
						return EclipseLinkUiMessages.EclipseLinkCategoryLoggingLevelComposite_config;
					case fine :
						return EclipseLinkUiMessages.EclipseLinkCategoryLoggingLevelComposite_fine;
					case finer :
						return EclipseLinkUiMessages.EclipseLinkCategoryLoggingLevelComposite_finer;
					case finest :
						return EclipseLinkUiMessages.EclipseLinkCategoryLoggingLevelComposite_finest;
					case info :
						return EclipseLinkUiMessages.EclipseLinkCategoryLoggingLevelComposite_info;
					case off :
						return EclipseLinkUiMessages.EclipseLinkCategoryLoggingLevelComposite_off;
					case severe :
						return EclipseLinkUiMessages.EclipseLinkCategoryLoggingLevelComposite_severe;
					case warning :
						return EclipseLinkUiMessages.EclipseLinkCategoryLoggingLevelComposite_warning;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected LoggingLevel getValue() {
				return this.getSubject().getLevel(category);
			}

			@Override
			protected void setValue(LoggingLevel value) {
				this.getSubject().setLevel(category, value);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if( propertyName != category && propertyName != DEFAULT_PROPERTY) return;
				super.propertyChanged(propertyName);
			}
		};
	}
}
