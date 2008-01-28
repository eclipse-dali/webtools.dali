/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.ICascade;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Cascade --------------------------------------------------------------- |
 * | |                                                                       | |
 * | | x All       x Persist   x Merge     x Remove    x Refresh             | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see ICascade
 * @see IRelationshipMapping
 * @see ManyToManyMappingComposite - A container of this pane
 * @see ManyToOneMappingComposite - A container of this pane
 * @see OneToManyMappingComposite - A container of this pane
 * @see OneToOneMappingComposite - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
public class CascadeComposite extends AbstractFormPane<ICascade>
{
	/**
	 * Creates a new <code>CascadeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject <code>ICascade</code>
	 * @param parent The parent container
	 */
	public CascadeComposite(AbstractFormPane<? extends IRelationshipMapping> parentPane,
	                        PropertyValueModel<? extends ICascade> subjectHolder,
		                     Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>ICascade</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public CascadeComposite(PropertyValueModel<? extends ICascade> subjectHolder,
		                     Composite parent,
		                     TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private WritablePropertyValueModel<Boolean> buildCascadeTypeAllHolder() {
		return new PropertyAspectAdapter<ICascade, Boolean>(getSubjectHolder(), ICascade.ALL_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isAll();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setAll(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildCascadeTypeMergeHolder() {
		return new PropertyAspectAdapter<ICascade, Boolean>(getSubjectHolder(), ICascade.MERGE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isMerge();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setMerge(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildCascadeTypePersistHolder() {
		return new PropertyAspectAdapter<ICascade, Boolean>(getSubjectHolder(), ICascade.PERSIST_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isPersist();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setPersist(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildCascadeTypeRefreshHolder() {
		return new PropertyAspectAdapter<ICascade, Boolean>(getSubjectHolder(), ICascade.REFRESH_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isRefresh();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setRefresh(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildCascadeTypeRemoveHolder() {
		return new PropertyAspectAdapter<ICascade, Boolean>(getSubjectHolder(), ICascade.REMOVE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isRemove();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setRemove(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Cascade group
		Group cascadeGroup = buildTitledPane(
			container,
			JptUiMappingsMessages.CascadeComposite_cascadeTitle
		);

		// Container of the check boxes
		container = buildSubPane(cascadeGroup, 5, 8, 0, 0, 0);

		// All check box
		buildCheckBox(
			container,
			JptUiMappingsMessages.CascadeComposite_all,
			buildCascadeTypeAllHolder()
		);

		// Persist check box
		buildCheckBox(
			container,
			JptUiMappingsMessages.CascadeComposite_persist,
			buildCascadeTypePersistHolder()
		);

		// Merge check box
		buildCheckBox(
			container,
			JptUiMappingsMessages.CascadeComposite_merge,
			buildCascadeTypeMergeHolder()
		);

		// Remove check box
		buildCheckBox(
			container,
			JptUiMappingsMessages.CascadeComposite_remove,
			buildCascadeTypeRemoveHolder()
		);

		// Refresh check box
		buildCheckBox(
			container,
			JptUiMappingsMessages.CascadeComposite_refresh,
			buildCascadeTypeRefreshHolder()
		);
	}
}