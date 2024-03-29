/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence;

import java.util.Collection;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.jpa2.persistence.JptJpaUiPersistenceMessages2_0;
import org.eclipse.jpt.jpa.ui.persistence.JptJpaUiPersistenceMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class PersistenceUnitConnectionEditorPage
	extends Pane<PersistenceUnit>
{
	public PersistenceUnitConnectionEditorPage(
			PropertyValueModel<PersistenceUnit> persistenceUnitModel,
            Composite parentComposite,
            WidgetFactory widgetFactory,
            ResourceManager resourceManager) {
		super(persistenceUnitModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiPersistenceMessages2_0.PERSISTENCE_UNIT_CONNECTION_COMPOSITE_SECTION_TITLE);
		section.setDescription(JptJpaUiPersistenceMessages2_0.PERSISTENCE_UNIT_CONNECTION_COMPOSITE_SECTION_DESCRIPTION);

		Composite client = this.getWidgetFactory().createComposite(section);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;
		client.setLayout(layout);
		client.setLayoutData(new GridData(GridData.FILL_BOTH));
		section.setClient(client);

		// Transaction Type widgets
		this.addLabel(client, JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CONNECTION_GENERAL_COMPOSITE_TRANSACTION_TYPE);
		this.buildTransactionTypeCombo(client).getControl();

		Group databaseGroup = this.addTitledGroup(client, JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CONNECTION_COMPOSITE_database, 2, JpaHelpContextIds.PERSISTENCE_XML_CONNECTION);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		databaseGroup.setLayoutData(gridData);

		// JTA Datasource Name widgets
		PropertyValueModel<Boolean> enabled = this.buildJTADatasourceNameBooleanHolder();
		this.addLabel(
			databaseGroup, 
			JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CONNECTION_DATABASE_COMPOSITE_JTA_DATASOURCE_NAME,
			enabled
		);
		this.addText(
			databaseGroup,
			buildJTADatasourceNameHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION,
			enabled
		);


		// Non-JTA Datasource Name widgets
		enabled = this.buildNonJTADatasourceNameBooleanHolder();
		this.addLabel(
			databaseGroup, 
			JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CONNECTION_DATABASE_COMPOSITE_NON_JTA_DATASOURCE_NAME,
			enabled
		);
		this.addText(
			databaseGroup,
			buildNonJTADatasourceNameHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION,
			enabled
		);
	}

	private EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType> buildTransactionTypeCombo(Composite container) {

		return new EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnit.SPECIFIED_TRANSACTION_TYPE_PROPERTY);
				propertyNames.add(PersistenceUnit.DEFAULT_TRANSACTION_TYPE_PROPERTY);
			}

			@Override
			protected PersistenceUnitTransactionType[] getChoices() {
				return PersistenceUnitTransactionType.values();
			}

			@Override
			protected PersistenceUnitTransactionType getDefaultValue() {
				return getSubject().getDefaultTransactionType();
			}

			@Override
			protected String displayString(PersistenceUnitTransactionType value) {
				switch (value) {
					case JTA :
						return JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CONNECTION_GENERAL_COMPOSITE_JTA;
					case RESOURCE_LOCAL :
						return JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CONNECTION_GENERAL_COMPOSITE_RESOURCE_LOCAL;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected PersistenceUnitTransactionType getValue() {
				return getSubject().getSpecifiedTransactionType();
			}

			@Override
			protected void setValue(PersistenceUnitTransactionType value) {
				getSubject().setSpecifiedTransactionType(value);
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}
		};
	}

	private PropertyValueModel<Boolean> buildJTADatasourceNameBooleanHolder() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform_(PersistenceUnitTransactionType value) {
				return Boolean.valueOf(value == PersistenceUnitTransactionType.JTA);
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildJTADatasourceNameHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, String>(getSubjectHolder(), PersistenceUnit.JTA_DATA_SOURCE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getJtaDataSource();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setJtaDataSource(value);
			}
		};
	}

	private PropertyValueModel<Boolean> buildNonJTADatasourceNameBooleanHolder() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform_(PersistenceUnitTransactionType value) {
				return Boolean.valueOf(value == PersistenceUnitTransactionType.RESOURCE_LOCAL);
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildNonJTADatasourceNameHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, String>(getSubjectHolder(), PersistenceUnit.NON_JTA_DATA_SOURCE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getNonJtaDataSource();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setNonJtaDataSource(value);
			}
		};
	}

	private PropertyValueModel<PersistenceUnitTransactionType> buildTransactionTypeHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, PersistenceUnitTransactionType>(
			getSubjectHolder(),
			PersistenceUnit.DEFAULT_TRANSACTION_TYPE_PROPERTY,
			PersistenceUnit.SPECIFIED_TRANSACTION_TYPE_PROPERTY)
		{
			@Override
			protected PersistenceUnitTransactionType buildValue_() {
				return subject.getTransactionType();
			}
		};
	}
}