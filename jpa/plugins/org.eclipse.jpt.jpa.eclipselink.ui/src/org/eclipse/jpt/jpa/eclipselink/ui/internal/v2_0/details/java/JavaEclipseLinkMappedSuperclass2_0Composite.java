/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AccessHolder;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java.AbstractJavaEclipseLinkMappedSuperclassComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an EclipseLink Java Mapped Superclass.
 *
 * @see EclipseLinkMappedSuperclass
 * @see EclipselinkJpaUiFactory - The factory creating this pane
 *
 * @version 2.3
 * @since 2.3
 */
public class JavaEclipseLinkMappedSuperclass2_0Composite
	extends AbstractJavaEclipseLinkMappedSuperclassComposite
{
	/**
	 * Creates a new <code>MappedSuperclassComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaEclipseLinkMappedSuperclass2_0Composite(
			PropertyValueModel<? extends JavaMappedSuperclass> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeMappedSuperclassSection(Composite container) {
		new AccessTypeComposite(this, buildAccessHolder(), container);	
		new IdClassComposite(this, buildIdClassReferenceHolder(), container);
	}

	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}

	@Override
	protected void initializeCachingSection(Composite container, PropertyValueModel<JavaEclipseLinkCaching> cachingHolder) {
		new JavaEclipseLinkCaching2_0Composite(this, cachingHolder, container);
	}
}