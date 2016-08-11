/*******************************************************************************
 * Copyright (c) 2010, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkConnection;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkExclusiveConnectionMode;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelAdapter;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelStringTransformer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 *  JdbcExclusiveConnectionsPropertiesComposite
 */
public class EclipseLinkJdbcExclusiveConnectionsPropertiesComposite<T extends EclipseLinkConnection> 
	extends Pane<T>
{
	public EclipseLinkJdbcExclusiveConnectionsPropertiesComposite(Pane<T> parentComposite, Composite parent, PropertyValueModel<Boolean> enabledModel) {

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
			this.buildLazyConnectionModel(),
			this.buildLazyConnectionStringModel(),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		lazyConnectionCheckBox.getCheckBox().setLayoutData(gridData);

	}

	private EnumFormComboViewer<EclipseLinkConnection, EclipseLinkExclusiveConnectionMode> addExclusiveConnectionModeCombo(Composite container) {
		return new EnumFormComboViewer<EclipseLinkConnection, EclipseLinkExclusiveConnectionMode>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkConnection.EXCLUSIVE_CONNECTION_MODE_PROPERTY);
			}

			@Override
			protected EclipseLinkExclusiveConnectionMode[] getChoices() {
				return EclipseLinkExclusiveConnectionMode.values();
			}

			@Override
			protected EclipseLinkExclusiveConnectionMode getDefaultValue() {
				return this.getSubject().getDefaultExclusiveConnectionMode();
			}

			@Override
			protected String displayString(EclipseLinkExclusiveConnectionMode value) {
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
			protected EclipseLinkExclusiveConnectionMode getValue() {
				return this.getSubject().getExclusiveConnectionMode();
			}

			@Override
			protected void setValue(EclipseLinkExclusiveConnectionMode value) {
				this.getSubject().setExclusiveConnectionMode(value);
			}
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}
		};
	}
	
	private ModifiablePropertyValueModel<Boolean> buildLazyConnectionModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkConnection, Boolean>(this.getSubjectHolder(), EclipseLinkConnection.LAZY_CONNECTION_PROPERTY) {
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

	private PropertyValueModel<String> buildLazyConnectionStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultLazyConnectionModel(), LAZY_CONNECTION_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> LAZY_CONNECTION_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_LAZY_CONNECTION_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_LAZY_CONNECTION_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultLazyConnectionModel() {
		return TriStateCheckBoxLabelModelAdapter.adaptSubjectModelAspects(
				this.getSubjectHolder(),
				EclipseLinkConnection.LAZY_CONNECTION_PROPERTY,
				c -> c.getLazyConnection(),
				EclipseLinkConnection.DEFAULT_LAZY_CONNECTION_PROPERTY,
				c -> c.getDefaultLazyConnection()
			);
	}
}
