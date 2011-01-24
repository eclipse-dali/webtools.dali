/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.ManyToOneRelationship;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractManyToOneMappingComposite<T extends ManyToOneMapping, R extends ManyToOneRelationship> 
	extends Pane<T>
	implements JpaComposite
{
	protected AbstractManyToOneMappingComposite(
			PropertyValueModel<? extends T> subjectHolder,
	        Composite parent,
	        WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeManyToOneCollapsibleSection(container);
		initializeJoiningStrategyCollapsibleSection(container);
	}
	
	protected void initializeManyToOneCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
			container,
			JptUiDetailsMessages.ManyToOneSection_title,
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE)
		);

		this.initializeManyToOneSection(container);
	}

	protected abstract void initializeManyToOneSection(Composite container);

	protected void initializeJoiningStrategyCollapsibleSection(Composite container) {
		new ManyToOneJoiningStrategyPane(this, buildJoiningHolder(), container);
	}

	protected PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<T, Cascade>(getSubjectHolder()) {
			@Override
			protected Cascade transform_(T value) {
				return value.getCascade();
			}
		};
	}

	protected PropertyValueModel<R> buildJoiningHolder() {
		return new TransformationPropertyValueModel<T, R>(
				getSubjectHolder()) {
			@SuppressWarnings("unchecked")
			@Override
			protected R transform_(T value) {
				return (R) value.getRelationship();
			}
		};
	}
	
	protected Composite addPane(Composite container, int groupBoxMargin) {
		return addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);
	}
}
