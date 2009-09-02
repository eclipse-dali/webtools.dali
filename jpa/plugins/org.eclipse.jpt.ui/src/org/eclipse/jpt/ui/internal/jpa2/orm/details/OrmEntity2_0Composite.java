/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.orm.details;

import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.BaseJpaUiFactory;
import org.eclipse.jpt.ui.internal.jpa2.mappings.details.Generation2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.mappings.details.Overrides2_0Composite;
import org.eclipse.jpt.ui.internal.orm.details.AbstractOrmEntityComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an ORM entity 2.0.
 *
 * @see OrmEntity
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see Overrides2_0Composite
 */
public class OrmEntity2_0Composite extends AbstractOrmEntityComposite
{
	/**
	 * Creates a new <code>OrmEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>OrmEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmEntity2_0Composite(PropertyValueModel<? extends OrmEntity> subjectHolder,
	                          Composite parent,
	                          WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void addAttributeOverridesComposite(Composite container) {
		new Overrides2_0Composite(this, container);
	}

	@Override
	protected void addGeneratorsComposite(Composite container, PropertyValueModel<GeneratorContainer> generatorContainerHolder) {
		new Generation2_0Composite(this, generatorContainerHolder, container);
	}

}