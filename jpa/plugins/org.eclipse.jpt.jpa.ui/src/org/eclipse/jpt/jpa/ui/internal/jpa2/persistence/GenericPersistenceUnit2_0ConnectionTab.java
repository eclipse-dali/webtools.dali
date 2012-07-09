/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.persistence;

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
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.jpa.ui.editors.JpaPageComposite;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 *  GenericPersistenceUnit2_0ConnectionTab
 */
public class GenericPersistenceUnit2_0ConnectionTab extends Pane<JpaConnection2_0>
	implements JpaPageComposite
{
	// ********** constructors/initialization **********
	/**
	 * Creates a new <code>GenericPersistenceUnit2_0ConnectionTab</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public GenericPersistenceUnit2_0ConnectionTab(
											PropertyValueModel<JpaConnection2_0> subjectHolder,
	                                        Composite parent,
	                                        WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		container = this.addSection(
			container,
			JptUiPersistence2_0Messages.GenericPersistenceUnit2_0ConnectionComposite_sectionTitle,
			JptUiPersistence2_0Messages.GenericPersistenceUnit2_0ConnectionComposite_sectionDescription
		);
		container.setLayout(new GridLayout(2, false));

		this.addLabel(container, JptUiPersistence2_0Messages.TransactionTypeComposite_transactionTypeLabel);
		this.buildTransactionTypeCombo(container);

		//Connection properties
		Group group = this.addTitledGroup(
			container,
			JptUiPersistence2_0Messages.ConnectionPropertiesComposite_Database_GroupBox
		);
		group.setLayout(new GridLayout(2, false));
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		group.setLayoutData(gridData);

		// JTA Data Source
		PropertyValueModel<Boolean> jtaEnabled = this.buildJTADataSourceHolder();
		this.addLabel(group, JptUiPersistence2_0Messages.DataSourcePropertiesComposite_jtaDataSourceLabel, jtaEnabled);
		this.addText(group, this.buildJtaDataSourceHolder(), JpaHelpContextIds.PERSISTENCE_XML_CONNECTION, jtaEnabled);

		// Non-JTA Data Source
		PropertyValueModel<Boolean> nonJTAEnabled = this.buildNonJTADataSourceHolder();
		this.addLabel(group, JptUiPersistence2_0Messages.DataSourcePropertiesComposite_nonJtaDataSourceLabel, nonJTAEnabled);
		this.addText(group, this.buildNonJtaDataSourceHolder(), JpaHelpContextIds.PERSISTENCE_XML_CONNECTION, nonJTAEnabled);

		
		Group jdbcConnectionPropertiesGroup = this.addTitledGroup(
			group,
			JptUiPersistence2_0Messages.JdbcPropertiesComposite_JdbcConnectionProperties_GroupBox
		);		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		jdbcConnectionPropertiesGroup.setLayoutData(gridData);

		new JdbcConnectionPropertiesComposite(this, jdbcConnectionPropertiesGroup, buildJdbcConnectionPropertiesPaneEnbaledModel());
	}

	private PropertyValueModel<Boolean> buildJdbcConnectionPropertiesPaneEnbaledModel() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform(PersistenceUnitTransactionType value) {
				return value == PersistenceUnitTransactionType.RESOURCE_LOCAL;
			}
		};
	}

	
	// ********** JpaPageComposite implementation **********

	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;	// TODO - Review for JPA 2.0
	}
	
	public ImageDescriptor getPageImageDescriptor() {
		return null;
	}

	public String getPageText() {
		return JptUiPersistence2_0Messages.GenericPersistenceUnit2_0ConnectionTab_title;
	}

	
	// ********** transaction type **********
	private EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType> buildTransactionTypeCombo(Composite container) {

		return new EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType>(this, 
											this.buildPersistenceUnitHolder(), 
											container) {
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
				return this.getSubject().getDefaultTransactionType();
			}

			@Override
			protected String displayString(PersistenceUnitTransactionType value) {
				switch (value) {
					case JTA :
						return JptUiPersistence2_0Messages.TransactionTypeComposite_jta;
					case RESOURCE_LOCAL :
						return JptUiPersistence2_0Messages.TransactionTypeComposite_resource_local;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected PersistenceUnitTransactionType getValue() {
				return this.getSubject().getSpecifiedTransactionType();
			}

			@Override
			protected void setValue(PersistenceUnitTransactionType value) {
				this.getSubject().setSpecifiedTransactionType(value);

				if (value == PersistenceUnitTransactionType.RESOURCE_LOCAL) {
					clearJTAProperties();
				}
				else {
					clearResourceLocalProperties();
				}
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;	// TODO - Review for JPA 2.0
			}
		};
	}

	private PropertyValueModel<PersistenceUnit> buildPersistenceUnitHolder() {
		return new PropertyAspectAdapter<JpaConnection2_0, PersistenceUnit>(this.getSubjectHolder()) {
			@Override
			protected PersistenceUnit buildValue_() {
				return this.subject.getPersistenceUnit();
			}
		};
	}

	private void clearJTAProperties() {
		this.getSubject().getPersistenceUnit().setJtaDataSource(null);
	}

	private void clearResourceLocalProperties() {
		JpaConnection2_0 connection = this.getSubject();
		connection.getPersistenceUnit().setNonJtaDataSource(null);
		connection.setDriver(null);
		connection.setUrl(null);
		connection.setUser(null);
		connection.setPassword(null);
	}


	private ModifiablePropertyValueModel<String> buildJtaDataSourceHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, String>(this.buildPersistenceUnitHolder(), PersistenceUnit.JTA_DATA_SOURCE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getJtaDataSource();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setJtaDataSource(value);
			}
		};
	}

	private PropertyValueModel<Boolean> buildJTADataSourceHolder() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform(PersistenceUnitTransactionType value) {
				return Boolean.valueOf(this.transform2(value));
			}
			private boolean transform2(PersistenceUnitTransactionType value) {
				return value == null || value == PersistenceUnitTransactionType.JTA;
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildNonJtaDataSourceHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, String>(buildPersistenceUnitHolder(), PersistenceUnit.NON_JTA_DATA_SOURCE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getNonJtaDataSource();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setNonJtaDataSource(value);
			}
		};
	}

	private PropertyValueModel<Boolean> buildNonJTADataSourceHolder() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform(PersistenceUnitTransactionType value) {
				return Boolean.valueOf(value == PersistenceUnitTransactionType.RESOURCE_LOCAL);
			}
		};
	}

	private PropertyValueModel<PersistenceUnitTransactionType> buildTransactionTypeHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, PersistenceUnitTransactionType>(
				buildPersistenceUnitHolder(), 
				PersistenceUnit.SPECIFIED_TRANSACTION_TYPE_PROPERTY, 
				PersistenceUnit.DEFAULT_TRANSACTION_TYPE_PROPERTY) {
			@Override
			protected PersistenceUnitTransactionType buildValue_() {
				return this.subject.getTransactionType();
			}
		};
	}
}
