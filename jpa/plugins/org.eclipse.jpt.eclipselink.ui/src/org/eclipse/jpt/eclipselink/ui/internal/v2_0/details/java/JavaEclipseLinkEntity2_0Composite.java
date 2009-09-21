/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.java;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.eclipselink.ui.internal.details.java.AbstractJavaEclipseLinkEntityComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.EntityNameComposite;
import org.eclipse.jpt.ui.internal.details.IdClassComposite;
import org.eclipse.jpt.ui.internal.details.TableComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.Entity2_0OverridesComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.Generation2_0Composite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an EclipseLink1.1 Java entity.
 *
 * @see JavaEclipseLinkEntity
 * @see EclipselinkJpaUiFactory - The factory creating this pane
 *
 * @version 2.2
 * @since 2.2
 */
public class JavaEclipseLinkEntity2_0Composite extends AbstractJavaEclipseLinkEntityComposite<JavaEntity>
{
	/**
	 * Creates a new <code>EclipseLinkJavaEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>JavaEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaEclipseLinkEntity2_0Composite(PropertyValueModel<? extends JavaEntity> subjectHolder,
	                           Composite parent,
	                           WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeGeneralPane(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();

		new TableComposite(this, container);
		new EntityNameComposite(this, addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));	
		new AccessTypeComposite(this, buildAccessHolder(), addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));	
		new IdClassComposite(this, addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin), false);
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<JavaEntity, AccessHolder>(
			getSubjectHolder())
		{
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentType();
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