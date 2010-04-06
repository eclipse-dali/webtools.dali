/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.persistence.connection;

import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.ui.internal.jpa2.persistence.JptUiPersistence2_0Messages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 *  GenericPersistenceUnit2_0ConnectionComposite
 */
public class GenericPersistenceUnit2_0ConnectionComposite extends Pane<JpaConnection2_0>
{
	public GenericPersistenceUnit2_0ConnectionComposite(
															Pane<JpaConnection2_0> subjectHolder,
															Composite container) {
		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = this.getGroupBoxMargin() * 2;

		container = this.addSection(
			container,
			JptUiPersistence2_0Messages.GenericPersistenceUnit2_0ConnectionComposite_sectionTitle,
			JptUiPersistence2_0Messages.GenericPersistenceUnit2_0ConnectionComposite_sectionDescription
		);

		Composite subPane = this.addSubPane(
			container,
			0, groupBoxMargin, 10, groupBoxMargin
		);

		new TransactionTypeComposite(this, subPane);

		new ConnectionPropertiesComposite(this, container);
	}
	
}
