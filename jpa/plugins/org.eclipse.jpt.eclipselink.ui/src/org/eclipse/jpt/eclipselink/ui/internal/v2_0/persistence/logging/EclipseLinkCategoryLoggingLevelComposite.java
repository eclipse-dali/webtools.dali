/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.logging;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.persistence.logging.LoggingLevel;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.logging.Logging2_0;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.utility.internal.ReflectionTools;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLinkCategoryLoggingLevelComposite
 */
public class EclipseLinkCategoryLoggingLevelComposite extends Pane<Logging2_0>
{
	private String property;
	
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
		
		this.property = Logging2_0.SQL_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.TRANSACTION_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.EVENT_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.CONNECTION_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.QUERY_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.CACHE_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.PROPAGATION_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.SEQUENCING_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.EJB_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.DMS_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.EJB_OR_METADATA_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.METAMODEL_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);

		this.property = Logging2_0.WEAVER_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.PROPERTIES_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
		
		this.property = Logging2_0.SERVER_CATEGORY_LOGGING_PROPERTY;
		new CategoryLoggingLevelComboViewer(this, parent);
	}
	


	private class CategoryLoggingLevelComboViewer extends Pane<Logging2_0>
	{
		private static final String DEFAULT_PROPERTY = Logging2_0.CATEGORIES_DEFAULT_LOGGING_PROPERTY;
		final private String category;
		
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
					Composite parent
					) {
			super(parentComposite, parent);
			
			this.category = EclipseLinkCategoryLoggingLevelComposite.this.property;
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
					return this.buildDisplayString(EclipseLinkUiMessages.class, EclipseLinkCategoryLoggingLevelComposite.class, value);
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
		protected void initializeLayout(Composite parent) {
			this.addLabeledComposite(
					parent,
					this.buildLabelString(),
					this.addLoggingLevelCombo(parent),
					null	// TODO
			);
		}
		
		private String buildLabelString() {

			StringBuilder sb = new StringBuilder();
			sb.append("PersistenceXmlLoggingTab_");	 //$NON-NLS-1$
			sb.append(EclipseLinkCategoryLoggingLevelComposite.this.property);
			sb.append("Label");	 //$NON-NLS-1$
			
			return (String) ReflectionTools.getStaticFieldValue(EclipseLinkUiMessages.class, sb.toString());
		}
	}
}