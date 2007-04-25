/*******************************************************************************
 * Copyright (c) 2005, 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/        
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Control;


public interface IJpaComposite<E extends EObject> {

	void populate(E model);
	
	Control getControl();
	
	void dispose();
}
