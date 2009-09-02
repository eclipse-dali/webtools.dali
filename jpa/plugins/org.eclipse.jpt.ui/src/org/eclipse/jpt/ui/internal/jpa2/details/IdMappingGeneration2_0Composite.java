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

import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.ui.internal.details.IdMappingGenerationComposite;
import org.eclipse.jpt.ui.internal.details.SequenceGeneratorComposite;
import org.eclipse.jpt.ui.internal.details.GeneratorComposite.GeneratorBuilder;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  IdMappingGeneration2_0Composite
 */
public class IdMappingGeneration2_0Composite extends IdMappingGenerationComposite
{

	public IdMappingGeneration2_0Composite(FormPane<? extends IdMapping> parentPane, Composite parent) {
		super(parentPane, parent);
	}

	@Override
	protected SequenceGeneratorComposite buildSequenceGeneratorComposite(
		Composite container, 
		PropertyValueModel<SequenceGenerator> sequenceGeneratorHolder,
		GeneratorBuilder<SequenceGenerator> sequenceGeneratorBuilder,
		int topMargin,
		int leftMargin) {

		return new SequenceGenerator2_0Composite(
			this,
			sequenceGeneratorHolder,
			this.addSubPane(container, topMargin, leftMargin),
			sequenceGeneratorBuilder
		);
	}
	
	
}
