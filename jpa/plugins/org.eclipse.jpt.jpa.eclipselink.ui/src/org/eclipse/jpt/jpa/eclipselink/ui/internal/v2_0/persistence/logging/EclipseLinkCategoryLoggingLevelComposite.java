/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence.logging;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Logging2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.LoggingLevel;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLinkCategoryLoggingLevelComposite
 */
public class EclipseLinkCategoryLoggingLevelComposite extends Pane<Logging2_0>
{
	
	/**
	 * Creates a new <code>EclipseLinkCategoryLoggingLevelComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public EclipseLinkCategoryLoggingLevelComposite(
					Pane<Logging2_0> parentComposite, 
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.SQL_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_sqlLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.TRANSACTION_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.EVENT_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_eventLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.CONNECTION_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_connectionLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.QUERY_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_queryLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.CACHE_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_cacheLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.PROPAGATION_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_propagationLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.SEQUENCING_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_sequencingLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.EJB_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_ejbLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.DMS_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_dmsLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.EJB_OR_METADATA_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_ejb_or_metadataLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.METAMODEL_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_jpa_metamodelLoggingLevelLabel);

		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.WEAVER_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_weaverLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.PROPERTIES_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_propertiesLoggingLevelLabel);
		
		new CategoryLoggingLevelComboViewer(this, parent, Logging2_0.SERVER_CATEGORY_LOGGING_PROPERTY, EclipseLinkUiMessages.PersistenceXmlLoggingTab_serverLoggingLevelLabel);
	}
	


	private class CategoryLoggingLevelComboViewer extends Pane<Logging2_0>
	{
		private static final String DEFAULT_PROPERTY = Logging2_0.CATEGORIES_DEFAULT_LOGGING_PROPERTY;
		final private String category;
		final private String labelString;
		
		/**
		 * Creates a new <code>CategoryLoggingLevelComposite</code>.
		 * 
		 * @param parentController
		 *            The parent container of this one
		 * @param parent
		 *            The parent container
		 */
		public CategoryLoggingLevelComboViewer(
					Pane<? extends Logging2_0> parentComposite, 
					Composite parent,
					String property,
					String labelString) {
			super(parentComposite, parent);
			
			this.category = property;
			this.labelString = labelString;
			initializeLayout2(this.getControl());
		}

		private EnumFormComboViewer<Logging2_0, LoggingLevel> addLoggingLevelCombo(Composite container) {
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

		@Override
		protected void initializeLayout(Composite container) {
			// see initializeLayout2
		}

		protected void initializeLayout2(Composite parent) {
			this.addLabeledComposite(
					parent,
					this.labelString,
					this.addLoggingLevelCombo(parent),
					null	// TODO
			);
		}
	}
}