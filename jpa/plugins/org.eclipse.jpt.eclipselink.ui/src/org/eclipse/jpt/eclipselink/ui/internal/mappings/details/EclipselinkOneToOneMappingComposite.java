/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwnable;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnComposite;
import org.eclipse.jpt.ui.internal.mappings.details.MappedByComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OptionalComposite;
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
public class EclipselinkOneToOneMappingComposite extends FormPane<OneToOneMapping>
                                      implements JpaComposite
{
	/**
	 * Creates a new <code>OneToOneMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToOneMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipselinkOneToOneMappingComposite(PropertyValueModel<? extends OneToOneMapping> subjectHolder,
	                                Composite parent,
	                                WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();
		Composite subPane = addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);

		// Target Entity widgets
		new TargetEntityComposite(this, subPane);

		// Fetch Type widgets
		new FetchTypeComposite(this, subPane);

		// Mapped By widgets
		new MappedByComposite(this, subPane);

		// Optional check box
		new OptionalComposite(this, addSubPane(subPane, 4));
		
		addCheckBox(
			subPane,
			EclipseLinkUiMappingsMessages.PrivateOwnedComposite_privateOwnedLabel,
			buildPrivateOwnedHolder(),
			null
		);

		// Cascade widgets
		new CascadeComposite(this, buildCascadeHolder(), container);

		// Join Column widgets
		new JoinColumnComposite(this, container);
	}
	
	private PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<OneToOneMapping, Cascade>(getSubjectHolder()) {
			@Override
			protected Cascade transform_(OneToOneMapping value) {
				return value.getCascade();
			}
		};
	}

	private PropertyAspectAdapter<OneToOneMapping, Boolean> buildPrivateOwnedHolder() {
		return new PropertyAspectAdapter<OneToOneMapping, Boolean>(getSubjectHolder(), PrivateOwnable.PRIVATE_OWNED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(((PrivateOwnable) this.subject).getPrivateOwned());
			}
			@Override
			protected void setValue_(Boolean value) {
				((PrivateOwnable) this.subject).setPrivateOwned(value.booleanValue());
			}
		};
	}

}