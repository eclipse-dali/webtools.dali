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

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;

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
 * @see Cascade
 * @see RelationshipMapping
 * @see ManyToManyMappingComposite
 * @see ManyToOneMappingComposite
 * @see OneToManyMappingComposite
 * @see OneToOneMappingComposite
 */
public abstract class AbstractCascadePane<T extends Cascade>
	extends Pane<T>
{
	protected AbstractCascadePane(
		Pane<? extends RelationshipMapping> parentPane,
		PropertyValueModel<? extends T> cascadeModel,
		Composite parent
	) {
		super(parentPane, cascadeModel, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return addTitledGroup(
			container,
			JptJpaUiDetailsMessages.CASCADE_COMPOSITE_CASCADE_TITLE,
			5,
			null);
	}

	@Override
	protected void initializeLayout(Composite container) {		
		addAllCheckBox(container);
		addPersistCheckBox(container);
		addMergeCheckBox(container);
		addRemoveCheckBox(container);
		addRefreshCheckBox(container);
	}

	protected void addAllCheckBox(Composite container) {
		addCheckBox(
				container,
				JptJpaUiDetailsMessages.CASCADE_COMPOSITE_ALL,
				buildCascadeTypeAllModel(),
				null);
	}

	protected void addPersistCheckBox(Composite container) {
		addCheckBox(
				container,
				JptJpaUiDetailsMessages.CASCADE_COMPOSITE_PERSIST,
				buildCascadeTypePersistModel(),
				null);
	}

	protected void addMergeCheckBox(Composite container) {
		addCheckBox(
				container,
				JptJpaUiDetailsMessages.CASCADE_COMPOSITE_MERGE,
				buildCascadeTypeMergeModel(),
				null);
	}

	protected void addRemoveCheckBox(Composite container) {
		addCheckBox(
				container,
				JptJpaUiDetailsMessages.CASCADE_COMPOSITE_REMOVE,
				buildCascadeTypeRemoveModel(),
				null);
	}
	
	protected void addRefreshCheckBox(Composite container) {
		addCheckBox(
				container,
				JptJpaUiDetailsMessages.CASCADE_COMPOSITE_REFRESH,
				buildCascadeTypeRefreshModel(),
				null);
	}
		
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypeAllModel() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), Cascade.ALL_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isAll());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setAll(value.booleanValue());
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypeMergeModel() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), Cascade.MERGE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isMerge());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setMerge(value.booleanValue());
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypePersistModel() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), Cascade.PERSIST_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isPersist());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setPersist(value.booleanValue());
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypeRefreshModel() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), Cascade.REFRESH_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isRefresh());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setRefresh(value.booleanValue());
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypeRemoveModel() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), Cascade.REMOVE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isRemove());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setRemove(value.booleanValue());
			}
		};
	}
}