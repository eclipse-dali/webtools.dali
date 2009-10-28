/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details.orm;

import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.EntityNameComposite;
import org.eclipse.jpt.ui.internal.details.IdClassComposite;
import org.eclipse.jpt.ui.internal.details.TableComposite;
import org.eclipse.jpt.ui.internal.details.orm.AbstractOrmEntityComposite;
import org.eclipse.jpt.ui.internal.details.orm.MetadataCompleteComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmJavaClassChooser;
import org.eclipse.jpt.ui.internal.jpa2.details.Cacheable2_0Pane;
import org.eclipse.jpt.ui.internal.jpa2.details.Generation2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.Entity2_0OverridesComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an ORM entity 2.0.
 *
 * @see OrmEntity
 * @see Entity2_0OverridesComposite
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
	protected void initializeGeneralPane(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();
		
		new OrmJavaClassChooser(this, getSubjectHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin), false);
		new TableComposite(this, container);
		new EntityNameComposite(this, addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
		new AccessTypeComposite(this, buildAccessHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
		new IdClassComposite(this, addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin), false);
		new Cacheable2_0Pane(this, buildCacheableHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
		new MetadataCompleteComposite(this, getSubjectHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
	}
	
	protected PropertyValueModel<Cacheable2_0> buildCacheableHolder() {
		return new PropertyAspectAdapter<OrmEntity, Cacheable2_0>(getSubjectHolder()) {
			@Override
			protected Cacheable2_0 buildValue_() {
				return ((CacheableHolder2_0) this.subject).getCacheable();
			}
		};
	}

	@Override
	protected void addAttributeOverridesComposite(Composite container) {
		new Entity2_0OverridesComposite(this, container);
	}

	@Override
	protected void addGeneratorsComposite(Composite container, PropertyValueModel<GeneratorContainer> generatorContainerHolder) {
		new Generation2_0Composite(this, generatorContainerHolder, container);
	}

}