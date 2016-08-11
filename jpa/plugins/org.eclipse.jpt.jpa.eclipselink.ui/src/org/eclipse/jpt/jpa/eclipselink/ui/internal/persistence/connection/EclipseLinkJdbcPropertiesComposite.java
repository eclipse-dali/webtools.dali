/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkConnection;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelAdapter;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelStringTransformer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 *  JdbcPropertiesComposite
 */
public class EclipseLinkJdbcPropertiesComposite<T extends EclipseLinkConnection> 
	extends Pane<T>
{
	public EclipseLinkJdbcPropertiesComposite(Pane<T> parentComposite, Composite parent, PropertyValueModel<Boolean> enabledModel) {

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
		EclipseLinkJdbcConnectionPropertiesComposite<T> connectionComposite = new EclipseLinkJdbcConnectionPropertiesComposite<>(this, container);
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
			this.buildReadConnectionsSharedModel(),
			this.buildReadConnectionsSharedStringModel(),
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

	private ModifiablePropertyValueModel<Boolean> buildReadConnectionsSharedModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkConnection, Boolean>(getSubjectHolder(), EclipseLinkConnection.READ_CONNECTIONS_SHARED_PROPERTY) {
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

	private PropertyValueModel<String> buildReadConnectionsSharedStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultReadConnectionsSharedModel(), CACHEABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> CACHEABLE_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_READ_CONNECTIONS_SHARED_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_read_Connections_Shared_Label
		);

	private PropertyValueModel<Boolean> buildDefaultReadConnectionsSharedModel() {
		return TriStateCheckBoxLabelModelAdapter.adaptSubjectModelAspects(
				this.getSubjectHolder(),
				EclipseLinkConnection.READ_CONNECTIONS_SHARED_PROPERTY,
				c -> c.getReadConnectionsShared(),
				EclipseLinkConnection.DEFAULT_READ_CONNECTIONS_SHARED_PROPERTY,
				c -> c.getDefaultReadConnectionsShared()
			);
	}

	
	@SuppressWarnings("unused")
	private void addReadConnectionsMinCombo(Composite container) {
		new ReadConnectionsMinCombo(this, container);
	}

	static class ReadConnectionsMinCombo
		extends IntegerCombo<EclipseLinkConnection>
	{
		ReadConnectionsMinCombo(Pane<? extends EclipseLinkConnection> parentPane, Composite parent) {
			super(parentPane, parent);
		}

		@Override
		protected String getHelpId() {
			return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
		}

		@Override
		protected PropertyValueModel<Integer> buildDefaultModel() {
			return PropertyValueModelTools.transform(this.getSubjectHolder(), c -> c.getDefaultReadConnectionsMin());
		}

		@Override
		protected ModifiablePropertyValueModel<Integer> buildSelectedItemModel() {
			return new PropertyAspectAdapterXXXX<EclipseLinkConnection, Integer>(getSubjectHolder(), EclipseLinkConnection.READ_CONNECTIONS_MIN_PROPERTY) {
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
	}

	@SuppressWarnings("unused")
	private void addReadConnectionsMaxCombo(Composite container) {
		new ReadConnectionsMaxCombo(this, container);
	}

	static class ReadConnectionsMaxCombo
		extends IntegerCombo<EclipseLinkConnection>
	{
		ReadConnectionsMaxCombo(Pane<? extends EclipseLinkConnection> parentPane, Composite parent) {
			super(parentPane, parent);
		}

		@Override
		protected String getHelpId() {
			return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
		}

		@Override
		protected PropertyValueModel<Integer> buildDefaultModel() {
			return PropertyValueModelTools.transform(this.getSubjectHolder(), c -> c.getDefaultReadConnectionsMax());
		}

		@Override
		protected ModifiablePropertyValueModel<Integer> buildSelectedItemModel() {
			return new PropertyAspectAdapterXXXX<EclipseLinkConnection, Integer>(getSubjectHolder(), EclipseLinkConnection.READ_CONNECTIONS_MAX_PROPERTY) {
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
	}

	@SuppressWarnings("unused")
	private void addWriteConnectionsMinCombo(Composite container) {
		new WriteConnectionsMinCombo(this, container);
	}

	static class WriteConnectionsMinCombo
		extends IntegerCombo<EclipseLinkConnection>
	{
		WriteConnectionsMinCombo(Pane<? extends EclipseLinkConnection> parentPane, Composite parent) {
			super(parentPane, parent);
		}

		@Override
		protected String getHelpId() {
			return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
		}

		@Override
		protected PropertyValueModel<Integer> buildDefaultModel() {
			return PropertyValueModelTools.transform(this.getSubjectHolder(), c -> c.getDefaultWriteConnectionsMin());
		}

		@Override
		protected ModifiablePropertyValueModel<Integer> buildSelectedItemModel() {
			return new PropertyAspectAdapterXXXX<EclipseLinkConnection, Integer>(getSubjectHolder(), EclipseLinkConnection.WRITE_CONNECTIONS_MIN_PROPERTY) {
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
	}

	@SuppressWarnings("unused")
	private void addWriteConnectionsMaxCombo(Composite container) {
		new WriteConnectionsMaxCombo(this, container);
	}

	static class WriteConnectionsMaxCombo
		extends IntegerCombo<EclipseLinkConnection>
	{
		WriteConnectionsMaxCombo(Pane<? extends EclipseLinkConnection> parentPane, Composite parent) {
			super(parentPane, parent);
		}

		@Override
		protected String getHelpId() {
			return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
		}

		@Override
		protected PropertyValueModel<Integer> buildDefaultModel() {
			return PropertyValueModelTools.transform(this.getSubjectHolder(), c -> c.getDefaultWriteConnectionsMax());
		}

		@Override
		protected ModifiablePropertyValueModel<Integer> buildSelectedItemModel() {
			return new PropertyAspectAdapterXXXX<EclipseLinkConnection, Integer>(getSubjectHolder(), EclipseLinkConnection.WRITE_CONNECTIONS_MAX_PROPERTY) {
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
	}
}
