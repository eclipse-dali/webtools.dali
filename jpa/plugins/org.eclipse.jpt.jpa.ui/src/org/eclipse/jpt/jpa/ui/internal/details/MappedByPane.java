/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.core.internal.utility.ICUStringCollator;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedMappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Mapped By: |                                                          |v| |
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 1.0
 */
public class MappedByPane 
	extends Pane<SpecifiedMappedByRelationshipStrategy>
{
	/**
	 * Creates a new <code>MappedByPane</code>.
	 *
	 * @param parentPane The parent form pane
	 * @param subjectHolder The PVM for the {@link SpecifiedMappedByRelationshipStrategy}
	 * @param parent The parent container
	 */
	public MappedByPane(
			Pane<?> parentPane,
			PropertyValueModel<SpecifiedMappedByRelationshipStrategy> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent) {
		super(parentPane, subjectHolder, enabledModel, parent);
	}
	
	@Override
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.addLabel(container, JptJpaUiDetailsMessages.JOINING_MAPPED_BY_ATTRIBUTE_LABEL);
		this.addEditableCombo(
			container,
			buildCandidateAttributesListValueModel(),
			buildAttributePropertyValueModel(),
			TransformerTools.<String>objectToStringTransformer(),
			JpaHelpContextIds.MAPPING_MAPPED_BY);
	}
	
	protected ListValueModel<String> buildCandidateAttributesListValueModel() {
		return new SortedListValueModelAdapter<String>(
			new CollectionAspectAdapter<SpecifiedMappedByRelationshipStrategy, String>(
					getSubjectHolder()) {
				@Override
				protected Iterable<String> getIterable() {
					return this.subject.getCandidateMappedByAttributeNames();
				}
			},
			new ICUStringCollator()
		);
	}
	
	protected ModifiablePropertyValueModel<String> buildAttributePropertyValueModel() {
		return new PropertyAspectAdapterXXXX<SpecifiedMappedByRelationshipStrategy, String>(
				getSubjectHolder(), SpecifiedMappedByRelationshipStrategy.MAPPED_BY_ATTRIBUTE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getMappedByAttribute();
			}
			
			@Override
			protected void setValue_(String value) {
				this.subject.setMappedByAttribute(value);
			}
		};
	}
}