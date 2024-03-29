/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.ui.internal.details.IdMappingGenerationComposite;
import org.eclipse.jpt.jpa.ui.internal.details.SequenceGeneratorComposite;
import org.eclipse.jpt.jpa.ui.internal.details.GeneratorComposite.GeneratorBuilder;
import org.eclipse.swt.widgets.Composite;

/**
 *  IdMappingGeneration2_0Composite
 */
public class IdMappingGenerationComposite2_0 extends IdMappingGenerationComposite
{
	public IdMappingGenerationComposite2_0(Pane<? extends IdMapping> parentPane, Composite parent) {
		super(parentPane, parent);
	}

	@Override
	protected SequenceGeneratorComposite buildSequenceGeneratorComposite(
		Composite container, 
		PropertyValueModel<SequenceGenerator> sequenceGeneratorHolder,
		GeneratorBuilder<SequenceGenerator> sequenceGeneratorBuilder) {

		return new SequenceGeneratorComposite2_0(
			this,
			sequenceGeneratorHolder,
			container,
			sequenceGeneratorBuilder
		);
	}
}
