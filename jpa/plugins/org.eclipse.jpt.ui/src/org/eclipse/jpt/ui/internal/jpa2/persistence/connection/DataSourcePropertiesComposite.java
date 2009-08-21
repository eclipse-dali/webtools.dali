/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.persistence.connection;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.jpa2.Jpt2_0UiMessages;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 *  DataSourcePropertiesComposite
 */
public class DataSourcePropertiesComposite extends Pane<JpaConnection2_0>
{
	/**
	 * Creates a new <code>DataSourcePropertiesComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public DataSourcePropertiesComposite(Pane<JpaConnection2_0> parentComposite,
	                                      Composite parent) {

		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<String> buildJtaDataSourceHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, String>(this.buildPersistenceUnitHolder(), PersistenceUnit.JTA_DATA_SOURCE_PROPERTY) {
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
				return value == null || value == PersistenceUnitTransactionType.JTA;
			}
		};
	}

	private WritablePropertyValueModel<String> buildNonJtaDataSourceHolder() {
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
		return new PropertyAspectAdapter<JpaConnection2_0, PersistenceUnit>(getSubjectHolder()) {
			@Override
			protected PersistenceUnit buildValue_() {
				return this.subject.getPersistenceUnit();
			}
		};
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = this.getGroupBoxMargin();

		container = this.addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);

		// JTA Data Source
		Label jtaLabel = this.addUnmanagedLabel(container, Jpt2_0UiMessages.DataSourcePropertiesComposite_jtaDataSourceLabel);
		Text text = this.addUnmanagedText(container, this.buildJtaDataSourceHolder(), this.getHelpID());
		this.addLabeledComposite(container, jtaLabel, text, this.getHelpID());

		this.installJTADataSourceControlEnabler(text, jtaLabel);

		// Non-JTA Data Source
		Label nonJtaLabel = this.addUnmanagedLabel(container, Jpt2_0UiMessages.DataSourcePropertiesComposite_nonJtaDataSourceLabel);
		Text nonJtaText = this.addUnmanagedText(container, this.buildNonJtaDataSourceHolder(), this.getHelpID());
		this.addLabeledComposite(container, nonJtaLabel, nonJtaText, this.getHelpID());

		this.installNonJTADataSourceControlEnabler(nonJtaText, nonJtaLabel);
	}

	private void installJTADataSourceControlEnabler(Text text, Label label) {
		SWTTools.controlEnabledState(this.buildJTADataSourceHolder(), text, label);
	}

	private void installNonJTADataSourceControlEnabler(Text text, Label label) {
		SWTTools.controlEnabledState(this.buildNonJTADataSourceHolder(), text, label);
	}
	
	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;	// TODO - Review for JPA 2.0
	}
}
