/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyRelationshipReference;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.FetchTypeComposite;
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
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkOneToManyMappingComposite 
	extends FormPane<EclipseLinkOneToManyMapping>
    implements JpaComposite
{
	/**
	 * Creates a new <code>EclipselinkOneToManyMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToManyMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkOneToManyMappingComposite(PropertyValueModel<? extends EclipseLinkOneToManyMapping> subjectHolder,
	                                 Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();
		
		new TargetEntityComposite(this, addPane(container, groupBoxMargin));
		new EclipseLinkOneToManyJoiningStrategyPane(this, buildJoiningHolder(), addPane(container, groupBoxMargin));
		new FetchTypeComposite(this, addPane(container, groupBoxMargin));
		new JoinFetchComposite(this, buildJoinFetchableHolder(), addPane(container, groupBoxMargin));
		new PrivateOwnedComposite(this, buildPrivateOwnableHolder(), addPane(container, groupBoxMargin));
		new CascadeComposite(this, buildCascadeHolder(), addPane(container, groupBoxMargin));
		new OrderingComposite(this, container);
	}
	
	protected Composite addPane(Composite container, int groupBoxMargin) {
		return addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);
	}

	protected PropertyValueModel<EclipseLinkOneToManyRelationshipReference> buildJoiningHolder() {
		return new TransformationPropertyValueModel<EclipseLinkOneToManyMapping, EclipseLinkOneToManyRelationshipReference>(getSubjectHolder()) {
			@Override
			protected EclipseLinkOneToManyRelationshipReference transform_(EclipseLinkOneToManyMapping value) {
				return value.getRelationshipReference();
			}
		};
	}
	
	protected PropertyValueModel<PrivateOwned> buildPrivateOwnableHolder() {
		return new PropertyAspectAdapter<OneToManyMapping, PrivateOwned>(getSubjectHolder()) {
			@Override
			protected PrivateOwned buildValue_() {
				return ((EclipseLinkOneToManyMapping) this.subject).getPrivateOwned();
			}
		};
	}
	
	protected PropertyValueModel<JoinFetch> buildJoinFetchableHolder() {
		return new PropertyAspectAdapter<OneToManyMapping, JoinFetch>(getSubjectHolder()) {
			@Override
			protected JoinFetch buildValue_() {
				return ((EclipseLinkOneToManyMapping) this.subject).getJoinFetch();
			}
		};
	}

	protected PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<OneToManyMapping, Cascade>(getSubjectHolder()) {
			@Override
			protected Cascade transform_(OneToManyMapping value) {
				return value.getCascade();
			}
		};
	}
}