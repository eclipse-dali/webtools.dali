/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Iterator;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.MappedByRelationshipStrategy;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

import com.ibm.icu.text.Collator;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Mapped By: |                                                          |v| |
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see NonOwningMapping
 * @see ManyToManyMappingComposite - A container of this pane
 * @see OneToManyMappingComposite - A container of this pane
 * @see OneToOneMappingComposite - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
public class MappedByPane 
	extends Pane<MappedByRelationshipStrategy>
{
	/**
	 * Creates a new <code>MappedByPane</code>.
	 *
	 * @param parentPane The parent form pane
	 * @param subjectHolder The PVM for the {@link MappedByRelationshipStrategy}
	 * @param parent The parent container
	 */
	public MappedByPane(
			Pane<?> parentPane,
			PropertyValueModel<MappedByRelationshipStrategy> subjectHolder,
			Composite parent) {
		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		addLabeledEditableCombo(
			container,
			JptUiDetailsMessages.Joining_mappedByAttributeLabel,
			buildCandidateAttributesListValueModel(),
			buildAttributePropertyValueModel(),
			JpaHelpContextIds.MAPPING_MAPPED_BY);
	}
	
	@SuppressWarnings("unchecked")
	protected ListValueModel<String> buildCandidateAttributesListValueModel() {
		return new SortedListValueModelAdapter<String>(
			new CollectionAspectAdapter<MappedByRelationshipStrategy, String>(
					getSubjectHolder()) {
				@Override
				protected Iterator<String> iterator_() {
					return this.subject.candidateMappedByAttributeNames();
				}
			},
			Collator.getInstance()
		);
	}
	
	protected WritablePropertyValueModel<String> buildAttributePropertyValueModel() {
		return new PropertyAspectAdapter<MappedByRelationshipStrategy, String>(
				getSubjectHolder(), MappedByRelationshipStrategy.MAPPED_BY_ATTRIBUTE_PROPERTY) {
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