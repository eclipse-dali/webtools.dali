/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.ui.internal.details.GenerationComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  Generation2_0Composite
 */
public class Generation2_0Composite extends GenerationComposite
{
	
	public Generation2_0Composite(
		Pane<?> parentPane, 
		PropertyValueModel<? extends GeneratorContainer> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void addSequenceGeneratorComposite(Composite container, int topMargin, int leftMargin) {
		new SequenceGenerator2_0Composite(
			this,
			this.buildSequenceGeneratorHolder(),
			this.addSubPane(container, topMargin, leftMargin),
			this.buildSequenceGeneratorBuilder()
		);
	}

}
