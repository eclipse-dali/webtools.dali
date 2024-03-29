/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import java.util.Collection;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkBatchWriting;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkConnection;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class EclipseLinkPersistenceUnitConnectionEditorPage
	extends Pane<EclipseLinkConnection>
{
	public EclipseLinkPersistenceUnitConnectionEditorPage(
		PropertyValueModel<EclipseLinkConnection> connectionModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager
	) {
		super(connectionModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_SECTION_TITLE);
		section.setDescription(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_SECTION_DESCRIPTION);

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

		//transaction type
		this.addLabel(client, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_TRANSACTION_TYPE_LABEL);
		this.addTransactionTypeCombo(client);

		//batch writing
		this.addLabel(client, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_BATCH_WRITING_LABEL);
		this.addBatchWritingCombo(client);


		//cache statements
		ModifiablePropertyValueModel<Boolean> cacheStatementsHolder = buildCacheStatementsHolder();
		this.addTriStateCheckBox(
				client,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_CACHE_STATEMENTS_LABEL,
			cacheStatementsHolder,
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
		IntegerCombo<?> combo = addCacheStatementsSizeCombo(client);

		this.bindEnabledState(cacheStatementsHolder, combo.getControl());


		TriStateCheckBox nativeSqlCheckBox = this.addTriStateCheckBoxWithDefault(
			client,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_NATIVE_SQL_LABEL,
			this.buildNativeSqlHolder(),
			this.buildNativeSqlStringHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		nativeSqlCheckBox.getCheckBox().setLayoutData(gridData);

		EclipseLinkConnectionPropertiesComposite<EclipseLinkConnection> connectionPropertiesComposite = new EclipseLinkConnectionPropertiesComposite<EclipseLinkConnection>(this, client);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		connectionPropertiesComposite.getControl().setLayoutData(gridData);
	}

	private EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType> addTransactionTypeCombo(Composite container) {
		 return new EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType>(this, this.buildPersistenceUnitHolder(), container) {
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
						return JptJpaEclipseLinkUiMessages.TRANSACTION_TYPE_COMPOSITE_JTA;
					case RESOURCE_LOCAL :
						return JptJpaEclipseLinkUiMessages.TRANSACTION_TYPE_COMPOSITE_RESOURCE_LOCAL;
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
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
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

	private void clearJTAProperties() {
		getSubject().getPersistenceUnit().setJtaDataSource(null);
	}

	private void clearResourceLocalProperties() {
		EclipseLinkConnection connection = this.getSubject();
		connection.getPersistenceUnit().setNonJtaDataSource(null);
		connection.setDriver(null);
		connection.setUrl(null);
		connection.setUser(null);
		connection.setPassword(null);
		connection.setBindParameters(null);
		connection.setWriteConnectionsMax(null);
		connection.setWriteConnectionsMin(null);
		connection.setReadConnectionsMax(null);
		connection.setReadConnectionsMin(null);
		connection.setReadConnectionsShared(null);
		connection.setExclusiveConnectionMode(null);
		connection.setLazyConnection(null);
	}

	private EnumFormComboViewer<EclipseLinkConnection, EclipseLinkBatchWriting> addBatchWritingCombo(Composite container) {
		return new EnumFormComboViewer<EclipseLinkConnection, EclipseLinkBatchWriting>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkConnection.BATCH_WRITING_PROPERTY);
			}

			@Override
			protected EclipseLinkBatchWriting[] getChoices() {
				return EclipseLinkBatchWriting.values();
			}

			@Override
			protected EclipseLinkBatchWriting getDefaultValue() {
				return getSubject().getDefaultBatchWriting();
			}

			@Override
			protected String displayString(EclipseLinkBatchWriting value) {
				switch (value) {
					case buffered :
						return JptJpaEclipseLinkUiMessages.BATCH_WRITING_COMPOSITE_BUFFERED;
					case jdbc :
						return JptJpaEclipseLinkUiMessages.BATCH_WRITING_COMPOSITE_JDBC;
					case none :
						return JptJpaEclipseLinkUiMessages.BATCH_WRITING_COMPOSITE_NONE;
					case oracle_jdbc :
						return JptJpaEclipseLinkUiMessages.BATCH_WRITING_COMPOSITE_ORACLE_JDBC;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected EclipseLinkBatchWriting getValue() {
				return getSubject().getBatchWriting();
			}

			@Override
			protected void setValue(EclipseLinkBatchWriting value) {
				getSubject().setBatchWriting(value);
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildNativeSqlHolder() {
		return new PropertyAspectAdapter<EclipseLinkConnection, Boolean>(getSubjectHolder(), EclipseLinkConnection.NATIVE_SQL_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getNativeSql();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setNativeSql(value);
			}

		};
	}

	private PropertyValueModel<String> buildNativeSqlStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultNativeSqlHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_NATIVE_SQL_LABEL_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_NATIVE_SQL_LABEL;
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultNativeSqlHolder() {
		return new PropertyAspectAdapter<EclipseLinkConnection, Boolean>(
			getSubjectHolder(),
			EclipseLinkConnection.NATIVE_SQL_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getNativeSql() != null) {
					return null;
				}
				return this.subject.getDefaultNativeSql();
			}
		};
	}


	private ModifiablePropertyValueModel<Boolean> buildCacheStatementsHolder() {
		return new PropertyAspectAdapter<EclipseLinkConnection, Boolean>(getSubjectHolder(), EclipseLinkConnection.CACHE_STATEMENTS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getCacheStatements();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setCacheStatements(value);
			}

			@Override
			protected synchronized void subjectChanged() {
				Boolean oldValue = this.getValue();
				super.subjectChanged();
				Boolean newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChanged(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private IntegerCombo<EclipseLinkConnection> addCacheStatementsSizeCombo(Composite container) {
		return new IntegerCombo<EclipseLinkConnection>(this, container) {
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<EclipseLinkConnection, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultCacheStatementsSize();
					}
				};
			}

			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<EclipseLinkConnection, Integer>(getSubjectHolder(), EclipseLinkConnection.CACHE_STATEMENTS_SIZE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getCacheStatementsSize();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setCacheStatementsSize(value);
					}
				};
			}
		};
	}
}
