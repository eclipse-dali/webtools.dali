/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.base;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractJpaJavaGlobalMetadataConvertor {

	protected JpaProject jpaProject;

	public AbstractJpaJavaGlobalMetadataConvertor(JpaProject jpaProject) {
		super();
		this.jpaProject = jpaProject;
	}

	public static void convert(JpaProject jpaProject) {
		new GenericJpaJavaQueryMetadataConvertor(jpaProject).convert();
	}

	protected void convert() {
		WizardDialog dialog = new WizardDialog(this.getCurrentShell(), getWizard()) {
			@Override
			protected void configureShell(Shell newShell) {
				super.configureShell(newShell);
				newShell.setSize(520, 460);
				center(newShell);
			}
		};
		dialog.create();
		dialog.open();	
	}

	abstract protected Wizard getWizard();
	
	protected Shell getCurrentShell() {
		return Display.getCurrent().getActiveShell();
	}

	protected void center(Shell shell) {
		Rectangle bounds = shell.getDisplay().getBounds();
		Point point = shell.getSize();
		int nLeft = (bounds.width - point.x) / 2;
		int nTop = (bounds.height - point.y) / 3;
		shell.setBounds(nLeft, nTop, point.x, point.y);
	}
}
