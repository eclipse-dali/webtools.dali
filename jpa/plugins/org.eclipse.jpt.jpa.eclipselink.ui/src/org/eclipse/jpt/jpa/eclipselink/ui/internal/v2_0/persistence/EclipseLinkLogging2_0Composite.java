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
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
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

/**
 *  EclipseLinkLogging2_0Composite
 */
public class EclipseLinkLogging2_0Composite extends EclipseLinkLoggingComposite<Logging2_0>
{

	public EclipseLinkLogging2_0Composite(
				PropertyValueModel<Logging2_0> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		super.initializeLayout(container);

		Composite loggingLevelComposite = this.addCategoryLoggingLevelComposite(container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		loggingLevelComposite.setLayoutData(gridData);
	}

	protected Composite addCategoryLoggingLevelComposite(Composite container) {
		container = this.addCollapsibleSubSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_categoryLoggingLevelSectionTitle,
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE)
		);
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_sqlLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.SQL_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.TRANSACTION_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_eventLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.EVENT_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.CONNECTION_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_queryLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.QUERY_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_cacheLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.CACHE_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_propagationLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.PROPAGATION_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_sequencingLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.SEQUENCING_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_ejbLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.EJB_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_dmsLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.DMS_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_ejb_or_metadataLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.EJB_OR_METADATA_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_jpa_metamodelLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.METAMODEL_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_weaverLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.WEAVER_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_propertiesLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.PROPERTIES_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_serverLoggingLevelLabel);
		this.addLoggingLevelCombo(container, Logging2_0.SERVER_CATEGORY_LOGGING_PROPERTY);

		return container.getParent(); //return the Section, instead of its client
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

	private EnumFormComboViewer<Logging2_0, LoggingLevel> addLoggingLevelCombo(Composite container, final String category) {
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
				propertyNames.add(Logging2_0.METAMODEL_CATEGORY_LOGGING_PROPERTY);
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
