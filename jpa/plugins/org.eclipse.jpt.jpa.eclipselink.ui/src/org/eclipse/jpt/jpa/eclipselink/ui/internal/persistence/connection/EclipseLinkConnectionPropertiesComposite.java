/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkConnection;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 *  ConnectionPropertiesComposite
 */
public class EclipseLinkConnectionPropertiesComposite<T extends EclipseLinkConnection> 
	extends Pane<T>
{
	public EclipseLinkConnectionPropertiesComposite(
					Pane<T> parentComposite, 
					Composite parent) {

		super(parentComposite, parent);
	}

	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
	}

	@Override
	protected Composite addComposite(Composite container) {
		return addTitledGroup(
			container,
			JptJpaEclipseLinkUiMessages.CONNECTION_PROPERTIES_COMPOSITE_DATABASE_GROUP_BOX,
			2,
			null
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// JTA Data Source
		PropertyValueModel<Boolean> jtaEnabled = this.buildJTADataSourceHolder();
		addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_JTA_DATA_SOURCE_LABEL, jtaEnabled);
		addText(container, this.buildJtaDataSourceHolder(), this.getHelpID(), jtaEnabled);

		// Non-JTA Data Source
		PropertyValueModel<Boolean> nonJtaEnabled = this.buildNonJTADataSourceHolder();
		addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_NON_JTA_DATA_SOURCE_LABEL, nonJtaEnabled);
		addText(container, buildNonJtaDataSourceHolder(), this.getHelpID(), nonJtaEnabled);


		PropertyValueModel<Boolean> enabledModel = buildTransacationTypeResourceLocalEnabledModel();

		// EclipseLink Connection Pool
		EclipseLinkJdbcPropertiesComposite<T> jdbcComposite = new EclipseLinkJdbcPropertiesComposite<T>(this, container, enabledModel);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		jdbcComposite.getControl().setLayoutData(gridData);

		// Exclusive Connections
		EclipseLinkJdbcExclusiveConnectionsPropertiesComposite<T> exclusiveConnectionsComposite = new EclipseLinkJdbcExclusiveConnectionsPropertiesComposite<T>(this, container, enabledModel);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		exclusiveConnectionsComposite.getControl().setLayoutData(gridData);
	}



	private PropertyValueModel<Boolean> buildTransacationTypeResourceLocalEnabledModel() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(this.buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform(PersistenceUnitTransactionType value) {
				return Boolean.valueOf(value == PersistenceUnitTransactionType.RESOURCE_LOCAL);
			}
		};
	}
	
	private ModifiablePropertyValueModel<String> buildJtaDataSourceHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, String>(buildPersistenceUnitHolder(), PersistenceUnit.JTA_DATA_SOURCE_PROPERTY) {
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

	private PropertyValueModel<PersistenceUnit> buildPersistenceUnitHolder() {
		return new PropertyAspectAdapter<EclipseLinkConnection, PersistenceUnit>(getSubjectHolder()) {
			@Override
			protected PersistenceUnit buildValue_() {
				return this.subject.getPersistenceUnit();
			}
		};
	}
}
