/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.IdClassHolder;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | ClassChooserPane                                                          |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IdClass
 * @see ClassChooserPane
 * @see AbstractEntityComposite - A parent container
 * @see MappedSuperclassComposite - A parent container
 *
 * @version 2.0
 * @since 2.0
 */
public class IdClassComposite extends Pane<IdClassHolder>
{
	/**
	 * Creates a new <code>IdClassComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public IdClassComposite(Pane<? extends IdClassHolder> parentPane,
                           Composite parent) {

		super(parentPane, parent);
	}
	
	public IdClassComposite(Pane<? extends IdClassHolder> parentPane,
        					Composite parent,
        					boolean automaticallyAlignWidgets) {

		super(parentPane, parent, automaticallyAlignWidgets);
	}

	private ClassChooserPane<IdClassHolder> addClassChooser(Composite container) {

		return new ClassChooserPane<IdClassHolder>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<IdClassHolder, String>(getSubjectHolder(), IdClassHolder.ID_CLASS_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getIdClass();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						this.subject.setIdClass(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getIdClass();
			}

			@Override
			protected String getLabelText() {
				return JptUiDetailsMessages.IdClassComposite_label;
			}
			
			@Override
			protected JpaProject getJpaProject() {
				return getSubject().getJpaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				getSubject().setIdClass(className);
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getIdClassEnclosingTypeSeparator();
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		addClassChooser(container);
	}
}
