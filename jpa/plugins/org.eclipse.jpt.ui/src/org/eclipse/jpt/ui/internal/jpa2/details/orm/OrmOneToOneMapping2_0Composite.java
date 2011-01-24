/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details.orm;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.jpa2.context.OneToOneRelationship2_0;
import org.eclipse.jpt.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.OrphanRemovalHolder2_0;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.OptionalComposite;
import org.eclipse.jpt.ui.internal.details.TargetEntityComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmMappingNameChooser;
import org.eclipse.jpt.ui.internal.jpa2.details.AbstractOneToOneMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.CascadePane2_0;
import org.eclipse.jpt.ui.internal.jpa2.details.OneToOneJoiningStrategy2_0Pane;
import org.eclipse.jpt.ui.internal.jpa2.details.OrphanRemoval2_0Composite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmOneToOneMapping2_0Composite
	extends AbstractOneToOneMapping2_0Composite<OneToOneMapping, OneToOneRelationship2_0>
{
	public OrmOneToOneMapping2_0Composite(
			PropertyValueModel<? extends OneToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeOneToOneSection(Composite container) {
		new TargetEntityComposite(this, container);
		new OrmMappingNameChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, buildAccessHolderHolder(), container);
		new FetchTypeComposite(this, container);
		new OptionalComposite(this, container);
		new OrphanRemoval2_0Composite(this, buildOrphanRemovableHolder(), container);
		new CascadePane2_0(this, buildCascadeHolder(),  addSubPane(container, 5));
	}

	@Override
	protected void initializeJoiningStrategyCollapsibleSection(Composite container) {
		new OneToOneJoiningStrategy2_0Pane(this, buildJoiningHolder(), container);
	}

	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<OneToOneMapping, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
	
	protected PropertyValueModel<OrphanRemovable2_0> buildOrphanRemovableHolder() {
		return new PropertyAspectAdapter<OneToOneMapping, OrphanRemovable2_0>(getSubjectHolder()) {
			@Override
			protected OrphanRemovable2_0 buildValue_() {
				return ((OrphanRemovalHolder2_0) this.subject).getOrphanRemoval();
			}
		};
	}
}
