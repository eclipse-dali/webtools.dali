/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence;

import java.util.Collection;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.ui.editors.JpaPageComposite;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.JptUiPersistence2_0Messages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | - General --------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | PersistenceUnitConnectionGeneralComposite                             | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - Database -------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | PersistenceUnitConnectionDatabaseComposite                            | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see PersistenceUnit
 * @see PersistenceUnitConnectionGeneralComposite
 * @see PersistenceUnitConnectionDatabaseComposite
 *
 * @version 2.0
 * @since 2.0
 */
public class PersistenceUnitConnectionTab extends Pane<PersistenceUnit>
                                                implements JpaPageComposite
{
	/**
	 * Creates a new <code>PersistenceUnitConnectionComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PersistenceUnitConnectionTab(PropertyValueModel<PersistenceUnit> subjectHolder,
	                                          Composite container,
	                                          WidgetFactory widgetFactory) {

		super(subjectHolder, container, widgetFactory);
	}

	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
	}


	public ImageDescriptor getPageImageDescriptor() {
		return null;
	}

	public String getPageText() {
		return JptUiPersistenceMessages.PersistenceUnitConnectionComposite_connection;
	}

	@Override
	protected void initializeLayout(Composite container) {
		container = this.addSection(
			container,
			JptUiPersistence2_0Messages.GenericPersistenceUnit2_0ConnectionComposite_sectionTitle,
			JptUiPersistence2_0Messages.GenericPersistenceUnit2_0ConnectionComposite_sectionDescription
		);
		container.setLayout(new GridLayout(2, false));

		// Transaction Type widgets
		this.addLabel(container, JptUiPersistenceMessages.PersistenceUnitConnectionGeneralComposite_transactionType);
		this.buildTransactionTypeCombo(container).getControl();

		Group databaseGroup = this.addTitledGroup(container, JptUiPersistenceMessages.PersistenceUnitConnectionComposite_database, 2, JpaHelpContextIds.PERSISTENCE_XML_CONNECTION);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		databaseGroup.setLayoutData(gridData);

		// JTA Datasource Name widgets
		PropertyValueModel<Boolean> enabled = this.buildJTADatasourceNameBooleanHolder();
		this.addLabel(
			databaseGroup, 
			JptUiPersistenceMessages.PersistenceUnitConnectionDatabaseComposite_jtaDatasourceName,
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
			JptUiPersistenceMessages.PersistenceUnitConnectionDatabaseComposite_nonJtaDatasourceName,
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
						return JptUiPersistenceMessages.PersistenceUnitConnectionGeneralComposite_jta;
					case RESOURCE_LOCAL :
						return JptUiPersistenceMessages.PersistenceUnitConnectionGeneralComposite_resource_local;
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
