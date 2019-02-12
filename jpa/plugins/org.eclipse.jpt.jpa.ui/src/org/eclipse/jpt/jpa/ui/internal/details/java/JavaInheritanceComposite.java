/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractInheritanceComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * The pane used for java inheritance.
 *
 * @see JavaEntity
 * @see JavaPrimaryKeyJoinColumnsComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class JavaInheritanceComposite extends AbstractInheritanceComposite<JavaEntity> {

	/**
	 * Creates a new <code>JavaInheritanceComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public JavaInheritanceComposite(Pane<? extends JavaEntity> parentPane,
	                            Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected Control addPrimaryKeyJoinColumnsComposite(Composite container) {
		return new JavaPrimaryKeyJoinColumnsComposite(this, container).getControl();
	}
}