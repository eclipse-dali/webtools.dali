/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.java;

import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToManyRelationshipReference;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToManyMapping2_0;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkJoinFetchComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkManyToManyMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkOneToManyJoiningStrategyPane;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkPrivateOwnedComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.OrderingComposite;
import org.eclipse.jpt.ui.internal.details.TargetEntityComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.Ordering2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.OrphanRemoval2_0Composite;
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
public class JavaEclipseLinkManyToManyMapping2_0Composite
	extends EclipseLinkManyToManyMappingComposite<ManyToManyMapping>
{
	public JavaEclipseLinkManyToManyMapping2_0Composite(
			PropertyValueModel<? extends ManyToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeManyToManySection(Composite container) {
		new TargetEntityComposite(this, container);
		new FetchTypeComposite(this, container);
		new EclipseLinkJoinFetchComposite(this, this.buildJoinFetchableHolder(), container);
		new CascadeComposite(this, buildCascadeHolder(), addSubPane(container, 5));
	}

	@Override
	protected void initializeOrderingCollapsibleSection(Composite container) {
		new Ordering2_0Composite(this, container);
	}

	protected PropertyValueModel<ManyToManyRelationshipReference> buildEclipseLinkJoiningHolder() {
		return new TransformationPropertyValueModel<ManyToManyMapping, ManyToManyRelationshipReference>(this.getSubjectHolder()) {
			@Override
			protected ManyToManyRelationshipReference transform_(ManyToManyMapping value) {
				return value.getRelationshipReference();
			}
		};
	}
}