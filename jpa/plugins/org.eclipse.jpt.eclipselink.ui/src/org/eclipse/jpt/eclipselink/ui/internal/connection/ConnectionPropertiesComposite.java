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
@SuppressWarnings("nls")
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

		DataSourcePropertiesComposite dataSourceComposite = new DataSourcePropertiesComposite(
			this,
			this.buildSubPane(dataGroup, 0, 16)
		);

		// JDBC radio button
		this.buildRadioButton(
			this.buildSubPane(dataGroup, 5),
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_jdbcLabel,
			jdbcHolder
		);

		JdbcPropertiesComposite jdbcComposite = new JdbcPropertiesComposite(
			this,
			this.buildSubPane(dataGroup, 0, 16)
		);

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
				return subject.isDataSource();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setDataSource(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildJdbcHolder(PropertyValueModel<DataModel> subjectHolder) {
		return new PropertyAspectAdapter<DataModel, Boolean>(subjectHolder, DataModel.DATA_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isJdbc();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setDataSource(!value);
			}
		};
	}

	private class DataModel extends AbstractModel {
		private boolean isDataSource;
			public static final String DATA_PROPERTY = "dataProperty";

		public DataModel() {
			this(false);
		}
		public DataModel(Boolean isDataSource) {
			this.isDataSource = isDataSource;
		}
		public boolean isDataSource() {
			return this.isDataSource;
		}
		public boolean isJdbc() {
			return ! this.isDataSource;
		}
		public void setDataSource(boolean isDataSource) {
			Object old = this.isDataSource;
			this.isDataSource = isDataSource;
			this.firePropertyChanged(DATA_PROPERTY, old, isDataSource);

			if (isDataSource) {
				clearJdbcOptions();
			}
			else {
				clearDataSourceOptions();
			}
		}

		private void clearDataSourceOptions() {
			Connection connection = subject();
			connection.setJtaDataSource(null);
			connection.setNonJtaDataSource(null);
		}

		private void clearJdbcOptions() {
			Connection connection = subject();
			connection.setDriver(null);
			connection.setUrl(null);
			connection.setUser(null);
			connection.setPassword(null);
			connection.setBindParameters(null);
			connection.setReadConnectionsMax(null);
			connection.setReadConnectionsMin(null);
			connection.setReadConnectionsShared(null);
			connection.setWriteConnectionsMax(null);
			connection.setWriteConnectionsMin(null);
		}
	}
}