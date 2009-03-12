/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkOneToOneMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.JoinFetchComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.PrivateOwnedComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.BaseJpaUiFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnComposite;
import org.eclipse.jpt.ui.internal.mappings.details.MappedByPane;
import org.eclipse.jpt.ui.internal.mappings.details.OneToOneJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.mappings.details.OptionalComposite;
import org.eclipse.jpt.ui.internal.mappings.details.TargetEntityComposite;
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
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | MappedByPane                                                     | |
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
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | JoinColumnComposite                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see OneToOneMapping
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see CascadeComposite
 * @see FetchTypeComposite
 * @see JoinColumnComposite
 * @see MappedByPane
 * @see OptionalComposite
 * @see TargetEntityComposite
 *
 * @version 2.2
 * @since 2.2
 */
public class EclipseLink1_1OrmOneToOneMappingComposite extends EclipseLinkOneToOneMappingComposite
{
	/**
	 * Creates a new <code>Eclipselink1_1OneToOneMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToOneMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLink1_1OrmOneToOneMappingComposite(PropertyValueModel<? extends OneToOneMapping> subjectHolder,
	                                Composite parent,
	                                WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();
		Composite subPane = addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);

		new TargetEntityComposite(this, subPane);
		new AccessTypeComposite(this, buildAccessHolderHolder(), subPane);
		new OneToOneJoiningStrategyPane(this, buildJoiningHolder(), container);
		new FetchTypeComposite(this, subPane);
		new JoinFetchComposite(this, buildJoinFetchableHolder(), subPane);
		new OptionalComposite(this, addSubPane(subPane, 4));
		new PrivateOwnedComposite(this, buildPrivateOwnableHolder(), subPane);
		new CascadeComposite(this, buildCascadeHolder(), container);
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