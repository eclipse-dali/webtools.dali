/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * @see OrmPersistentAttributeDetailsPageManager - The parent container
 *
 * @version 2.3
 * @since 1.0
 */
public class OrmMappingNameText
	extends Pane<AttributeMapping>
{

	private Text text;

	/**
	 * Creates a new <code>OrmMappingNameText</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public OrmMappingNameText(Pane<?> parentPane,
	                               PropertyValueModel<? extends AttributeMapping> subjectHolder,
	                               Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected boolean addsComposite() {
		return false;
	}

	@Override
	public Control getControl() {
		return this.text;
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.text = this.addText(
			container,
			buildNameHolder()
		);
	}

	private ModifiablePropertyValueModel<String> buildNameHolder() {
		return new PropertyAspectAdapterXXXX<AttributeMapping, String>(getSubjectHolder(), OrmAttributeMapping.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getName();
			}

			@Override
			protected void setValue_(String value) {
				if (this.subject.getPersistentAttribute().isVirtual()) {
					return;
				}
				if (value.length() == 0) {
					value = null;
				}
				// we can safely cast to an orm.xml mapping since it is not virtual
				((OrmAttributeMapping) this.subject).setName(value);
			}
		};
	}
}