/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Connection;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 *  JdbcPropertiesComposite
 */
public class JdbcPropertiesComposite<T extends Connection> 
	extends Pane<T>
{
	public JdbcPropertiesComposite(Pane<T> parentComposite, Composite parent, PropertyValueModel<Boolean> enabledModel) {

		super(parentComposite, parent, enabledModel);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addTitledGroup(
			parent,
			JptJpaEclipseLinkUiMessages.JDBC_PROPERTIES_COMPOSITE_ECLIPSELINK_CONNECTION_POOL_GROUP_BOX,
			2,
			null
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Connection Properties
		JdbcConnectionPropertiesComposite<T> connectionComposite = new JdbcConnectionPropertiesComposite<T>(this, container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		connectionComposite.getControl().setLayoutData(gridData);

		// Read Connection
		Section readConnectionSection = this.addReadConnectionSection(container);
		gridData = new GridData();
		gridData.verticalAlignment = SWT.TOP;
		readConnectionSection.setLayoutData(gridData);
		
		// Write Connection
		Section writeConnectionSection = this.addWriteConnectionSection(container);
		gridData = new GridData();
		gridData.horizontalIndent = 15;
		gridData.verticalAlignment = SWT.TOP;
		writeConnectionSection.setLayoutData(gridData);
	}

	protected Section addReadConnectionSection(Composite container) {
		Section readConnectionSection = this.getWidgetFactory().createSection(container, 
				ExpandableComposite.TWISTIE | 
				ExpandableComposite.EXPANDED |
				ExpandableComposite.CLIENT_INDENT);
		readConnectionSection.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_READ_CONNECTIONS_SECTION_TITLE);
		
		Composite readConnectionClient = this.getWidgetFactory().createComposite(readConnectionSection);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth  = 0;
		readConnectionClient.setLayout(gridLayout);
		readConnectionClient.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Read Connections Shared
		TriStateCheckBox sharedCheckBox = this.addTriStateCheckBoxWithDefault(
				readConnectionClient,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_read_Connections_Shared_Label,
			this.buildReadConnectionsSharedHolder(),
			this.buildReadConnectionsSharedStringHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		sharedCheckBox.getCheckBox().setLayoutData(gridData);
		
		// Read Connections Minimum
		this.addLabel(readConnectionClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_READ_CONNECTIONS_MIN_LABEL);
		this.addReadConnectionsMinCombo(readConnectionClient);

		// Read Connections Maximum
		this.addLabel(readConnectionClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_READ_CONNECTIONS_MAX_LABEL);
		this.addReadConnectionsMaxCombo(readConnectionClient);
		
		readConnectionSection.setClient(readConnectionClient);

		return readConnectionSection;
	}

	protected Section addWriteConnectionSection(Composite container) {
		Section writeConnectionSection = this.getWidgetFactory().createSection(container, 
				ExpandableComposite.TWISTIE | 
				ExpandableComposite.EXPANDED |
				ExpandableComposite.CLIENT_INDENT);
		writeConnectionSection.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_WRITE_CONNECTIONS_SECTION_TITLE);
		
		Composite writeConnectionClient = this.getWidgetFactory().createComposite(writeConnectionSection);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth  = 0;
		writeConnectionClient.setLayout(gridLayout);
		writeConnectionClient.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Write Connections Minimum
		this.addLabel(writeConnectionClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_WRITE_CONNECTIONS_MIN_LABEL);
		this.addWriteConnectionsMinCombo(writeConnectionClient);

		// Write Connections Maximum
		this.addLabel(writeConnectionClient, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_WRITE_CONNECTIONS_MAX_LABEL);
		this.addWriteConnectionsMaxCombo(writeConnectionClient);
		
		writeConnectionSection.setClient(writeConnectionClient);

		return writeConnectionSection;
	}

	private ModifiablePropertyValueModel<Boolean> buildReadConnectionsSharedHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(getSubjectHolder(), Connection.READ_CONNECTIONS_SHARED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getReadConnectionsShared();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setReadConnectionsShared(value);
			}
		};
	}

	private PropertyValueModel<String> buildReadConnectionsSharedStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultReadConnectionsSharedHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_READ_CONNECTIONS_SHARED_LABEL_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_read_Connections_Shared_Label;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultReadConnectionsSharedHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(
			getSubjectHolder(),
			Connection.READ_CONNECTIONS_SHARED_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getReadConnectionsShared() != null) {
					return null;
				}
				return this.subject.getDefaultReadConnectionsShared();
			}
		};
	}

	
	private void addReadConnectionsMinCombo(Composite container) {
		new IntegerCombo<Connection>(this, container) {
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultReadConnectionsMin();
					}
				};
			}
			
			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder(), Connection.READ_CONNECTIONS_MIN_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getReadConnectionsMin();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setReadConnectionsMin(value);
					}
				};
			}
		};
	}

	private void addReadConnectionsMaxCombo(Composite container) {
		new IntegerCombo<Connection>(this, container) {
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultReadConnectionsMax();
					}
				};
			}
			
			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder(), Connection.READ_CONNECTIONS_MAX_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getReadConnectionsMax();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setReadConnectionsMax(value);
					}
				};
			}
		};
	}

	private void addWriteConnectionsMinCombo(Composite container) {
		new IntegerCombo<Connection>(this, container) {
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultWriteConnectionsMin();
					}
				};
			}
			
			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder(), Connection.WRITE_CONNECTIONS_MIN_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getWriteConnectionsMin();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setWriteConnectionsMin(value);
					}
				};
			}
		};
	}
	private void addWriteConnectionsMaxCombo(Composite container) {
		new IntegerCombo<Connection>(this, container) {
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultWriteConnectionsMax();
					}
				};
			}
			
			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder(), Connection.WRITE_CONNECTIONS_MAX_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getWriteConnectionsMax();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setWriteConnectionsMax(value);
					}
				};
			}
		};
	}
}