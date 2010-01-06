/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @see OrmPersistentAttributeDetailsPage - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
public class OrmJavaAttributeChooser extends Pane<OrmAttributeMapping>
{
	private Text text;

	/**
	 * Creates a new <code>XmlJavaAttributeChooser</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public OrmJavaAttributeChooser(Pane<?> parentPane,
	                               PropertyValueModel<OrmAttributeMapping> subjectHolder,
	                               Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	private WritablePropertyValueModel<String> buildNameHolder() {
		return new PropertyAspectAdapter<OrmAttributeMapping, String>(getSubjectHolder(), OrmAttributeMapping.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getName();
			}

			@Override
			protected void setValue_(String value) {
				if (subject.getPersistentAttribute().isVirtual()) {
					return;
				}
				if (value.length() == 0) {
					value = null;
				}
				subject.setName(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);

		if (!text.isDisposed()) {
			text.setEnabled(enabled);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		text = addLabeledText(
			container,
			JptUiDetailsOrmMessages.OrmJavaAttributeChooser_javaAttribute,
			buildNameHolder()
		);
	}
}