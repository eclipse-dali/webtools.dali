/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkJoinFetchComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkManyToOneMappingComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.ManyToOneJoiningStrategyPane;
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
 * | | JoiningStrategyComposite                                              | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OptionalComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | CascadeComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link ManyToOneMapping}
 * @see {@link TargetEntityComposite}
 * @see {@link ManyToOneJoiningStrategyPane}
 * @see {@link FetchTypeComposite}
 * @see {@link OptionalComposite}
 * @see {@link CascadeComposite}
 *
 * @version 2.3
 * @since 2.2
 */
public class OrmEclipseLinkManyToOneMappingComposite
	extends EclipseLinkManyToOneMappingComposite<OrmManyToOneMapping>
{
	/**
	 * Creates a new <code>EclipseLink1_1ManyToOneMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>ManyToOneMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmEclipseLinkManyToOneMappingComposite(PropertyValueModel<? extends OrmManyToOneMapping> subjectHolder,
	                                 Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeManyToOneSection(Composite container) {
		new TargetEntityComposite(this, container);
		new OrmMappingNameChooser(this, getSubjectHolder(), container);
		new FetchTypeComposite(this, container);		
		new EclipseLinkJoinFetchComposite(this, buildJoinFetchableHolder(), container);
		new OptionalComposite(this, container);
		new CascadeComposite(this, buildCascadeHolder(), addSubPane(container, 5));
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolderHolder() {
		return new PropertyAspectAdapter<ManyToOneMapping, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}

}
