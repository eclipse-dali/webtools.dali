/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.IdClassReference;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;


public abstract class AbstractMappedSuperclassComposite<T extends MappedSuperclass>
	extends Pane<T>
    implements JpaComposite
{
	protected AbstractMappedSuperclassComposite(
			PropertyValueModel<? extends T> subjectHolder,
	        Composite parent,
	        WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeMappedSuperclassCollapsibleSection(container);
	}
	
	protected void initializeMappedSuperclassCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
			container,
			JptUiDetailsMessages.MappedSuperclassSection_title,
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE)
		);

		this.initializeMappedSuperclassSection(container);
	}
	
	protected void initializeMappedSuperclassSection(Composite container) {
		new IdClassComposite(this, buildIdClassReferenceHolder(), container);
	}

	protected PropertyValueModel<IdClassReference> buildIdClassReferenceHolder() {
		return new PropertyAspectAdapter<T, IdClassReference>(getSubjectHolder()) {
			@Override
			protected IdClassReference buildValue_() {
				return this.subject.getIdClassReference();
			}
		};
	}
}