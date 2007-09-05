/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.mappings.properties;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.ui.internal.IJpaUiFactory;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class NullAttributeMappingUiProvider
	implements IAttributeMappingUiProvider
{
	
	// singleton
	private static final NullAttributeMappingUiProvider INSTANCE = new NullAttributeMappingUiProvider();

	/**
	 * Return the singleton.
	 */
	public static IAttributeMappingUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NullAttributeMappingUiProvider() {
		super();
	}


	public String attributeMappingKey() {
		return null;
	}
	
	public String label() {
		return "";
	}
	
	public IJpaComposite buildAttributeMappingComposite(IJpaUiFactory factory, Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new NullComposite(parent);
	}
	
	
	public static class NullComposite extends Composite 
		implements IJpaComposite 
	{
		NullComposite(Composite parent) {
			super(parent, SWT.NONE);
		}
		
		public void populate(EObject model) {
			// no op
		}
		
		@Override
		public void dispose() {
			super.dispose();
		}
		public Control getControl() {
			return this;
		}
	}	
}