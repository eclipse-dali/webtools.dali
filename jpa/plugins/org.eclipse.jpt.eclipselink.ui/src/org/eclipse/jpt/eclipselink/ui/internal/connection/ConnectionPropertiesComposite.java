/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.connection;

import org.eclipse.jpt.eclipselink.core.internal.context.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 *  ConnectionPropertiesComposite
 */
public class ConnectionPropertiesComposite extends AbstractPane<Connection>
{
	public ConnectionPropertiesComposite(AbstractPane<Connection> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// group
		Group dataGroup = this.buildTitledPane(container, "");

		WritablePropertyValueModel<DataModel> dataHolder = this.buildDataHolder();
		WritablePropertyValueModel<Boolean> dataSourceHolder = this.buildDataSourceHolder(dataHolder);
		WritablePropertyValueModel<Boolean> jdbcHolder = this.buildJdbcHolder(dataHolder);

		// DataSource radio button
		this.buildRadioButton(
			dataGroup,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_dataSourceLabel,
			dataSourceHolder
		);
		
		DataSourcePropertiesComposite dataSourceComposite = new DataSourcePropertiesComposite(this, dataGroup);

		// JDBC radio button
		this.buildRadioButton(
			dataGroup,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_jdbcLabel,
			jdbcHolder
		);
		
		JdbcPropertiesComposite jdbcComposite = new JdbcPropertiesComposite(this, dataGroup);

		new PaneEnabler(dataSourceHolder, dataSourceComposite);
		new PaneEnabler(jdbcHolder, jdbcComposite);
	}

	private WritablePropertyValueModel<DataModel> buildDataHolder() {
		return new SimplePropertyValueModel<DataModel>(new DataModel());
	}

	private WritablePropertyValueModel<Boolean> buildDataSourceHolder(PropertyValueModel<DataModel> subjectHolder) {
		return new PropertyAspectAdapter<DataModel, Boolean>(subjectHolder, DataModel.DATA_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(subject.isDataSource());
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setDataSource(value.booleanValue());
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildJdbcHolder(PropertyValueModel<DataModel> subjectHolder) {
		return new PropertyAspectAdapter<DataModel, Boolean>(subjectHolder, DataModel.DATA_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(subject.isJdbc());
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setDataSource(!value.booleanValue());
			}
		};
	}

	private class DataModel extends AbstractModel {
		private Boolean isDataSource;
			public static final String DATA_PROPERTY = "dataProperty";

		public DataModel() {
			this(false);
		}
		public DataModel(Boolean isDataSource) {
			this.isDataSource = isDataSource;
		}
		public Boolean isDataSource() {
			return this.isDataSource;
		}
		public Boolean isJdbc() {
			return ! this.isDataSource;
		}
		public void setDataSource(Boolean isDataSource) {
			Object old = this.isDataSource;
			this.isDataSource = isDataSource;
			this.firePropertyChanged(DATA_PROPERTY, old, isDataSource);
		}
	}
}