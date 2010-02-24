/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.java;

import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.OrphanRemovalHolder2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToManyMapping2_0;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyRelationshipReference;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.eclipselink.core.v2_0.context.EclipseLinkOneToManyMapping2_0;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkJoinFetchComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkOneToManyJoiningStrategyPane;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkPrivateOwnedComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AbstractOneToManyMappingComposite;
import org.eclipse.jpt.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.OrderingComposite;
import org.eclipse.jpt.ui.internal.details.TargetEntityComposite;
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
 * | | EclipseLinkOneToManyJoiningStrategyPane                  | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EclipseLinkJoinFetchComposite                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EclipseLinkPrivateOwnedComposite                             | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | CascadeComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrphanRemoval2_0Composite                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrderingComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link JavaOneToManyMapping2_0}
 * @see {@link TargetEntityComposite}
 * @see {@link EclipseLinkOneToManyJoiningStrategyPane}
 * @see {@link FetchTypeComposite}
 * @see {@link EclipseLinkJoinFetchComposite}
 * @see {@link EclipseLinkPrivateOwnedComposite}
 * @see {@link CascadeComposite}
 * @see {@link OrphanRemoval2_0Composite}
 * @see {@link OrderingComposite}
 */
public class JavaEclipseLinkOneToManyMapping2_0Composite
	extends AbstractOneToManyMappingComposite<JavaOneToManyMapping, EclipseLinkOneToManyRelationshipReference>
{
	public JavaEclipseLinkOneToManyMapping2_0Composite(
			PropertyValueModel<? extends JavaOneToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = this.getGroupBoxMargin();
		
		new TargetEntityComposite(this, this.addPane(container, groupBoxMargin));
		new EclipseLinkOneToManyJoiningStrategyPane(this, this.buildJoiningHolder(), container);
		new FetchTypeComposite(this, this.addPane(container, groupBoxMargin));
		new EclipseLinkJoinFetchComposite(this, this.buildJoinFetchableHolder(), this.addPane(container, groupBoxMargin));
		new EclipseLinkPrivateOwnedComposite(this, this.buildPrivateOwnableHolder(), this.addPane(container, groupBoxMargin));
		new OrphanRemoval2_0Composite(this, this.buildOrphanRemovableHolder(), this.addPane(container, groupBoxMargin));
		new CascadeComposite(this, this.buildCascadeHolder(), this.addSubPane(container, 5));
		new Ordering2_0Composite(this, container);
	}
	
	protected PropertyValueModel<EclipseLinkJoinFetch> buildJoinFetchableHolder() {
		return new PropertyAspectAdapter<JavaOneToManyMapping, EclipseLinkJoinFetch>(this.getSubjectHolder()) {
			@Override
			protected EclipseLinkJoinFetch buildValue_() {
				return ((EclipseLinkOneToManyMapping2_0) this.subject).getJoinFetch();
			}
		};
	}
	
	protected PropertyValueModel<EclipseLinkPrivateOwned> buildPrivateOwnableHolder() {
		return new PropertyAspectAdapter<JavaOneToManyMapping, EclipseLinkPrivateOwned>(this.getSubjectHolder()) {
			@Override
			protected EclipseLinkPrivateOwned buildValue_() {
				return ((EclipseLinkOneToManyMapping2_0) this.subject).getPrivateOwned();
			}
		};
	}
	
	protected PropertyValueModel<OrphanRemovable2_0> buildOrphanRemovableHolder() {
		return new PropertyAspectAdapter<JavaOneToManyMapping, OrphanRemovable2_0>(this.getSubjectHolder()) {
			@Override
			protected OrphanRemovable2_0 buildValue_() {
				return ((OrphanRemovalHolder2_0) this.subject).getOrphanRemoval();
			}
		};
	}
}