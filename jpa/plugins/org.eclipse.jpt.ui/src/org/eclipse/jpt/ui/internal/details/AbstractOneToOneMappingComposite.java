/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.OneToOneRelationship;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractOneToOneMappingComposite<T extends OneToOneMapping, R extends OneToOneRelationship> 
	extends Pane<T>
	implements JpaComposite
{
	protected AbstractOneToOneMappingComposite(
			PropertyValueModel<? extends T> subjectHolder,
	        Composite parent,
	        WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeOneToOneCollapsibleSection(container);
		initializeJoiningStrategyCollapsibleSection(container);
	}
	
	protected void initializeOneToOneCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
			container,
			JptUiDetailsMessages.OneToOneSection_title,
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE)
		);

		this.initializeOneToOneSection(container);
	}

	protected abstract void initializeOneToOneSection(Composite container);

	protected void initializeJoiningStrategyCollapsibleSection(Composite container) {
		new OneToOneJoiningStrategyPane(this, buildJoiningHolder(), container);
	}

	protected Composite addPane(Composite container, int groupBoxMargin) {
		return addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);
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

	protected PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<T, Cascade>(getSubjectHolder()) {
			@Override
			protected Cascade transform_(T value) {
				return value.getCascade();
			}
		};
	}
}
