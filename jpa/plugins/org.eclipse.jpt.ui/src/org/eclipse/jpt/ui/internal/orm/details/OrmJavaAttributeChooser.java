/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.Collection;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @see OrmPersistentAttributeDetailsPage - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class OrmJavaAttributeChooser extends AbstractFormPane<AbstractOrmAttributeMapping<? extends XmlAttributeMapping>>
{
	private Text text;

	/**
	 * Creates a new <code>XmlJavaAttributeChooser</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public OrmJavaAttributeChooser(AbstractFormPane<?> parentPane,
	                               PropertyValueModel<? extends AbstractOrmAttributeMapping<? extends XmlAttributeMapping>> subjectHolder,
	                               Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(AbstractOrmAttributeMapping.NAME_PROPERTY);
	}

	private ModifyListener buildNameModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!isPopulating()) {
					Text text = (Text) e.widget;
					textChanged(text.getText());
				}
			}
		};
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

		text = buildLabeledText(
			container,
			JptUiOrmMessages.PersistentAttributePage_javaAttributeLabel,
			buildNameModifyListener()
		);
	}

	private void populateText() {

		AbstractOrmAttributeMapping<?> subject = subject();
		text.setText("");

		if (subject == null) {
			return;
		}

		String name = subject.getName();

		if (name == null) {
			name = "";
		}

		text.setText(name);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == AbstractOrmAttributeMapping.NAME_PROPERTY) {
			populateText();
		}
	}

	private void textChanged(String text) {

		setPopulating(true);

		try {
			subject().setName(text);
		}
		finally {
			setPopulating(false);
		}
	}
}