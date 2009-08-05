/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.java.details;

import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.ui.internal.mappings.details.GenerationComposite;
import org.eclipse.jpt.ui.internal.mappings.details.SequenceGeneratorComposite;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  Generation2_0Composite
 */
public class Generation2_0Composite extends GenerationComposite
{

	public Generation2_0Composite(FormPane<? extends IdMapping> parentPane, Composite parent) {
		super(parentPane, parent);
	}

	@Override
	protected SequenceGeneratorComposite buildSequenceGeneratorComposite(
			Composite container, 
			PropertyValueModel<GeneratorContainer> generatorHolder, 
			int topMargin,
			int leftMargin) {

		return new JavaSequenceGenerator2_0Composite(
			this,
			generatorHolder,
			this.addSubPane(container, topMargin, leftMargin)
		);
	}
}
