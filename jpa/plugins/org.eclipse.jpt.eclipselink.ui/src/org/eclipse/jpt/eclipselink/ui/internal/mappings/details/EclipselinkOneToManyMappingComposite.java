/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchable;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwnable;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.JoinTableComposite;
import org.eclipse.jpt.ui.internal.mappings.details.MappedByComposite;
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
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | MappedByComposite                                                     | |
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
 * @see MappedByComposite
 * @see OptionalComposite
 * @see TargetEntityComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class EclipselinkOneToManyMappingComposite extends FormPane<OneToManyMapping>
                                      implements JpaComposite
{
	/**
	 * Creates a new <code>OneToManyMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToManyMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipselinkOneToManyMappingComposite(PropertyValueModel<? extends OneToManyMapping> subjectHolder,
	                                 Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// General sub pane
		initializeGeneralPane(container);

		// Join Table sub pane
		initializeJoinTablePane(container);
	}

	private void initializeGeneralPane(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();
		Composite subPane = addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);

		// Target Entity widgets
		new TargetEntityComposite(this, subPane);

		// Fetch Type widgets
		new FetchTypeComposite(this, subPane);
		
		// Join Fetch Type widgets
		new JoinFetchComposite(this, buildJoinFetchableHolder(), subPane);
		
		// Mapped By widgets
		new MappedByComposite(this, subPane);
		
		// Private owned widgets
		new PrivateOwnedComposite(this, buildPrivateOwnableHolder(), subPane);

		// Cascade widgets
		new CascadeComposite(this, buildCascadeHolder(), addSubPane(container, 4));

		// Ordering widgets
		new OrderingComposite(this, container);
	}

	private void initializeJoinTablePane(Composite container) {

		container = addCollapsableSection(
			container,
			JptUiMappingsMessages.MultiRelationshipMappingComposite_joinTable
		);

		new JoinTableComposite(
			this,
			buildJoinTableHolder(),
			container
		);
	}
	
	private PropertyValueModel<PrivateOwnable> buildPrivateOwnableHolder() {
		return new PropertyAspectAdapter<OneToManyMapping, PrivateOwnable>(getSubjectHolder()) {
			@Override
			protected PrivateOwnable buildValue_() {
				return ((EclipseLinkOneToManyMapping) this.subject).getPrivateOwnable();
			}
		};
	}
	
	private PropertyValueModel<JoinFetchable> buildJoinFetchableHolder() {
		return new PropertyAspectAdapter<OneToManyMapping, JoinFetchable>(getSubjectHolder()) {
			@Override
			protected JoinFetchable buildValue_() {
				return ((EclipseLinkOneToManyMapping) this.subject).getJoinFetchable();
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

	private PropertyValueModel<JoinTable> buildJoinTableHolder() {
		return new TransformationPropertyValueModel<OneToManyMapping, JoinTable>(getSubjectHolder()) {
			@Override
			protected JoinTable transform_(OneToManyMapping value) {
				return value.getJoinTable();
			}
		};
	}
}