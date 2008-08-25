/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.connection;

import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.eclipselink.core.internal.context.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @version 2.0
 * @since 2.0
 */
public class DataSourcePropertiesComposite extends Pane<Connection> {

	/**
	 * Creates a new <code>DataSourcePropertiesComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public DataSourcePropertiesComposite(Pane<Connection> parentComposite,
	                                      Composite parent) {

		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<String> buildJtaDataSourceHolder() {
		return new PropertyAspectAdapter<Connection, String>(getSubjectHolder(), Connection.JTA_DATA_SOURCE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getJtaDataSource();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setJtaDataSource(value);
			}
		};
	}

	private PropertyValueModel<Boolean> buildJTADataSourceHolder() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform(PersistenceUnitTransactionType value) {
				return value == null || value == PersistenceUnitTransactionType.JTA;
			}
		};
	}

	private WritablePropertyValueModel<String> buildNonJtaDataSourceHolder() {
		return new PropertyAspectAdapter<Connection, String>(getSubjectHolder(), Connection.NON_JTA_DATA_SOURCE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getNonJtaDataSource();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setNonJtaDataSource(value);
			}
		};
	}

	private PropertyValueModel<Boolean> buildNonJTADataSourceHolder() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform(PersistenceUnitTransactionType value) {
				return value == PersistenceUnitTransactionType.RESOURCE_LOCAL;
			}
		};
	}

	private PropertyValueModel<PersistenceUnitTransactionType> buildTransactionTypeHolder() {
		return new PropertyAspectAdapter<Connection, PersistenceUnitTransactionType>(getSubjectHolder(), Connection.TRANSACTION_TYPE_PROPERTY) {
			@Override
			protected PersistenceUnitTransactionType buildValue_() {
				return subject.getTransactionType();
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();

		container = addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);

		// JTA Data Source
		Label jtaLabel = addUnmanagedLabel(container, EclipseLinkUiMessages.PersistenceXmlConnectionTab_jtaDataSourceLabel);
		Text text = addUnmanagedText(container, buildJtaDataSourceHolder(), null);
		this.addLabeledComposite(container, jtaLabel, text, null);

		this.installJTADataSourceControlEnabler(text, jtaLabel);

		// Non-JTA Data Source
		Label nonJtaLabel = addUnmanagedLabel(container, EclipseLinkUiMessages.PersistenceXmlConnectionTab_nonJtaDataSourceLabel);
		Text nonJtaText = addUnmanagedText(container, buildNonJtaDataSourceHolder(), null);
		this.addLabeledComposite(container, nonJtaLabel, nonJtaText, null);

		this.installNonJTADataSourceControlEnabler(nonJtaText, nonJtaLabel);
	}

	private void installJTADataSourceControlEnabler(Text text, Label label) {
		new ControlEnabler(buildJTADataSourceHolder(), text, label);
	}

	private void installNonJTADataSourceControlEnabler(Text text, Label label) {
		new ControlEnabler(buildNonJTADataSourceHolder(), text, label);
	}
}