/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.orm;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.OrphanRemovalHolder2_0;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkJoinFetchComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkOneToManyJoiningStrategyPane;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkOneToManyMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkPrivateOwnedComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.OrderingComposite;
import org.eclipse.jpt.ui.internal.details.TargetEntityComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmMappingNameChooser;
import org.eclipse.jpt.ui.internal.jpa2.details.Ordering2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.OrphanRemoval2_0Composite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;


/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TargetEntityComposite                                                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EclipseLinkOneToManyJoiningStrategyPane                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AccessTypeComposite                                                | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EclipseLinkJoinFetchComposite                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EclipseLinkPrivateOwnedComposite                                | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrphanRemoval2_0Composite                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | CascadeComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrderingComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see OneToManyMapping
 * @see TargetEntityComposite
 * @see EclipseLinkOneToManyJoiningStrategyPane
 * @see AccessTypeComposite
 * @see FetchTypeComposite
 * @see EclipseLinkJoinFetchComposite
 * @see EclipseLinkPrivateOwnedComposite
 * @see OrphanRemoval2_0Composite
 * @see CascadeComposite
 * @see OrderingComposite
 */
public class OrmEclipseLinkOneToManyMapping2_0Composite
	extends EclipseLinkOneToManyMappingComposite<OrmOneToManyMapping>
{
	public OrmEclipseLinkOneToManyMapping2_0Composite(
			PropertyValueModel<? extends OrmOneToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeOneToManySection(Composite container) {
		new TargetEntityComposite(this, container);
		new OrmMappingNameChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, this.buildAccessHolderHolder(), container);
		new FetchTypeComposite(this, container);
		new EclipseLinkJoinFetchComposite(this, this.buildJoinFetchableHolder(), container);
		new EclipseLinkPrivateOwnedComposite(this, this.buildPrivateOwnableHolder(), container);
		new OrphanRemoval2_0Composite(this, this.buildOrphanRemovableHolder(), container);
		new CascadeComposite(this, this.buildCascadeHolder(), this.addSubPane(container, 5));
	}

	@Override
	protected void initializeOrderingCollapsibleSection(Composite container) {
		new Ordering2_0Composite(this, container);
	}

	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<OneToManyMapping, AccessHolder>(this.getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
	
	protected PropertyValueModel<OrphanRemovable2_0> buildOrphanRemovableHolder() {
		return new PropertyAspectAdapter<OrmOneToManyMapping, OrphanRemovable2_0>(this.getSubjectHolder()) {
			@Override
			protected OrphanRemovable2_0 buildValue_() {
				return ((OrphanRemovalHolder2_0) this.subject).getOrphanRemoval();
			}
		};
	}
}
