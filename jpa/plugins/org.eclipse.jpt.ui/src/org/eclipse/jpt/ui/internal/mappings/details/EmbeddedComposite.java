/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.mappings.IEmbedded;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class EmbeddedComposite extends BaseJpaComposite 
{
	private IEmbedded embedded;
	
	private EmbeddedAttributeOverridesComposite attributeOverridesComposite;

	public EmbeddedComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
	}
	
	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);

		GridData gridData;
		
		this.attributeOverridesComposite = new EmbeddedAttributeOverridesComposite(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.attributeOverridesComposite.getControl().setLayoutData(gridData);
	}
	
	
	public void doPopulate(EObject obj) {
		this.embedded = (IEmbedded) obj;
		this.attributeOverridesComposite.populate(obj);
	}
	
	public void doPopulate() {
		this.attributeOverridesComposite.populate();
	}
	
	protected void engageListeners() {
	}
	
	protected void disengageListeners() {
	}
	
	@Override
	public void dispose() {
		this.attributeOverridesComposite.dispose();
	}

}
