/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import java.util.Collection;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Connection;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.ExclusiveConnectionMode;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 *  JdbcExclusiveConnectionsPropertiesComposite
 */
public class JdbcExclusiveConnectionsPropertiesComposite<T extends Connection> 
	extends Pane<T>
{
	public JdbcExclusiveConnectionsPropertiesComposite(Pane<T> parentComposite, Composite parent, PropertyValueModel<Boolean> enabledModel) {

		super(parentComposite, parent, enabledModel);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addTitledGroup(
			parent,
			JptJpaEclipseLinkUiMessages.JDBC_EXCLUSIVE_CONNECTIONS_PROPERTIES_COMPOSITE_GROUP_BOX,
			2,
			null
		);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Exclusive connection mode
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_EXCLUSIVE_CONNECTION_MODE_LABEL);
		this.addExclusiveConnectionModeCombo(container);

		// Lazy Connection
		TriStateCheckBox lazyConnectionCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_LAZY_CONNECTION_LABEL,
			this.buildLazyConnectionHolder(),
			this.buildLazyConnectionStringHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		lazyConnectionCheckBox.getCheckBox().setLayoutData(gridData);

	}

	private EnumFormComboViewer<Connection, ExclusiveConnectionMode> addExclusiveConnectionModeCombo(Composite container) {
		return new EnumFormComboViewer<Connection, ExclusiveConnectionMode>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Connection.EXCLUSIVE_CONNECTION_MODE_PROPERTY);
			}

			@Override
			protected ExclusiveConnectionMode[] getChoices() {
				return ExclusiveConnectionMode.values();
			}

			@Override
			protected ExclusiveConnectionMode getDefaultValue() {
				return this.getSubject().getDefaultExclusiveConnectionMode();
			}

			@Override
			protected String displayString(ExclusiveConnectionMode value) {
				switch (value) {
					case always :
						return JptJpaEclipseLinkUiMessages.JDBC_EXCLUSIVE_CONNECTION_MODE_COMPOSITE_ALWAYS;
					case isolated :
						return JptJpaEclipseLinkUiMessages.JDBC_EXCLUSIVE_CONNECTION_MODE_COMPOSITE_ISOLATED;
					case transactional :
						return JptJpaEclipseLinkUiMessages.JDBC_EXCLUSIVE_CONNECTION_MODE_COMPOSITE_TRANSACTIONAL;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected ExclusiveConnectionMode getValue() {
				return this.getSubject().getExclusiveConnectionMode();
			}

			@Override
			protected void setValue(ExclusiveConnectionMode value) {
				this.getSubject().setExclusiveConnectionMode(value);
			}
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}
		};
	}
	
	private ModifiablePropertyValueModel<Boolean> buildLazyConnectionHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(this.getSubjectHolder(), Connection.LAZY_CONNECTION_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getLazyConnection();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setLazyConnection(value);
			}
		};
	}

	private PropertyValueModel<String> buildLazyConnectionStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(this.buildDefaultLazyConnectionHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_LAZY_CONNECTION_LABEL_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_LAZY_CONNECTION_LABEL;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultLazyConnectionHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(
			this.getSubjectHolder(),
			Connection.LAZY_CONNECTION_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getLazyConnection() != null) {
					return null;
				}
				return this.subject.getDefaultLazyConnection();
			}
		};
	}
}
