/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToManyRelationshipReference;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToManyJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.mappings.details.OrderingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.TargetEntityComposite;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
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
 * @see {@link ManyToManyMapping}
 * @see {@link BaseJpaUiFactory} - The factory creating this pane
 * @see {@link TargetEntityComposite}
 * @see {@link ManyToManyJoiningStrategyPane}
 * @see {@link FetchTypeComposite}
 * @see {@link CascadeComposite}
 * @see {@link OrderingComposite}
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkManyToManyMappingComposite 
	extends FormPane<ManyToManyMapping>
	implements JpaComposite
{
	/**
	 * Creates a new <code>ManyToManyMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IManyToManyMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkManyToManyMappingComposite(PropertyValueModel<? extends ManyToManyMapping> subjectHolder,
	                                  Composite parent,
	                                  WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();
		
		new TargetEntityComposite(this, addPane(container, groupBoxMargin));
		new ManyToManyJoiningStrategyPane(this, buildJoiningHolder(), container);
		new FetchTypeComposite(this, addPane(container, groupBoxMargin));
		new JoinFetchComposite(this, buildJoinFetchableHolder(), addPane(container, groupBoxMargin));
		new CascadeComposite(this, buildCascadeHolder(), addSubPane(container, 5));
		new OrderingComposite(this, container);
	}

	protected Composite addPane(Composite container, int groupBoxMargin) {
		return addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);
	}

	protected PropertyValueModel<ManyToManyRelationshipReference> buildJoiningHolder() {
		return new TransformationPropertyValueModel<ManyToManyMapping, ManyToManyRelationshipReference>(
				getSubjectHolder()) {
			@Override
			protected ManyToManyRelationshipReference transform_(ManyToManyMapping value) {
				return value.getRelationshipReference();
			}
		};
	}
	
	protected PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<ManyToManyMapping, Cascade>(getSubjectHolder()) {
			@Override
			protected Cascade transform_(ManyToManyMapping value) {
				return value.getCascade();
			}
		};
	}

	protected PropertyValueModel<JoinFetch> buildJoinFetchableHolder() {
		return new PropertyAspectAdapter<ManyToManyMapping, JoinFetch>(getSubjectHolder()) {
			@Override
			protected JoinFetch buildValue_() {
				return ((EclipseLinkRelationshipMapping) this.subject).getJoinFetch();
			}
		};
	}
}