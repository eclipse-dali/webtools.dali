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
import org.eclipse.jpt.ui.internal.jpa2.details.OrphanRemoval2_0Composite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
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
public class JavaEclipseLinkOneToManyMapping2_0Composite<T extends EclipseLinkOneToManyMapping2_0>
	extends AbstractOneToManyMappingComposite<T>
{
	public JavaEclipseLinkOneToManyMapping2_0Composite(
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = this.getGroupBoxMargin();
		
		new TargetEntityComposite(this, this.addPane(container, groupBoxMargin));
		new EclipseLinkOneToManyJoiningStrategyPane(this, this.buildEclipseLinkJoiningHolder(), container);
		new FetchTypeComposite(this, this.addPane(container, groupBoxMargin));
		new EclipseLinkJoinFetchComposite(this, this.buildJoinFetchableHolder(), this.addPane(container, groupBoxMargin));
		new EclipseLinkPrivateOwnedComposite(this, this.buildPrivateOwnableHolder(), this.addPane(container, groupBoxMargin));
		new CascadeComposite(this, this.buildCascadeHolder(), this.addSubPane(container, 5));
		new OrphanRemoval2_0Composite(this, this.addPane(container, groupBoxMargin));
		new OrderingComposite(this, container);
	}

	protected PropertyValueModel<EclipseLinkOneToManyRelationshipReference> buildEclipseLinkJoiningHolder() {
		return new TransformationPropertyValueModel<T, EclipseLinkOneToManyRelationshipReference>(this.getSubjectHolder()) {
			@Override
			protected EclipseLinkOneToManyRelationshipReference transform_(T value) {
				return value.getRelationshipReference();
			}
		};
	}
	
	protected PropertyValueModel<EclipseLinkJoinFetch> buildJoinFetchableHolder() {
		return new PropertyAspectAdapter<T, EclipseLinkJoinFetch>(this.getSubjectHolder()) {
			@Override
			protected EclipseLinkJoinFetch buildValue_() {
				return this.subject.getJoinFetch();
			}
		};
	}
	
	protected PropertyValueModel<EclipseLinkPrivateOwned> buildPrivateOwnableHolder() {
		return new PropertyAspectAdapter<T, EclipseLinkPrivateOwned>(this.getSubjectHolder()) {
			@Override
			protected EclipseLinkPrivateOwned buildValue_() {
				return this.subject.getPrivateOwned();
			}
		};
	}
	
}
	