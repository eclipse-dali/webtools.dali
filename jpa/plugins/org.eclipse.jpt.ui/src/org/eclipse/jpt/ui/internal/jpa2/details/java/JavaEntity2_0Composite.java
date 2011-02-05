/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.QueryContainer;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.ui.internal.details.AbstractEntityComposite;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.EntityNameComposite;
import org.eclipse.jpt.ui.internal.details.IdClassComposite;
import org.eclipse.jpt.ui.internal.details.TableComposite;
import org.eclipse.jpt.ui.internal.details.java.JavaInheritanceComposite;
import org.eclipse.jpt.ui.internal.details.java.JavaSecondaryTablesComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.Cacheable2_0Pane;
import org.eclipse.jpt.ui.internal.jpa2.details.Entity2_0OverridesComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.Queries2_0Composite;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for a Java entity.
 *
 * @see JavaEntity
 * @see JavaSecondaryTablesComposite
 *
 * @version 2.3
 * @since 1.0
 */
public class JavaEntity2_0Composite
	extends AbstractEntityComposite<JavaEntity>
{
	/**
	 * Creates a new <code>JavaEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>JavaEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaEntity2_0Composite(
			PropertyValueModel<? extends JavaEntity> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeEntitySection(Composite container) {
		new TableComposite(this, container);
		new EntityNameComposite(this, container);
		new AccessTypeComposite(this, buildAccessHolder(), container);	
		new IdClassComposite(this, buildIdClassReferenceHolder(), container);
		new Cacheable2_0Pane(this, buildCacheableHolder(), container);
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<JavaEntity, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}
	
	protected PropertyValueModel<Cacheable2_0> buildCacheableHolder() {
		return new PropertyAspectAdapter<JavaEntity, Cacheable2_0>(getSubjectHolder()) {
			@Override
			protected Cacheable2_0 buildValue_() {
				return ((CacheableHolder2_0) this.subject).getCacheable();
			}
		};
	}

	@Override
	protected void initializeSecondaryTablesSection(Composite container) {
		new JavaSecondaryTablesComposite(this, container);
	}

	@Override
	protected void initializeInheritanceSection(Composite container) {
		new JavaInheritanceComposite(this, container);
	}

	@Override
	protected void initializeAttributeOverridesSection(Composite container) {
		new Entity2_0OverridesComposite(this, container);
	}

	@Override
	protected void initializeQueriesSection(Composite container, PropertyValueModel<QueryContainer> queryContainerHolder) {
		new Queries2_0Composite(this, queryContainerHolder, container);
	}
}
