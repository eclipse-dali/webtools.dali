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

import org.eclipse.jpt.core.internal.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.ui.internal.jpa2.Jpt2_0UiMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.widgets.Composite;

/**
 *  GenericPersistenceUnit2_0ConnectionComposite
 */
public class GenericPersistenceUnit2_0ConnectionComposite extends FormPane<JpaConnection2_0>
{
	public GenericPersistenceUnit2_0ConnectionComposite(
															FormPane<JpaConnection2_0> subjectHolder,
															Composite container) {
		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = this.getGroupBoxMargin() * 2;

		container = this.addSection(
			container,
			Jpt2_0UiMessages.GenericPersistenceUnit2_0ConnectionComposite_sectionTitle,
			Jpt2_0UiMessages.GenericPersistenceUnit2_0ConnectionComposite_sectionDescription
		);

		Composite subPane = this.addSubPane(
			container,
			0, groupBoxMargin, 10, groupBoxMargin
		);

		new TransactionTypeComposite(this, subPane);

		new ConnectionPropertiesComposite(this, container);
	}
	
}
