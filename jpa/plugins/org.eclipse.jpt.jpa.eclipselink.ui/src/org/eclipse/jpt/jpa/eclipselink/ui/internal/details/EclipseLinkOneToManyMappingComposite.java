/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToManyRelationship;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOneToManyMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.OrderingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.TargetEntityComposite;
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
 * | | EclipseLinkOneToManyJoiningStrategyPane                               | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EclipseLinkJoinFetchComposite                                         | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EclipseLinkvateOwnedComposite                                         | |
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
 * @see OneToOneMapping
 * @see CascadeComposite
 * @see EclipseLinkJoinFetchComposite
 * @see EclipseLinkJoinFetchComposite
 * @see EclipseLinkOneToManyJoiningStrategyPane
 * @see FetchTypeComposite
 * @see OrderingComposite
 * @see TargetEntityComposite
 *
 * @version 2.3
 * @since 2.1
 */
public class EclipseLinkOneToManyMappingComposite<T extends OneToManyMapping> 
	extends AbstractOneToManyMappingComposite<T, EclipseLinkOneToManyRelationship>
    implements JpaComposite
{
	/**
	 * Creates a new <code>EclipselinkOneToManyMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToManyMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkOneToManyMappingComposite(PropertyValueModel<? extends T> subjectHolder,
	                                 Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeOneToManySection(Composite container) {
		new TargetEntityComposite(this, container);
		new FetchTypeComposite(this, container);
		new EclipseLinkJoinFetchComposite(this, buildJoinFetchableHolder(), container);
		new EclipseLinkPrivateOwnedComposite(this, buildPrivateOwnableHolder(), container);
		new CascadeComposite(this, buildCascadeHolder(), addSubPane(container, 5));
	}

	@Override
	protected void initializeJoiningStrategyCollapsibleSection(Composite container) {
		new EclipseLinkOneToManyJoiningStrategyPane(this, buildJoiningHolder(), container);
	}

	protected PropertyValueModel<EclipseLinkPrivateOwned> buildPrivateOwnableHolder() {
		return new PropertyAspectAdapter<T, EclipseLinkPrivateOwned>(getSubjectHolder()) {
			@Override
			protected EclipseLinkPrivateOwned buildValue_() {
				return ((EclipseLinkOneToManyMapping) this.subject).getPrivateOwned();
			}
		};
	}
	
	protected PropertyValueModel<EclipseLinkJoinFetch> buildJoinFetchableHolder() {
		return new PropertyAspectAdapter<T, EclipseLinkJoinFetch>(getSubjectHolder()) {
			@Override
			protected EclipseLinkJoinFetch buildValue_() {
				return ((EclipseLinkOneToManyMapping) this.subject).getJoinFetch();
			}
		};
	}
}