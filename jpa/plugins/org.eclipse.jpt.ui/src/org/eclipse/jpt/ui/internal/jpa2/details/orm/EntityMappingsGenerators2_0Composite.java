/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details.orm;

import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.ui.internal.details.GeneratorComposite;
import org.eclipse.jpt.ui.internal.details.GeneratorComposite.GeneratorBuilder;
import org.eclipse.jpt.ui.internal.details.orm.EntityMappingsGeneratorsComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.SequenceGenerator2_0Composite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  EntityMappingsGenerators2_0Composite
 */
public class EntityMappingsGenerators2_0Composite extends EntityMappingsGeneratorsComposite
{
	/**
	 * Creates a new <code>EntityMappingsGenerators2_0Composite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EntityMappingsGenerators2_0Composite(
						Pane<? extends EntityMappings> parentPane,
						Composite parent) {
	
		super(parentPane, parent);
	}

	@Override
	protected GeneratorComposite<SequenceGenerator> buildSequenceGeneratorComposite(
			Composite parent,
			PropertyValueModel<SequenceGenerator> sequenceGeneratorHolder,
			GeneratorBuilder<SequenceGenerator> generatorBuilder) {

		return new SequenceGenerator2_0Composite(
			this,
			sequenceGeneratorHolder,
			parent,
			generatorBuilder
		);
	}
}