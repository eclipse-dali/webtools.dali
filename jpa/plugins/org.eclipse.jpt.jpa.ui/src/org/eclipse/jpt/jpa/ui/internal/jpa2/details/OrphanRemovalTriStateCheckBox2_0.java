/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | [X]  Orphan removal (true/false)                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link OrphanRemovable2_0}
 * @see {@link JavaOneToOneMappingComposite2_0} - A container of this widget
 * @see {@link OrmOneToOneMappingComposite2_0} - A container of this widget
 */
public class OrphanRemovalTriStateCheckBox2_0 extends Pane<OrphanRemovable2_0>
{
	private TriStateCheckBox checkBox;

	/**
	 * Creates a new <code>OrphanRemoval2_0Composite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public OrphanRemovalTriStateCheckBox2_0(
							Pane<? extends RelationshipMapping> parentPane,
							PropertyValueModel<? extends OrphanRemovable2_0> subjectHolder,
							Composite parent) {
		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected boolean addsComposite() {
		return false;
	}

	@Override
	public Control getControl() {
		return this.checkBox.getCheckBox();
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.checkBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsMessages2_0.ORPHAN_REMOVAL_COMPOSITE_ORPHAN_REMOVAL_LABEL,
			this.buildSpecifiedOrphanRemovalHolder(),
			this.buildOrphanRemovalStringHolder(),
			null		// TODO
		);
	}

	private ModifiablePropertyValueModel<Boolean> buildSpecifiedOrphanRemovalHolder() {
		return new PropertyAspectAdapter<OrphanRemovable2_0, Boolean>(
				this.getSubjectHolder(), 
				OrphanRemovable2_0.DEFAULT_ORPHAN_REMOVAL_PROPERTY,
				OrphanRemovable2_0.SPECIFIED_ORPHAN_REMOVAL_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedOrphanRemoval();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedOrphanRemoval(value);
			}
		};
	}

	private PropertyValueModel<String> buildOrphanRemovalStringHolder() {
		
		return new TransformationPropertyValueModel<Boolean, String>(this.buildDefaultOrphanRemovalHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages2_0.ORPHAN_REMOVAL_COMPOSITE_ORPHAN_REMOVAL_LABEL_DEFAULT, defaultStringValue);
				}
				return JptJpaUiDetailsMessages2_0.ORPHAN_REMOVAL_COMPOSITE_ORPHAN_REMOVAL_LABEL;
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultOrphanRemovalHolder() {
		return new PropertyAspectAdapter<OrphanRemovable2_0, Boolean>(
			this.getSubjectHolder(),
			OrphanRemovable2_0.SPECIFIED_ORPHAN_REMOVAL_PROPERTY,
			OrphanRemovable2_0.DEFAULT_ORPHAN_REMOVAL_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedOrphanRemoval() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultOrphanRemoval());
			}
		};
	}
}
