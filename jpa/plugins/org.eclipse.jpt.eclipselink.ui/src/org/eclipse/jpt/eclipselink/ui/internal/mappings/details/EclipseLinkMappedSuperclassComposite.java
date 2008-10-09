/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an EclipseLink Java entity.
 *
 * @see EclipseLinkMappedSuperclass
 * @see EclipselinkJpaUiFactory - The factory creating this pane
 *
 * @version 2.1
 * @since 2.1
 */
public abstract class EclipseLinkMappedSuperclassComposite<T extends MappedSuperclass> extends FormPane<T>
                                       implements JpaComposite
{
	/**
	 * Creates a new <code>MappedSuperclassComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkMappedSuperclassComposite(PropertyValueModel<? extends T> subjectHolder,
	                                 Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	protected void initializeCachingPane(Composite container) {

		container = addCollapsableSection(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.EclipseLinkMappedSuperclassComposite_caching
		);

		new CachingComposite(this, buildCachingHolder(), container);
	}

	private PropertyAspectAdapter<T, Caching> buildCachingHolder() {
		return new PropertyAspectAdapter<T, Caching>(
			getSubjectHolder())
		{
			@Override
			protected Caching buildValue_() {
				return ((EclipseLinkMappedSuperclass) this.subject).getCaching();
			}
		};
	}
}