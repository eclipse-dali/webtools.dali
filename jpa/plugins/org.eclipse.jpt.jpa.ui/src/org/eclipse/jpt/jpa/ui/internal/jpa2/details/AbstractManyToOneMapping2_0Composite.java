/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneRelationship2_0;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractManyToOneMappingComposite;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractManyToOneMapping2_0Composite<T extends ManyToOneMapping, R extends ManyToOneRelationship2_0>
	extends AbstractManyToOneMappingComposite<T, R>
{
	protected AbstractManyToOneMapping2_0Composite(
			PropertyValueModel<? extends T> subjectHolder,
	        Composite parent,
	        WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeManyToOneCollapsibleSection(container);
		initializeDerivedIdentityCollapsibleSection(container);
		initializeJoiningStrategyCollapsibleSection(container);
	}

	protected void initializeDerivedIdentityCollapsibleSection(Composite container) {
		new DerivedIdentity2_0Pane(this, buildDerivedIdentityHolder(), container);
	}

	@Override
	protected void initializeJoiningStrategyCollapsibleSection(Composite container) {
		new ManyToOneJoiningStrategy2_0Pane(this, buildJoiningHolder(), container);
	}

	protected PropertyValueModel<DerivedIdentity2_0> buildDerivedIdentityHolder() {
		return new PropertyAspectAdapter<T, DerivedIdentity2_0>(getSubjectHolder()) {
			@Override
			protected DerivedIdentity2_0 buildValue_() {
				return ((ManyToOneMapping2_0) this.subject).getDerivedIdentity();
			}
		};
	}
}
