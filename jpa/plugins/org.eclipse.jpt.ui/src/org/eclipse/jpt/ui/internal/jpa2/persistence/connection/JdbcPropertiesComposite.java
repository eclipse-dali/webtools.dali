/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.persistence.connection;

import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.ui.internal.jpa2.persistence.JptUiPersistence2_0Messages;
import org.eclipse.swt.widgets.Composite;

/**
 *  JdbcPropertiesComposite
 */
public class JdbcPropertiesComposite extends Pane<JpaConnection2_0>
{
	public JdbcPropertiesComposite(Pane<JpaConnection2_0> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform(PersistenceUnitTransactionType value) {
				return value == PersistenceUnitTransactionType.RESOURCE_LOCAL;
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

		container = addTitledGroup(
			addSubPane(container, 10),
			JptUiPersistence2_0Messages.JdbcPropertiesComposite_JdbcConnectionProperties_GroupBox
		);

		new JdbcConnectionPropertiesComposite(this, container);

		this.installPaneEnabler();
	}

	private void installPaneEnabler() {
		new PaneEnabler(buildPaneEnablerHolder(), this);
	}
}