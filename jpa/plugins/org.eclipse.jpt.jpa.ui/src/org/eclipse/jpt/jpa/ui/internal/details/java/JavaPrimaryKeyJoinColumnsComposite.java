/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractPrimaryKeyJoinColumnsComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * @see JavaEntity
 * @see JavaInheritanceComposite - The container of this pane
 *
 * @version 2.0
 * @since 2.0
 */
public class JavaPrimaryKeyJoinColumnsComposite extends AbstractPrimaryKeyJoinColumnsComposite<JavaEntity>
{
	public JavaPrimaryKeyJoinColumnsComposite(Pane<? extends JavaEntity> subjectHolder,
	                                      Composite parent) {

		super(subjectHolder, parent);
	}
	
	@Override
	protected ListValueModel<SpecifiedPrimaryKeyJoinColumn> buildDefaultJoinColumnsListHolder() {
		return new PropertyListValueModelAdapter<SpecifiedPrimaryKeyJoinColumn>(buildDefaultJoinColumnHolder());
	}
	
	private PropertyValueModel<SpecifiedPrimaryKeyJoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapter<JavaEntity, SpecifiedPrimaryKeyJoinColumn>(getSubjectHolder(), JavaEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN_PROPERTY) {
			@Override
			protected SpecifiedPrimaryKeyJoinColumn buildValue_() {
				return subject.getDefaultPrimaryKeyJoinColumn();
			}
		};
	}
}