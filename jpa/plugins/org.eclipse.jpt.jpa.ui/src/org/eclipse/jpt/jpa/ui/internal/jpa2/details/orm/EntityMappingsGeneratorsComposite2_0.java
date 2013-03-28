/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.ui.internal.details.GeneratorComposite;
import org.eclipse.jpt.jpa.ui.internal.details.GeneratorComposite.GeneratorBuilder;
import org.eclipse.jpt.jpa.ui.internal.details.orm.EntityMappingsGeneratorsComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.SequenceGeneratorComposite2_0;
import org.eclipse.swt.widgets.Composite;

/**
 *  EntityMappingsGenerators2_0Composite
 */
public class EntityMappingsGeneratorsComposite2_0 extends EntityMappingsGeneratorsComposite
{
	/**
	 * Creates a new <code>EntityMappingsGenerators2_0Composite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EntityMappingsGeneratorsComposite2_0(
						Pane<? extends EntityMappings> parentPane,
						Composite parent) {
	
		super(parentPane, parent);
	}

	@Override
	protected GeneratorComposite<SequenceGenerator> buildSequenceGeneratorComposite(
			Composite parent,
			PropertyValueModel<SequenceGenerator> sequenceGeneratorHolder,
			GeneratorBuilder<SequenceGenerator> generatorBuilder) {

		return new SequenceGeneratorComposite2_0(
			this,
			sequenceGeneratorHolder,
			parent,
			generatorBuilder
		);
	}
}
