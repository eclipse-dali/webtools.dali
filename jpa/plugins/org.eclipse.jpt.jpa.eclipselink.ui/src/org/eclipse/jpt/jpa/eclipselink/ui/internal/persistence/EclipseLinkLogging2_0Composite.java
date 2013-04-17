/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence;

import java.util.Collection;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLoggingLevel;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
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
	extends EclipseLinkLoggingComposite<EclipseLinkLogging2_0>
{
	public EclipseLinkLogging2_0Composite(
			Pane<?> parent,
			PropertyValueModel<EclipseLinkLogging2_0> subjectModel,
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
		loggingSection.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_CATEGORY_LOGGING_LEVEL_SECTION_TITLE);

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

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_CACHE_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.CACHE_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_CONNECTION_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.CONNECTION_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_DMS_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.DMS_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_EJB_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.EJB_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_EJB_OR_METADATA_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.EJB_OR_METADATA_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_EVENT_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.EVENT_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_JPA_METAMODEL_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.JPA_METAMODEL_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_PROPAGATION_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.PROPAGATION_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_PROPERTIES_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.PROPERTIES_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_QUERY_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.QUERY_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_SEQUENCING_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.SEQUENCING_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_SERVER_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.SERVER_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_SQL_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.SQL_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_TRANSACTION_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.TRANSACTION_CATEGORY_LOGGING_PROPERTY);

		this.addLabel(loggingClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_WEAVER_LOGGING_LEVEL_LABEL);
		this.addLoggingLevelCombo(loggingClient, EclipseLinkLogging2_0.WEAVER_CATEGORY_LOGGING_PROPERTY);

		return loggingClient;
	}

	@Override
	protected void logPropertiesComposite(Composite container) {
		super.logPropertiesComposite(container);
		// Connection:
		TriStateCheckBox connectionCheckBox = 
			this.addTriStateCheckBoxWithDefault(
				container,
				JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_CONNECTION_LABEL,
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
		return new PropertyAspectAdapter<EclipseLinkLogging2_0, Boolean>(getSubjectHolder(), EclipseLinkLogging2_0.CONNECTION_PROPERTY) {
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
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_CONNECTION_LABEL_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_LOGGING_TAB_CONNECTION_LABEL;
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultConnectionHolder() {
		return new PropertyAspectAdapter<EclipseLinkLogging2_0, Boolean>(
			getSubjectHolder(),
			EclipseLinkLogging2_0.CONNECTION_PROPERTY)
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
	private static final String DEFAULT_PROPERTY = EclipseLinkLogging2_0.CATEGORIES_DEFAULT_LOGGING_PROPERTY;

	protected EnumFormComboViewer<EclipseLinkLogging2_0, EclipseLinkLoggingLevel> addLoggingLevelCombo(Composite container, final String category) {
		return new EnumFormComboViewer<EclipseLinkLogging2_0, EclipseLinkLoggingLevel>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(DEFAULT_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.SQL_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.TRANSACTION_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.EVENT_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.CONNECTION_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.QUERY_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.CACHE_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.PROPAGATION_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.SEQUENCING_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.EJB_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.DMS_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.EJB_OR_METADATA_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.JPA_METAMODEL_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.WEAVER_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.PROPERTIES_CATEGORY_LOGGING_PROPERTY);
				propertyNames.add(EclipseLinkLogging2_0.SERVER_CATEGORY_LOGGING_PROPERTY);
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
				return this.getSubject().getCategoriesDefaultLevel();
			}

			@Override
			protected String displayString(EclipseLinkLoggingLevel value) {
				switch (value) {
					case all :
						return JptJpaEclipseLinkUiMessages.ECLIPSELINK_CATEGORY_LOGGING_LEVEL_COMPOSITE_ALL;
					case config :
						return JptJpaEclipseLinkUiMessages.ECLIPSELINK_CATEGORY_LOGGING_LEVEL_COMPOSITE_CONFIG;
					case fine :
						return JptJpaEclipseLinkUiMessages.ECLIPSELINK_CATEGORY_LOGGING_LEVEL_COMPOSITE_FINE;
					case finer :
						return JptJpaEclipseLinkUiMessages.ECLIPSELINK_CATEGORY_LOGGING_LEVEL_COMPOSITE_FINER;
					case finest :
						return JptJpaEclipseLinkUiMessages.ECLIPSELINK_CATEGORY_LOGGING_LEVEL_COMPOSITE_FINEST;
					case info :
						return JptJpaEclipseLinkUiMessages.ECLIPSELINK_CATEGORY_LOGGING_LEVEL_COMPOSITE_INFO;
					case off :
						return JptJpaEclipseLinkUiMessages.ECLIPSELINK_CATEGORY_LOGGING_LEVEL_COMPOSITE_OFF;
					case severe :
						return JptJpaEclipseLinkUiMessages.ECLIPSELINK_CATEGORY_LOGGING_LEVEL_COMPOSITE_SEVERE;
					case warning :
						return JptJpaEclipseLinkUiMessages.ECLIPSELINK_CATEGORY_LOGGING_LEVEL_COMPOSITE_WARNING;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected EclipseLinkLoggingLevel getValue() {
				return this.getSubject().getLevel(category);
			}

			@Override
			protected void setValue(EclipseLinkLoggingLevel value) {
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
