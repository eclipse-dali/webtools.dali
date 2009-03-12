/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToManyRelationshipReference;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.BaseJpaUiFactory;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
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
 * @see OneToManyMapping
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see CascadeComposite
 * @see FetchTypeComposite
 * @see JoinTableComposite
 * @see OrderingComposite
 * @see TargetEntityComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class OneToManyMappingComposite 
	extends FormPane<OneToManyMapping>
	implements JpaComposite
{
	/**
	 * Creates a new <code>OneToManyMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToManyMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OneToManyMappingComposite(PropertyValueModel<? extends OneToManyMapping> subjectHolder,
	                                 Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	
	private PropertyValueModel<OneToManyRelationshipReference> buildJoiningHolder() {
		return new TransformationPropertyValueModel<OneToManyMapping, OneToManyRelationshipReference>(getSubjectHolder()) {
			@Override
			protected OneToManyRelationshipReference transform_(OneToManyMapping value) {
				return value.getRelationshipReference();
			}
		};
	}
	
	private PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<OneToManyMapping, Cascade>(getSubjectHolder()) {
			@Override
			protected Cascade transform_(OneToManyMapping value) {
				return value.getCascade();
			}
		};
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();
		Composite subPane = addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);

		// Target Entity widgets
		new TargetEntityComposite(this, subPane);
		
		// Joining Strategy widgets
		new OneToManyJoiningStrategyPane(this, buildJoiningHolder(), container);
		
		// Fetch Type widgets
		new FetchTypeComposite(this, subPane);

		// Cascade widgets
		new CascadeComposite(this, buildCascadeHolder(), addSubPane(container, 4));

		// Ordering widgets
		new OrderingComposite(this, container);
	}
}