/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.ui.IEditorInput;

public interface IJPADiagramEditorInput extends IEditorInput {
	
	public Diagram getDiagram();
	
	public String getProjectName();

}
