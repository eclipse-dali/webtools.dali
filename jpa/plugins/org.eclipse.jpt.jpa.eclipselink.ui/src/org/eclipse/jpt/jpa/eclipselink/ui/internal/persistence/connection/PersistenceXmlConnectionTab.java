/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import java.util.Collection;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
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
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.BatchWriting;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Connection;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.editors.JpaPageComposite;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * PersistenceXmlConnectionTab
 */
public class PersistenceXmlConnectionTab<T extends Connection>
	extends Pane<T>
	implements JpaPageComposite
{
	// ********** constructors/initialization **********
	public PersistenceXmlConnectionTab(
				PropertyValueModel<T> subjectHolder, 
				Composite parent, 
				WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	// ********** JpaPageComposite implementation **********

	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
	}

	public ImageDescriptor getPageImageDescriptor() {
		return null;
	}

	public String getPageText() {
		return EclipseLinkUiMessages.PersistenceXmlConnectionTab_title;
	}


	@Override
	protected void initializeLayout(Composite container) {
		container = addSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_sectionTitle,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_sectionDescription
		);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;
		container.setLayout(layout);

		//transaction type
		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlConnectionTab_transactionTypeLabel);
		this.addTransactionTypeCombo(container);

		//batch writing
		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlConnectionTab_batchWritingLabel);
		this.addBatchWritingCombo(container);


		//cache statements
		ModifiablePropertyValueModel<Boolean> cacheStatementsHolder = buildCacheStatementsHolder();
		this.addTriStateCheckBox(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_cacheStatementsLabel,
			cacheStatementsHolder,
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
		IntegerCombo<?> combo = addCacheStatementsSizeCombo(container);

		this.installControlEnabler(cacheStatementsHolder, combo);

		
		TriStateCheckBox nativeSqlCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_nativeSqlLabel,
			this.buildNativeSqlHolder(),
			this.buildNativeSqlStringHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		nativeSqlCheckBox.getCheckBox().setLayoutData(gridData);
	
		ConnectionPropertiesComposite<T> connectionPropertiesComposite = new ConnectionPropertiesComposite<T>(this, container);
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
						return EclipseLinkUiMessages.TransactionTypeComposite_jta;
					case RESOURCE_LOCAL :
						return EclipseLinkUiMessages.TransactionTypeComposite_resource_local;
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
		return new PropertyAspectAdapter<Connection, PersistenceUnit>(getSubjectHolder()) {
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
		Connection connection = this.getSubject();
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

	private EnumFormComboViewer<Connection, BatchWriting> addBatchWritingCombo(Composite container) {
		return new EnumFormComboViewer<Connection, BatchWriting>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Connection.BATCH_WRITING_PROPERTY);
			}

			@Override
			protected BatchWriting[] getChoices() {
				return BatchWriting.values();
			}

			@Override
			protected BatchWriting getDefaultValue() {
				return getSubject().getDefaultBatchWriting();
			}

			@Override
			protected String displayString(BatchWriting value) {
				switch (value) {
					case buffered :
						return EclipseLinkUiMessages.BatchWritingComposite_buffered;
					case jdbc :
						return EclipseLinkUiMessages.BatchWritingComposite_jdbc;
					case none :
						return EclipseLinkUiMessages.BatchWritingComposite_none;
					case oracle_jdbc :
						return EclipseLinkUiMessages.BatchWritingComposite_oracle_jdbc;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected BatchWriting getValue() {
				return getSubject().getBatchWriting();
			}

			@Override
			protected void setValue(BatchWriting value) {
				getSubject().setBatchWriting(value);
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildNativeSqlHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(getSubjectHolder(), Connection.NATIVE_SQL_PROPERTY) {
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
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlConnectionTab_nativeSqlLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlConnectionTab_nativeSqlLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultNativeSqlHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(
			getSubjectHolder(),
			Connection.NATIVE_SQL_PROPERTY)
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
		return new PropertyAspectAdapter<Connection, Boolean>(getSubjectHolder(), Connection.CACHE_STATEMENTS_PROPERTY) {
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
	
	private IntegerCombo<Connection> addCacheStatementsSizeCombo(Composite container) {
		return new IntegerCombo<Connection>(this, container) {
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultCacheStatementsSize();
					}
				};
			}
			
			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder(), Connection.CACHE_STATEMENTS_SIZE_PROPERTY) {
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

	private void installControlEnabler(ModifiablePropertyValueModel<Boolean> cacheStatementsHolder, IntegerCombo<?> combo) {
		SWTTools.controlEnabledState(cacheStatementsHolder, combo.getControl());
	}
}
