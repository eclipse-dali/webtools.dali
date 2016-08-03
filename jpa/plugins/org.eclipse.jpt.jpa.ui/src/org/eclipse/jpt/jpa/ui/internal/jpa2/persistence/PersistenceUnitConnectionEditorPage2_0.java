/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.persistence;

import java.util.Collection;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.Connection2_0;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.jpa2.persistence.JptJpaUiPersistenceMessages2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class PersistenceUnitConnectionEditorPage2_0
	extends Pane<Connection2_0>
{
	public PersistenceUnitConnectionEditorPage2_0(
			PropertyValueModel<Connection2_0> subjectModel,
            Composite parent,
            WidgetFactory widgetFactory,
            ResourceManager resourceManager) {
		super(subjectModel, parent, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unused")
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

		this.addLabel(client, JptJpaUiPersistenceMessages2_0.TRANSACTION_TYPE_COMPOSITE_TRANSACTION_TYPE_LABEL);
		this.buildTransactionTypeCombo(client);

		//Connection properties
		Group group = this.addTitledGroup(
				client,
			JptJpaUiPersistenceMessages2_0.CONNECTION_PROPERTIES_COMPOSITE_DATABASE_GROUP_BOX
		);
		group.setLayout(new GridLayout(2, false));
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		group.setLayoutData(gridData);

		// JTA Data Source
		PropertyValueModel<Boolean> jtaEnabled = this.buildJTADataSourceModel();
		this.addLabel(group, JptJpaUiPersistenceMessages2_0.DATA_SOURCE_PROPERTIES_COMPOSITE_JTA_DATA_SOURCE_LABEL, jtaEnabled);
		this.addText(group, this.buildJtaDataSourceModel(), JpaHelpContextIds.PERSISTENCE_XML_CONNECTION, jtaEnabled);

		// Non-JTA Data Source
		PropertyValueModel<Boolean> nonJTAEnabled = this.buildNonJTADataSourceModel();
		this.addLabel(group, JptJpaUiPersistenceMessages2_0.DATA_SOURCE_PROPERTIES_COMPOSITE_NON_JTA_DATA_SOURCE_LABEL, nonJTAEnabled);
		this.addText(group, this.buildNonJtaDataSourceModel(), JpaHelpContextIds.PERSISTENCE_XML_CONNECTION, nonJTAEnabled);

		
		Group jdbcConnectionPropertiesGroup = this.addTitledGroup(
			group,
			JptJpaUiPersistenceMessages2_0.JDBC_PROPERTIES_COMPOSITE_JDBC_CONNECTION_PROPERTIES_GROUP_BOX
		);		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		jdbcConnectionPropertiesGroup.setLayoutData(gridData);

		new JdbcConnectionPropertiesComposite2_0(this, jdbcConnectionPropertiesGroup, buildJdbcConnectionPropertiesPaneEnbaledModel());
	}

	private PropertyValueModel<Boolean> buildJdbcConnectionPropertiesPaneEnbaledModel() {
		return PropertyValueModelTools.valueIsIdentical(this.buildTransactionTypeModel(), PersistenceUnitTransactionType.RESOURCE_LOCAL);
	}



	// ********** transaction type **********
	private EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType> buildTransactionTypeCombo(Composite container) {

		return new EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType>(this, 
											this.buildPersistenceUnitModel(), 
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
						return JptJpaUiPersistenceMessages2_0.TRANSACTION_TYPE_COMPOSITE_JTA;
					case RESOURCE_LOCAL :
						return JptJpaUiPersistenceMessages2_0.TRANSACTION_TYPE_COMPOSITE_RESOURCE_LOCAL;
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

	private PropertyValueModel<PersistenceUnit> buildPersistenceUnitModel() {
		return new PropertyAspectAdapterXXXX<Connection2_0, PersistenceUnit>(this.getSubjectHolder()) {
			@Override
			protected PersistenceUnit buildValue_() {
				return this.subject.getPersistenceUnit();
			}
		};
	}

	void clearJTAProperties() {
		this.getSubject().getPersistenceUnit().setJtaDataSource(null);
	}

	void clearResourceLocalProperties() {
		Connection2_0 connection = this.getSubject();
		connection.getPersistenceUnit().setNonJtaDataSource(null);
		connection.setDriver(null);
		connection.setUrl(null);
		connection.setUser(null);
		connection.setPassword(null);
	}


	private ModifiablePropertyValueModel<String> buildJtaDataSourceModel() {
		return new PropertyAspectAdapterXXXX<PersistenceUnit, String>(this.buildPersistenceUnitModel(), PersistenceUnit.JTA_DATA_SOURCE_PROPERTY) {
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

	private PropertyValueModel<Boolean> buildJTADataSourceModel() {
		return PropertyValueModelTools.valueAffirms_(this.buildTransactionTypeModel(), TRANSACTION_TYPE_IS_JTA);
	}

	private static final Predicate<PersistenceUnitTransactionType> TRANSACTION_TYPE_IS_JTA = new TransactionTypeIsJTA();
	static class TransactionTypeIsJTA
		extends PredicateAdapter<PersistenceUnitTransactionType>
	{
		@Override
		public boolean evaluate(PersistenceUnitTransactionType type) {
			return (type == null) || (type == PersistenceUnitTransactionType.JTA);
		}
	}

	private ModifiablePropertyValueModel<String> buildNonJtaDataSourceModel() {
		return new PropertyAspectAdapterXXXX<PersistenceUnit, String>(buildPersistenceUnitModel(), PersistenceUnit.NON_JTA_DATA_SOURCE_PROPERTY) {
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

	private PropertyValueModel<Boolean> buildNonJTADataSourceModel() {
		return PropertyValueModelTools.valueIsIdentical(this.buildTransactionTypeModel(), PersistenceUnitTransactionType.RESOURCE_LOCAL);
	}

	private PropertyValueModel<PersistenceUnitTransactionType> buildTransactionTypeModel() {
		return new PropertyAspectAdapterXXXX<PersistenceUnit, PersistenceUnitTransactionType>(
				buildPersistenceUnitModel(), 
				PersistenceUnit.SPECIFIED_TRANSACTION_TYPE_PROPERTY, 
				PersistenceUnit.DEFAULT_TRANSACTION_TYPE_PROPERTY) {
			@Override
			protected PersistenceUnitTransactionType buildValue_() {
				return this.subject.getTransactionType();
			}
		};
	}
}
