/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;

/**
 * All the state in the definition should be "static" (i.e. unchanging once it is initialized).
 */
public abstract class AbstractResourceUiDefinition
	implements ResourceUiDefinition
{

	private ArrayList<JpaEditorPageDefinition> editorPageDefinitions;


	/**
	 * zero-argument constructor
	 */
	protected AbstractResourceUiDefinition() {
		super();
	}

	public synchronized ListIterable<JpaEditorPageDefinition> getEditorPageDefinitions() {
		if (this.editorPageDefinitions == null) {
			this.editorPageDefinitions = this.buildEditorPageDefinitions();
		}
		return IterableTools.listIterable(this.editorPageDefinitions);
	}

	protected ArrayList<JpaEditorPageDefinition> buildEditorPageDefinitions() {
		ArrayList<JpaEditorPageDefinition> definitions = new ArrayList<JpaEditorPageDefinition>();
		this.addEditorPageDefinitionsTo(definitions);
		return definitions;
	}

	/**
	 * Override to add the appropriate <code>JpaEditorPageDefinitions</code>
	 * @param definitions
	 */
	protected void addEditorPageDefinitionsTo(List<JpaEditorPageDefinition> definitions) {
		//subclasses override
	}
}
