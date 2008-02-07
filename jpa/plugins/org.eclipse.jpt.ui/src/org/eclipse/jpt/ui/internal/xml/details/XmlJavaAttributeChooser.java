/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.jpt.core.internal.context.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class XmlJavaAttributeChooser extends AbstractFormPane<XmlAttributeMapping<? extends AttributeMapping>>
{
	private Text text;

	/**
	 * Creates a new <code>XmlJavaAttributeChooser</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public XmlJavaAttributeChooser(AbstractFormPane<?> parentPane,
	                               PropertyValueModel<? extends XmlAttributeMapping<? extends AttributeMapping>> subjectHolder,
	                               Composite parent) {

		super(parentPane, subjectHolder, parent);
	}


	private XmlPersistentAttribute attribute() {
		return (subject() != null) ? subject().persistentAttribute() : null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void doPopulate() {
		super.doPopulate();
		populateText();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {
		text = buildText(container);
		text.addModifyListener(
			new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					textModified(e);
				}
			});
	}

	private void populateText() {
		if (attribute() == null) {
			text.clearSelection();
			return;
		}

		String name = attribute().getName();

		if (name == null) {
			name = "";
		}
		setTextData(name);
	}

	private void setTextData(String textData) {
		if (! textData.equals(text.getText())) {
			text.setText(textData);
		}
	}

	private void textModified(ModifyEvent e) {
		if (isPopulating()) {
			return;
		}
		String text = ((Text) e.getSource()).getText();
		subject().setName(text);
	}
}