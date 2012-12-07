/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Logging2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Logging2_4;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.LoggingLevel;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Section;

public class EclipseLinkLogging2_4Composite
	extends EclipseLinkLogging2_0Composite
{
	public EclipseLinkLogging2_4Composite(
			Pane<?> parent,
			PropertyValueModel<Logging2_0> subjectModel,
			Composite parentComposite) {
		super(parent, subjectModel, parentComposite);
	}

	@Override
	protected Composite createCategoryLoggingLevelClient(Section loggingSection) {
		Composite loggingClient = this.getWidgetFactory().createComposite(loggingSection);
		loggingClient.setLayout(new GridLayout(2, false));

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_cacheLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.CACHE_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.CONNECTION_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_ddlLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_4.DDL_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_dmsLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.DMS_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_ejbLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.EJB_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_eventLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_0.EVENT_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_jpaLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_4.JPA_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_metadataLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_4.METADATA_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, EclipseLinkUiMessages.PersistenceXmlLoggingTab_metamodelLoggingLevelLabel);
		this.addLoggingLevelCombo(loggingClient, Logging2_4.METAMODEL_CATEGORY_LOGGING_PROPERTY);

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
	
	private static final String DEFAULT_PROPERTY = Logging2_0.CATEGORIES_DEFAULT_LOGGING_PROPERTY;
	
	@Override
	protected EnumFormComboViewer<Logging2_0, LoggingLevel> addLoggingLevelCombo(Composite container, final String category) {
		return new EnumFormComboViewer<Logging2_0, LoggingLevel>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(DEFAULT_PROPERTY);
				propertyNames.add(Logging2_0.CACHE_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.CONNECTION_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_4.DDL_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.DMS_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.EJB_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.EVENT_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_4.JPA_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_4.METADATA_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_4.METAMODEL_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.PROPAGATION_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.PROPERTIES_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.QUERY_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.SEQUENCING_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.SERVER_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.SQL_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.TRANSACTION_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(Logging2_0.WEAVER_CATEGORY_LOGGING_PROPERTY);
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
