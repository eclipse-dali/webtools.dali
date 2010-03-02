/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v1_1.details.orm;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkJoinFetchComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkOneToOneMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkPrivateOwnedComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.OneToOneJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.details.OptionalComposite;
import org.eclipse.jpt.ui.internal.details.TargetEntityComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmMappingNameChooser;
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
 * | | AccessTypeComposite                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OneToOneJoiningStrategyPane                                           | |
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
 * | | OptionalComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EclipseLinkPrivateOwnedComposite                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | CascadeComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see OneToOneMapping
 * @see AccessTypeComposite
 * @see CascadeComposite
 * @see EclipseLinkJoinFetchComposite
 * @see EclipseLinkPrivateOwnedComposite
 * @see FetchTypeComposite
 * @see MappedByPane
 * @see OneToOneJoiningStrategyPane
 * @see OptionalComposite
 * @see TargetEntityComposite
 *
 * @version 2.3
 * @since 2.2
 */
public class OrmEclipseLinkOneToOneMapping1_1Composite
extends EclipseLinkOneToOneMappingComposite<OrmOneToOneMapping>
{
	/**
	 * Creates a new <code>Eclipselink1_1OneToOneMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToOneMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmEclipseLinkOneToOneMapping1_1Composite(PropertyValueModel<? extends OrmOneToOneMapping> subjectHolder,
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
		new EclipseLinkJoinFetchComposite(this, buildJoinFetchableHolder(), container);
		new OptionalComposite(this, container);
		new EclipseLinkPrivateOwnedComposite(this, buildPrivateOwnableHolder(), container);
		new CascadeComposite(this, buildCascadeHolder(), addSubPane(container, 5));
	}
		
	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<OneToOneMapping, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
}