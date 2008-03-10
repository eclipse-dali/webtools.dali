/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.persistence.details;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | Description                                                               |
 * |                                                                           |
 * |                          ------------------------------------------------ |
 * | JTA Datasource Name:     | I                                            | |
 * |                          ------------------------------------------------ |
 * |                          ------------------------------------------------ |
 * | Non-JTA Datasource Name: | I                                            | |
 * |                          ------------------------------------------------ |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see PersistenceUnit
 * @see PersistenceUnitConnectionComposite - The parent container
 *
 * @version 2.0
 * @since 2.0
 */
public class PersistenceUnitConnectionDatabaseComposite extends AbstractPane<PersistenceUnit>
{
	/**
	 * Creates a new <code>PersistenceUnitConnectionDatabaseComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PersistenceUnitConnectionDatabaseComposite(AbstractPane<PersistenceUnit> subjectHolder,
	                                                  Composite container) {

		super(subjectHolder, container, false);
	}

	private PropertyValueModel<Boolean> buildJTADatasourceNameBooleanHolder() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform_(PersistenceUnitTransactionType value) {
				return value == PersistenceUnitTransactionType.JTA;
			}
		};
	}

	private WritablePropertyValueModel<String> buildJTADatasourceNameHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, String>(getSubjectHolder(), PersistenceUnit.JTA_DATA_SOURCE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getJtaDataSource();
			}

			@Override
			protected void setValue_(String value) {
				subject.setJtaDataSource(value);
			}
		};
	}

	private PropertyValueModel<Boolean> buildNonJTADatasourceNameBooleanHolder() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform_(PersistenceUnitTransactionType value) {
				return value == PersistenceUnitTransactionType.RESOURCE_LOCAL;
			}
		};
	}

	private WritablePropertyValueModel<String> buildNonJTADatasourceNameHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, String>(getSubjectHolder(), PersistenceUnit.NON_JTA_DATA_SOURCE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getNonJtaDataSource();
			}

			@Override
			protected void setValue_(String value) {
				subject.setNonJtaDataSource(value);
			}
		};
	}

	private PropertyValueModel<PersistenceUnitTransactionType> buildTransactionTypeHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, PersistenceUnitTransactionType>(
			getSubjectHolder(),
			PersistenceUnit.DEFAULT_TRANSACTION_TYPE_PROPERTY,
			PersistenceUnit.TRANSACTION_TYPE_PROPERTY)
		{
			@Override
			protected PersistenceUnitTransactionType buildValue_() {
				return subject.getTransactionType();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Description
		buildMultiLineLabel(
			container,
			JptUiPersistenceMessages.PersistenceUnitConnectionDatabaseComposite_description
		);

		// JTA Datasource Name widgets
		Text text = buildLabeledText(
			container,
			JptUiPersistenceMessages.PersistenceUnitConnectionDatabaseComposite_jtaDatasourceName,
			buildJTADatasourceNameHolder()
		);

		installJTADatasourceNameEnabler(text);

		// Non-JTA Datasource Name widgets
		buildLabeledText(
			container,
			JptUiPersistenceMessages.PersistenceUnitConnectionDatabaseComposite_nonJtaDatasourceName,
			buildNonJTADatasourceNameHolder()
		);

		installNonJTADatasourceNameEnabler(text);
	}

	private void installJTADatasourceNameEnabler(Text text) {
		new ControlEnabler(buildJTADatasourceNameBooleanHolder(), text);
	}

	private void installNonJTADatasourceNameEnabler(Text text) {
		new ControlEnabler(buildNonJTADatasourceNameBooleanHolder(), text);
	}
}
