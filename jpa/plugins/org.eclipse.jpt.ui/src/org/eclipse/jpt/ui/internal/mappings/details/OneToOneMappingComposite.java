/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.OneToOneRelationshipReference;
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
 * @see OneToOneMapping
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see TargetEntityComposite
 * @see JoiningStrategyComposite
 * @see FetchTypeComposite
 * @see OptionalComposite
 * @see CascadeComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class OneToOneMappingComposite 
	extends FormPane<OneToOneMapping>
	implements JpaComposite
{
	/**
	 * Creates a new <code>OneToOneMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToOneMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OneToOneMappingComposite(PropertyValueModel<? extends OneToOneMapping> subjectHolder,
	                                Composite parent,
	                                WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	
	private PropertyValueModel<OneToOneRelationshipReference> buildJoiningHolder() {
		return new TransformationPropertyValueModel<OneToOneMapping, OneToOneRelationshipReference>(
				getSubjectHolder()) {
			@Override
			protected OneToOneRelationshipReference transform_(OneToOneMapping value) {
				return value.getRelationshipReference();
			}
		};
	}
	
	private PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<OneToOneMapping, Cascade>(getSubjectHolder()) {
			@Override
			protected Cascade transform_(OneToOneMapping value) {
				return value.getCascade();
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();
		
		// Target Entity widgets
		new TargetEntityComposite(this, addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
		
		// Joining Strategy widgets
		new OneToOneJoiningStrategyPane(this, buildJoiningHolder(), container);
		
		// Fetch Type widgets
		new FetchTypeComposite(this, addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin));
		
		// Optional check box
		new OptionalComposite(this, addSubPane(container, 4, groupBoxMargin, 0, groupBoxMargin));
		
		// Cascade widgets
		new CascadeComposite(this, buildCascadeHolder(), container);
	}
}