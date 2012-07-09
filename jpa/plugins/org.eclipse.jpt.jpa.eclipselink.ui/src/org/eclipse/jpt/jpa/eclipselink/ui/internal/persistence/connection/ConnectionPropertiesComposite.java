/*******************************************************************************
* Copyright (c) 2008, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Connection;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 *  ConnectionPropertiesComposite
 */
public class ConnectionPropertiesComposite<T extends Connection> 
	extends Pane<T>
{
	public ConnectionPropertiesComposite(
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
			EclipseLinkUiMessages.ConnectionPropertiesComposite_Database_GroupBox,
			2,
			null
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// JTA Data Source
		PropertyValueModel<Boolean> jtaEnabled = this.buildJTADataSourceHolder();
		addLabel(container, EclipseLinkUiMessages.PersistenceXmlConnectionTab_jtaDataSourceLabel, jtaEnabled);
		addText(container, this.buildJtaDataSourceHolder(), this.getHelpID(), jtaEnabled);

		// Non-JTA Data Source
		PropertyValueModel<Boolean> nonJtaEnabled = this.buildNonJTADataSourceHolder();
		addLabel(container, EclipseLinkUiMessages.PersistenceXmlConnectionTab_nonJtaDataSourceLabel, nonJtaEnabled);
		addText(container, buildNonJtaDataSourceHolder(), this.getHelpID(), nonJtaEnabled);


		PropertyValueModel<Boolean> enabledModel = buildTransacationTypeResourceLocalEnabledModel();

		// EclipseLink Connection Pool
		JdbcPropertiesComposite<T> jdbcComposite = new JdbcPropertiesComposite<T>(this, container, enabledModel);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		jdbcComposite.getControl().setLayoutData(gridData);

		// Exclusive Connections
		JdbcExclusiveConnectionsPropertiesComposite<T> exclusiveConnectionsComposite = new JdbcExclusiveConnectionsPropertiesComposite<T>(this, container, enabledModel);
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
		return new PropertyAspectAdapter<Connection, PersistenceUnit>(getSubjectHolder()) {
			@Override
			protected PersistenceUnit buildValue_() {
				return this.subject.getPersistenceUnit();
			}
		};
	}

}