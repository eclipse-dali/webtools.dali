/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;

/**
 * All the state in the definition should be "static" (i.e. unchanging once it is initialized).
 */
public abstract class AbstractResourceUiDefinition
	implements ResourceUiDefinition
{
	private ArrayList<JpaDetailsProvider> detailsProviders;

	private ArrayList<JpaEditorPageDefinition> editorPageDefinitions;


	/**
	 * zero-argument constructor
	 */
	protected AbstractResourceUiDefinition() {
		super();
	}


	// ********** details providers **********

	public synchronized Iterable<JpaDetailsProvider> getDetailsProviders() {
		if (this.detailsProviders == null) {
			this.detailsProviders = this.buildDetailsProviders();
		}
		return this.detailsProviders;
	}

	protected ArrayList<JpaDetailsProvider> buildDetailsProviders() {
		ArrayList<JpaDetailsProvider> providers = new ArrayList<JpaDetailsProvider>();
		this.addDetailsProvidersTo(providers);
		return providers;
	}

	/**
	 * Add the appropriate details providers.
	 */
	protected void addDetailsProvidersTo(@SuppressWarnings("unused") List<JpaDetailsProvider> providers) {
		// only resources for which Dali supplies an details page need implement this method
	}

	
	// ********** editor page definitions **********

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
	 * Add the appropriate definitions
	 */
	protected void addEditorPageDefinitionsTo(@SuppressWarnings("unused") List<JpaEditorPageDefinition> definitions) {
		// only resources for which Dali supplies an editor need implement this method
	}
}
